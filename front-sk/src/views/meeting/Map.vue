<template>
  <div>
    <google-map class="googleMap" @click="addMarker">
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
            <input type="text" v-model="meeting.title" placeholder="제목" />
            <label>시간</label>
            <input type="text" v-model="meeting.date" placeholder="시간" />
            <label>인원</label>
            <input type="text" v-model="meeting.count" placeholder="인원" />
            <label>장소</label>
            <input type="text" v-model="meeting.place" placeholder="장소" />
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
        place: ""
      },
      open: false,
      location: null,
      gettingLocation: false,
      errorStr: null,
      Images: []
    };
  },
  mounted() {
    this.username = this.$store.state.username;
    console.log(this.username);
    this.getMarker();
  },
  methods: {
    addMarker(e) {
      const { lat, lng } = e.latLng.toJSON();
      this.open = !this.open;
      this.pos.lat = lat;
      this.pos.lng = lng;
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
        console.log(res.data.object[1].usermeeting);
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
          place: this.meeting.place,
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
  width: 50%;
}
</style>
