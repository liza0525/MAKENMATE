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
          <p>{{ meet.limit }}명</p>
          <button @click="goMeeting()">신청하기</button>
          <button v-if="meet.author === username" @click="deleteMeeting()">
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
            <input type="text" v-model="meeting.limit" placeholder="인원" />
            <label>장소</label>
            <input type="text" v-model="meeting.place" placeholder="장소" />
            <button @click="makeMeeting()"><h1>만남 만들기</h1></button>
          </div>
        </div>
      </div>
    </Drawer>
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
      markers: [
        { position: { lat: 37.5, lng: 126.98 } },
        { position: { lat: 37.5, lng: 126.99 } },
        { position: { lat: 37.5, lng: 127.0 } },
        { position: { lat: 37.5, lng: 127.01 } },
        { position: { lat: 37.5, lng: 127.02 } }
      ],
      pos: {
        lat: 37.5,
        lng: 127
      },
      username: "",
      meetings: [
        {
          id: 1,
          title: "aaa",
          date: "2020.2.27",
          limit: 3,
          place: "강남역",
          author: "aa"
        },
        {
          id: 2,
          title: "bbb",
          date: "2020.3.1",
          limit: 4,
          place: "역삼역",
          author: "aa"
        },
        {
          id: 3,
          title: "ccc",
          date: "2020.2.20",
          limit: 5,
          place: "선릉역",
          author: "aa"
        }
      ],
      meeting: {
        id: 0,
        title: "",
        date: "",
        limit: 0,
        place: ""
      },
      open: false,
      location: null,
      gettingLocation: false,
      errorStr: null
    };
  },
  mounted() {
    this.username = this.$store.state.username;
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
    },
    toggle() {
      this.open = !this.open;
    },
    getMarker() {
      http.get("/meeting").then(res => {
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
    makeMeeting() {
      http
        .post("/meeting", {
          author: this.username,
          title: this.meeting.title,
          latitude: this.pos.lat,
          longitude: this.pos.lng,
          date: this.meeting.date,
          place: this.meeting.place,
          limit: this.meeting.limit
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
  height: 50rem;
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
