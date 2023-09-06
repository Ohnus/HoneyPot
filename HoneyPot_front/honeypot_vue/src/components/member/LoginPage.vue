<template>
<br>

<div class="login">
<p style="font-family: AppleSDGothicNeoB; font-size: 20px; color: #444444; margin-top:100px">로그인</p>

<div class="row justify-content-center">
<div class="col-md-3" style="margin-bottom: 10px">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
<path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
<path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
</svg>
</span>
<input v-model="email" type="text" class="form-control" placeholder="아이디 입력">
</div>
</div>
</div>

<div class="row justify-content-center">
<div class="col-md-3" style="margin-bottom: 10px">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
<path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
</svg>
</span>
<input v-model="pwd" type="password" class="form-control" placeholder="비밀번호 입력">
</div>
</div>
</div>

<div class="row justify-content-center">
<div class="col-md-3" style="margin-top:10px; margin-bottom: 10px">
<button @click="login">허니팟 로그인</button>
</div>
</div>

<div class="row justify-content-center">
<hr class="col-md-3" id="line">
<p class="col-md-1">또는</p>
<hr class="col-md-3" id="line">
</div>

<div class="row justify-content-center">
<div class="col-md-3" style="margin-bottom: 10px">
<router-link to="/NaverLogin"><img src="../../assets/images/naverLogin.png" style="width:12%; margin-right:5px"></router-link>
<router-link to=""><img src="../../assets/images/kakaoLogin.png"  @click="kakaoLogin()" style="width:12%; margin-left:5px"></router-link>
</div>
</div>
<br>
<div class="row justify-content-center">
<div class="findMenu">
<router-link to="/JoinMainpage"><button class="findButton">회원가입</button></router-link> | 
<router-link to="/FindUserId"><button class="findButton">아이디 찾기</button></router-link> | 
<router-link to="/FindUserPwd"><button class="findButton">비밀번호 찾기</button></router-link>
</div>
</div>

</div>


</template>

<script>
export default {
    name: 'LoginPage',
    data() {
        return {
            email: '',     
            pwd: '' 
        }
    },

    methods: {

        login() {
            const self = this;
            const formdata = new FormData();

            formdata.append('email', self.email);
            formdata.append('pwd', self.pwd);

            console.log(self.email);
            console.log(self.pwd);

            self.$axios.post("http://localhost:8988/members/login", formdata)
            .then(function (res) {
                if (res.status == 200) {
                    if (res.data.flag) {
                        sessionStorage.setItem('token', res.data.token);    // 세션에 뭐뭐 넣어야하지..?
                        sessionStorage.setItem('loginId', res.data.email);
                        sessionStorage.setItem('userNum', res.data.userNum);

                        console.log(sessionStorage.getItem("loginId") + "/" + sessionStorage.getItem("userNum"));
                        
                        window.location.href = "/";
                        
                    } else {
                        alert ('로그인에 실패하였습니다. 로그인 정보를 다시 확인해주세요.')
                    }
                }
            })                
            .catch(function (error) {
                console.error(error);
            });
        },
        kakaoLogin(){
            const client_id='d836a71e352daf8a9987274ee1b74912'
            const redirect_uri='http://localhost:8989/KakaoJoin'
           
            const uri = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${client_id}&redirect_uri=${redirect_uri}`;
          
            window.location.replace(uri);
        }

    }
}



</script>

<style scoped>
button {
    padding: 5px 13px;
    text-align: center;
    text-decoration: none;
    display: inline-block;

    transition-duration: 0.4s;
    cursor: pointer;
    background-color: #Fdd000;
    color: #444444;
    border: 2px solid #Fdd000;
    border-radius: 7px;

    font-weight: 500; /*폰트 굵기 -> 글씨체 적용하면 좀 바뀔 것 같은데 굵은게 예쁠것 같음 */
    font-size: 14px; /* 지금 버튼 사이즈에는 이게 딱임 */
    font-family: 'AppleSDGothicNeoB';

}

button:hover {
  background-color: white;
  color: #444444;
  font-family: 'AppleSDGothicNeoB';
}

#line {
  border: none;
  height: 1px; /* 가로선의 높이를 조절하세요. */
  background-color: #bdbdbd; /* 가로선의 색상을 설정하세요. */
  margin-top: 10px; /* 가로선 위 여백을 조절하세요. */
  margin-bottom: 10px; /* 가로선 아래 여백을 조절하세요. */
  /* margin-right: -100px;
  margin-left: -100px; */
  width: 115px;
}

.findMenu {
  font-family: 'AppleSDGothicNeoR';
  fort-size:13px;
  color: #BDBDBD;
}

.findButton {
  background-color: transparent;
  border: 0px;
  font-family: 'AppleSDGothicNeoM';
  font-size: 13px;
  color: #BDBDBD;

}

input::-webkit-input-placeholder {
  color: #BDBDBD;
}

.form-control {
  font-family: 'AppleSDGothicNeoR';
  font-size: 15px;
}

p {
  font-family: 'AppleSDGothicNeoR';
  font-size: 13px;
  color: #BDBDBD;
}



</style>