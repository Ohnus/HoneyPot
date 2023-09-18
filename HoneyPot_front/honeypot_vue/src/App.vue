<template>
  <nav v-if="userNum != null">
    <router-link to="/">
    <span class="nav-word" style="font-family:'GmarketSansTTFBold'; font-size:28px; float:left;"> HoneyPot </span></router-link>
    <span><img :src="require('@/assets/images/honeypotLogo2.png')" style="width:65px; height:40px; margin-top:-2px; float:left"></span>
    
    <span class="nav-links" style="margin-top:0px">
      <router-link to="/HostBoardAdd" class="nav-link" ><span class="nav-word">파티생성</span></router-link>
      <router-link to="/HostBoardList" class="nav-link"><span class="nav-word" >파티찾기</span></router-link>
      <router-link to="/ChattingRoomList" class="nav-link" ><span class="nav-word">채팅하기</span></router-link>
      <span class="nav-link" @click="logout" ><span class="nav-word">로그아웃</span></span>
      <router-link to="/MyinfoSidebar"><div class="userInfoNav"><img :src="profile"
        style="width: 45px; height: 45px; border-radius:50%; margin-top:-2px; background-color:white"></div></router-link>
    </span>
  </nav>
  <router-view />
</template>


<script>
export default {
  data() {
    return {
      profile:'',
    }
  },

  created() {
    this.userNum = sessionStorage.getItem('userNum')
    this.profileImg();

  },
    
  methods: {
    profileImg() {
      if (this.profile != null) {
        this.profile = 'http://localhost:8988/members/imgs/' + this.userNum;
      } else {
        this.profile = require('@/assets/images/BasicUserImg.png');
      }
    },

    logout() {
      sessionStorage.clear();
      window.location.href = "/";
    }
  },
}

</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

@font-face {
  font-family: 'AppleSDGothicNeoB';
  src: url('assets/fonts/AppleSDGothicNeoB.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'AppleSDGothicNeoEB';
  src: url('assets/fonts/AppleSDGothicNeoEB.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'AppleSDGothicNeoH';
  src: url('assets/fonts/AppleSDGothicNeoH.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'AppleSDGothicNeoL';
  src: url('assets/fonts/AppleSDGothicNeoL.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'AppleSDGothicNeoM';
  src: url('assets/fonts/AppleSDGothicNeoM.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'AppleSDGothicNeoR';
  src: url('assets/fonts/AppleSDGothicNeoR.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'AppleSDGothicNeoSB';
  src: url('assets/fonts/AppleSDGothicNeoSB.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'AppleSDGothicNeoT';
  src: url('assets/fonts/AppleSDGothicNeoT.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'AppleSDGothicNeoUL';
  src: url('assets/fonts/AppleSDGothicNeoUL.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'GmarketSansTTFBold';
  src: url('assets/fonts/GmarketSansTTFBold.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'GmarketSansTTFLight';
  src: url('assets/fonts/GmarketSansTTFLight.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family: 'GmarketSansTTFMedium';
  src: url('assets/fonts/GmarketSansTTFMedium.ttf') format('truetype');
  font-weight: 300;
}

nav {
  padding: 10px;
  background-color: #Fdd000;
  height : 60px;
}

.nav-links {
  float: right; 
  display: flex; 
  align-items: center;
  margin-right: 20px; 
  margin-top :8px;
}

.nav-link {
  text-decoration: none;
  color: #444444;
  margin-right: 20px; 
}

.nav-word {
  font-family: 'GmarketSansTTFMedium';
  font-size: 16px;
  color: #444444;
  cursor: pointer;
}

.userInfoNav {
  width: 45px;
  height: 45px;
}

</style>
