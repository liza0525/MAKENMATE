<template>
  <div>
    <google-map
      class="googleMap"
      @click="addMarker"
      :style="{ height: map.height }"
      style="margin-top:4rem"
    >
      <div slot-scope="{ google, map }">
        <google-map-marker
          v-for="(marker, index) in markers"
          :google="google"
          :map="map"
          :key="index"
          :position="marker.position"
          @click="panTo($event, map)"
        ></google-map-marker>

        <google-map-custom-control
          :google="google"
          :map="map"
          position="BOTTOM_CENTER"
          :style="{ height: map.height }"
          style="top:4rem"
        >
          <input type="text" />
        </google-map-custom-control>
      </div>
    </google-map>

    <Drawer @close="toggle" align="right" :closeable="true">
      <div v-if="open">
        <div v-for="meet in meetings" :key="meet.mid">
          <h1>{{ meet.title }}</h1>
          <p>{{ meet.date }}</p>
          <p>{{ meet.people }} / {{ meet.count }}명</p>
          <span v-for="um in meet.usermeeting" :key="um.id">
            <img :src="um.userImg" />
          </span>
          <button @click="goMeeting(meet.mid)">신청하기</button>
          <button
            v-if="meet.author === username"
            @click="deleteMeeting(meet.mid)"
          >
            삭제
          </button>
        </div>
        <div class="up">
          <div>
            <label>제목</label>
            <input
              type="text"
              v-model="meeting.title"
              placeholder="제목"
            /><br />
            <label>시간</label>
            <input
              type="text"
              v-model="meeting.date"
              placeholder="시간"
            /><br />
            <label>인원</label>
            <input
              type="text"
              v-model="meeting.count"
              placeholder="인원"
            /><br />
            <label>장소</label>
            <input
              type="text"
              v-model="meeting.place"
              placeholder="장소"
            /><br />
            <label>상세 주소</label>
            <input
              type="text"
              v-model="meeting.detail"
              placeholder="상세 주소"
            />
            <button @click="makeMeeting()"><h1>만남 만들기</h1></button>
          </div>
        </div>
      </div>
    </Drawer>
    <div class="modal info-modal">
      <v-card class="modal-content" max-width="344" outlined>
        <p class="close">&times;</p>
        <div>
          <h1>{{ meeting.title }}</h1>
          <p>{{ meeting.people }} / {{ meeting.count }}명</p>
          <button @click="goMeeting(meeting.mid)">신청하기</button>
        </div>
      </v-card>
    </div>
  </div>
</template>

<script>
import Drawer from "vue-simple-drawer";
import http from "../../http-common";
import axios from "axios";
import config from "../../../config";
export default {
  components: {
    Drawer
  },
  data() {
    return {
      markers: [],
      pos: {
        lat: 37.5,
        lng: 127
      },
      username: "",
      meetings: [],
      meeting: {
        mid: 0,
        title: "",
        date: "",
        count: 0,
        place: "",
        detail: ""
      },
      window: {
        width: 0,
        height: 0
      },
      open: false,
      location: null,
      gettingLocation: false,
      errorStr: null,
      Images: [],
      map: {
        width: 0,
        height: 0
      }
    };
  },
  mounted() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
    this.username = this.$store.state.username;
    this.getMarker();
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  },
  methods: {
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      // console.log(typeof this.window.height);
      this.window.height = this.window.height + "px";
      this.map.height = "calc(" + this.window.height + " - 8rem)";
      this.window.height = "calc(" + this.window.height + " - 4rem)";
    },
    addMarker(e) {
      const { lat, lng } = e.latLng.toJSON();
      this.open = !this.open;
      this.pos.lat = lat;
      this.pos.lng = lng;
      axios
        .get(
          "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
            lat +
            ", " +
            lng +
            "&key=" +
            config.apiKey
        )
        .then(res => {
          this.meeting.place = res.data.results[0].formatted_address;
        });
    },
    panTo(e, map) {
      map.panTo(e.latLng);
      http
        .get("/meeting/one", {
          params: {
            latitude: e.latLng.lat(),
            longitude: e.latLng.lng()
          }
        })
        .then(res => {
          console.log(res);
          let modal = document.getElementsByClassName("modal");
          let close = document.getElementsByClassName("close");
          this.meeting = res.data;
          modal[0].style.display = "block";
          close[0].onclick = function(e) {
            modal[0].style.display = "none";
          };
        });
    },
    toggle() {
      this.open = !this.open;
    },
    goMeeting(mid) {
      http
        .post("/meeting/user", null, {
          params: {
            username: this.username,
            mid: mid
          }
        })
        .then(res => console.log(res))
        .catch(exp => console.log(exp));
    },
    getMarker() {
      http.get("/meeting").then(res => {
        this.meetings = res.data.object;
        this.markers = [];
        this.meetings.forEach(el => {
          let pos = {
            position: {
              lat: parseFloat(el.latitude),
              lng: parseFloat(el.longitude)
            }
          };
          this.markers.push(pos);
        });
      });
    },
    makeMeeting() {
      http
        .post("/meeting", {
          author: this.username,
          title: this.meeting.title,
          latitude: this.pos.lat,
          longitude: this.pos.lng,
          date: this.meeting.date,
          place: this.meeting.place + " " + this.meeting.detail,
          count: this.meeting.count
        })
        .then(res => {
          this.meetings = res.data.object;
          this.markers = [];
          this.meetings.forEach(el => {
            let pos = {
              lat: el.latitude,
              lng: el.longitude
            };
            this.markers.push(pos);
          });
        });
    },
    deleteMeeting(mid) {
      http.delete("/meeting/" + mid).then(res => {
        this.meetings = res.data.object;
        this.markers = [];
        this.meetings.forEach(el => {
          let pos = {
            lat: el.latitude,
            lng: el.longitude
          };
          this.markers.push(pos);
        });
      });
    },
    async getLocation() {
      return new Promise((resolve, reject) => {
        if (!("geolocation" in navigator)) {
          reject(new Error("Geolocation is not available."));
        }

        navigator.geolocation.getCurrentPosition(
          pos => {
            resolve(pos);
          },
          err => {
            reject(err);
          }
        );
      });
    },
    async locateMe() {
      this.gettingLocation = true;
      try {
        this.gettingLocation = false;
        this.location = await this.getLocation();
        this.pos.lat = this.location.coords.latitude;
        this.pos.lng = this.location.coords.longitude;
      } catch (e) {
        this.gettingLocation = false;
        this.errorStr = e.message;
      }
    },
    getSearchData(inputValue) {
      console.log(inputValue);
    }
  }
};
</script>

<style lang="scss" scoped>
@import "~vue-simple-drawer/src/index";
.googleMap {
  height: 41rem;
  position: relative;
}
.modal {
  display: none;
  position: fixed;
  z-index: 100;
  left: 0;
  top: 10%;
  width: 100%;
  height: 100%;
  overflow: auto;
  // z-index:100; position: absolute; top: 10%; left: 4%
}
.modal-content {
  color: black;
  background-color: #fefefe;
  margin: 5% auto;
  padding: 2%;
  width: 50%;
}
.close {
  display: inline;
  right: 0%;
  font-size: 25px;
  cursor: pointer;
}
.up {
  padding-left: 5%;
  bottom: 2rem;
  position: absolute;
  left: 0;
  right: 0;
  width: 80%;
}
input {
  width: 90%;
}
.search {
  z-index: 2;
}
</style>
