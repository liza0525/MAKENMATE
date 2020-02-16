<template>
<div>
  <div style="text-align:center;">
    <div class="background" :style="{height: window.height}">
      <v-row>
        <v-col md="6" xs="12" style="display:inline-table" :style="{height: window.height}">
          <div  style="padding-right:10%;padding-left:10%;display:table-cell; vertical-align:middle">
          <div class="oops">
            Oops, <br>Something's<br> gone wrong
          </div>
          <div class="nottoworry">
            Not to worry. Why don't you try one of these helpful links:
          </div>
<button class="gobtn" @click="clickback">
      GO BACK
    </button>
<button class="gobtn" @click="clickmain">
      GO MAIN
    </button>
          </div>
        </v-col>
      <v-col style="display:inline-table" :style="{height: window.height}">
      <div class="log" style="display:table-cell; vertical-align:middle">
        <div class="open">404</div>
        <div class="hrs">PAGE NOT FOUND</div>
      </div>
</v-col>
      </v-row>
    </div>
      </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      url : require("../assets/images/404.jpg"),
          window: {
      width: 0,
      height: 0
    },
    logo:{height: 0}
    }
  },
  created() {
    window.addEventListener('resize', this.handleResize)
    this.handleResize();
  },
  destroyed() {
    window.removeEventListener('resize', this.handleResize)
  },
  methods: {
    handleResize() {
      this.window.width = window.innerWidth;
      this.window.height = window.innerHeight;
      this.window.height = this.window.height + "px";
      this.window.height = "calc(" + this.window.height + " - 4rem)";
    },
    clickback() {
      this.$router.go(-1)
    },
    clickmain() {
      this.$router.push({
        name: "Main",
      });
    },
  },
}
</script>

<style scoped>

@font-face {
    font-family: 'neontubes';
    src: url('https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.eot');
    src: url('https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.eot?#iefix') format('embedded-opentype'),
         url('https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.woff2') format('woff2'),
         url('https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.woff') format('woff'),
         url('https://bitbucket.org/kennethjensen/webfonts/raw/fc13c1cb430a0e9462da56fe3f421ff7af72db71/neontubes/neontubes-webfont.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
}

body {
  -webkit-font-smoothing:antialiased;
  -moz-osx-font-smoothing:grayscale;
}

html, body, #app, section.section {
  height: 100%;
}
div {
  text-align: center
}

.gobtn{
  margin-top:5%;
  font-size:1.5vmax;
    color:rgb(241,23,109);
  border: 1px solid rgb(241,23,109);
    padding: 3%;
  border-radius: 30px;
  width: 40%;
  margin-left: 3%;
  margin-right: 3%
  }
.gobtn:hover {
    margin-top:5%;
  font-size:1.5vmax;
    color:#ffffff;
    background-color:  rgb(241,23,109);
  border: 1px solid rgb(241,23,109);
    padding: 3%;
  border-radius: 30px;
  width: 40%;
  margin-left: 3%;
  margin-right: 3%
}
.oops {
  color: #ffffff;
  font-size:5vmax;
  font-weight: bolder;
  text-align: left;
}

.nottoworry{
    margin-top: 5%;
      text-align: left;
       font-size:1.5vmax;
    color: #ffffff;
  }

.background {
  font-size: 1rem;
  background-image: url("https://www.dropbox.com/s/2ct0i6kc61vp0bh/wall.jpg?raw=1");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-color: #141414;
  z-index: 100;
  width: 100% !important;
  top: 0px;
  display: flex;
  flex-flow: column;
 justify-content: center;
  align-items: center;
}

.logo {
  display: block;
  margin-top: 10%;
  font-family: "neontubes";
  text-align: center;
  }
  
  .open {
    font-size:10vmax;
    font-family: 'neontubes';
    color: rgb(255,200,238);
    text-shadow:
      0 0 0 transparent,
      0 0 10px rgb(241,23,109),
      /*0 5px 10px rgba(scale-color($clr1, $lightness: -80%),1),*/
      0 0 20px rgba(241,23,109,0.5),
      0 0 40px rgba(241,23,109,1),
      0 0 100px rgba(241,23,109,1),
      0 0 200px rgba(241,23,109,1),
      0 0 300px rgba(241,23,109,1),
      0 0 500px rgba(241,23,109,1),
      0 0 1000px rgba(241,23,109,1);
    animation : blink 4s infinite alternate;
  }
  .hrs {    font-size:5vmax;
        font-family: 'neontubes';
    color:#ffffff;
    text-shadow:
      0 0 0 transparent,
      /*0 5px 10px rgba(scale-color($clr2, $lightness: -80%),1),*/
      0 0 10px rgb(5, 211,250),
      0 0 20px rgba(5, 211,250, 0.5),
      0 0 40px rgba(5, 211,250, 1),
      0 0 100px rgba(5, 211,250, 1),
      0 0 200px rgba(5, 211,250, 1),
      0 0 300px rgba(5, 211,250, 1),
      0 0 500px rgba(5, 211,250, 1);
    animation: buzz 0.01s infinite alternate;
  }
  .gotoback {
    position: absolute;
    top: 10%;
    left: 5%;
    font-size:8vmax;
        font-family: 'neontubes';
    color:#ffffff;
    text-shadow:
      0 0 0 transparent,
      /*0 5px 10px rgba(scale-color($clr2, $lightness: -80%),1),*/
      0 0 10px#ffffff,
      0 0 20px rgba(250, 250, 250, 0.5),
      0 0 40px rgba(250, 250, 250,1),
      0 0 100px rgba(250, 250, 250,1),
      0 0 200px rgba(250, 250, 250,1),
      0 0 300px rgba(250, 250, 250, 1),
      0 0 500px rgba(250, 250, 250, 1);
    animation: buzz 0.01s infinite alternate;
  }

@keyframes buzz {
  70% { opacity: 0.80; }
}
@keyframes blink {
  40% { opacity: 1; }
  42% { opacity: 0.8; }
  43% { opacity: 1; }
  45% { opacity: 0.2; }
  46% { opacity: 1; }
}

</style>