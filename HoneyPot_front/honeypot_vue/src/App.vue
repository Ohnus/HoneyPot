<template>
  <nav v-if="userNum != null">
    <span style="font-family: 'AppleSDGothicNeoB';
    font-size: 34px;  float: left;"> HoneyPot <img :src="require('@/assets/images/honey.png')"
        style="width: 40px; height: 40px;">
    </span>
    <span class="nav-links">
      <router-link to="/HostBoardAdd" class="nav-link" ><span class="nav-word">파티생성</span></router-link>
      <router-link to="/HostBoardList" class="nav-link"><span class="nav-word" >파티찾기</span></router-link>
      <span class="nav-link" @click="logout" ><span class="nav-word">로그아웃</span></span>
      <router-link to="MyInfo" class="nav-link" >여기에 이미지 넣는거 멤버에서 ㅎ ㅐ줘</router-link>
    </span>
  </nav>
  <router-view />
</template>
 <script>
export default {
    data() {
        return {
        }
      },
      created() {
    this.userNum = sessionStorage.getItem('userNum')
    console.log(this.userNum);
  },
methods: {
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
.nav-word{
  font-family: 'AppleSDGothicNeoB';
  font-size: 18px;
  color: #444444;
  cursor: pointer;
}
</style>
