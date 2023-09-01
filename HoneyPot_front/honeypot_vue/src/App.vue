<template>
 
  <nav>
    <span style="font-family: 'AppleSDGothicNeoB';
    font-size: 40px;    float: left;"> 허니팟 로고 </span>
    <router-link to="/">Home</router-link> |
    <router-link to="/HostBoardAdd">HostBoardAdd</router-link>|
    <router-link to="/HostBoardList">HostBoardList</router-link>|
    <router-link to="/MyPartyList">마이파티리스트</router-link> |
    <router-link to="/RegisterTest">RegisterTest</router-link> |
    <router-link to="/PartyTest1">PartyTest1</router-link> | <br/>
    <router-link to="/PartyTest2">PartyTest2</router-link> |
    <router-link to="/RecurringTest">RecurringTest</router-link>
    <router-link to="/JoinMainpage">회원가입</router-link> |
    <router-link to="/LoginPage">로그인</router-link> |
    <router-link to="/MyInfo">내정보</router-link> |
    <router-link to="/ChattingRoomList">채팅하기</router-link>
  </nav>
  <router-view/>
</template>

<style>

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
@font-face {
  font-family:'AppleSDGothicNeoB';
  src: url('assets/fonts/AppleSDGothicNeoB.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family:'AppleSDGothicNeoEB';
  src: url('assets/fonts/AppleSDGothicNeoEB.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family:'AppleSDGothicNeoH';
  src: url('assets/fonts/AppleSDGothicNeoH.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family:'AppleSDGothicNeoL';
  src: url('assets/fonts/AppleSDGothicNeoL.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family:'AppleSDGothicNeoM';
  src: url('assets/fonts/AppleSDGothicNeoM.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family:'AppleSDGothicNeoR';
  src: url('assets/fonts/AppleSDGothicNeoR.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family:'AppleSDGothicNeoSB';
  src: url('assets/fonts/AppleSDGothicNeoSB.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family:'AppleSDGothicNeoT';
  src: url('assets/fonts/AppleSDGothicNeoT.ttf') format('truetype');
  font-weight: 300;
}

@font-face {
  font-family:'AppleSDGothicNeoUL';
  src: url('assets/fonts/AppleSDGothicNeoUL.ttf') format('truetype');
  font-weight: 300;
}


nav {
  padding: 10px;
  background-color: #Fdd000;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
