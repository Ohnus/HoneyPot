<template>

<div class="NaverJoinForm">
        <input v-model="name" type="text" readonly><br>

        <input v-model="phone" type="text" readonly><br>

        <input v-model="email" type="email" readonly><br>

        <div>
        <input v-model="pwd" type="password" placeholder="비밀번호 입력" @input="checkPwd">
        <span v-if="!pwdValid" style="color:red">대문자, 특수문자 포함 8자리 이상으로 설정해주세요.</span>
        </div>

        <div>
        <input v-model="pwdCheck" type="password" placeholder="비밀번호 재입력" @input="samePwdCheck">

        </div>

        <!-- @input으로 처리하면 검색을 못하고 @blur로 처리하니까 검색은 잘 되는데 필드를 벗어나야 검사를 하는듯..? 그리고 한 번 검사 끝나면 중복값 다시 넣어도 메서드 재실행 안됨.. -->
        <div>
        <input v-model="nickname" type="text" placeholder="닉네임 입력" @blur="checkNickname"><br> 

        </div>

        <div> 약관 추가 예정.. ㅎ </div>

        <button @click="join">가입</button>

    </div>

</template>

<script>
export default {
    name: 'NaverJoin',
    data() {
        return {
            duplicated1: false,         // 핸드폰 + 이름 중복, true일 경우 회원가입 불가
            duplicated2: false,         // 핸드폰 중복, true일 경우 회원가입 불가
            certified: false,           // 중복 값 없는 상태, true일 경우 회원가입 가능
            pwdValid: false,            // 비밀번호 정규식 체크
            pwdCheckValid: false,       // 비밀번호 일치 여부 체크
            nickNameRexegValid: false,  // 닉네임 정규식 체크
            nickNameValid: false,       // 닉네임 중복 체크
            
            code: this.$route.query.code,
            state: this.$route.query.state,
            name: self.name,    // 본인인증한 이름 정보
            phone: self.phone,  // 본인인증한 핸드폰 번호 정보
            email: self.email,   
            pwd: self.pwd,      
            nickname: '',       // 네이버 회원과 허니팟 회원 간 닉네임 중복될 수 있어 새로 입력하는 것으로 설정
            snsType: '',
        }
    },
    created() {
        this.code = this.$route.query.code;
        this.state = this.$route.query.state;
        
        console.log("code: " + this.code + " / " + "state: " + this.state);

        this.getToken(this.code, this.state);        
    },

    methods: {

        getToken() {
            const self = this;

            self.$axios.post('http://localhost:8988/naver/login' + self.code + '/' + self.state)
            .then(function (res) {
                if (res.status == 200) {
                    if (res.data.userinfo.message) {
                        alert(res.data.userinfo.message);
                        location.href = "/";
                    } else {
                        console.log(res.data);

                        self.form.email = res.data.userinfo.naverResponse.email;
                        self.form.pwd = res.data.userinfo.naverResponse.pwd;
                        self.form.navertoken = res.data.access_token;
                    }
                    self.$axios.get('http://localhost:8988/members/emailCheck/' + self.form.email)
                    .then(function (res) {
                        if (res.status == 200) {
                            if (res.data.flag) {    // 중복된 이메일이 있을 때
                                console.log(res.data.flag);
                            } else {
                                self.Nlogin(); // Nlogin() 뭐지?
                            }
                        }
                    });
                }
            });
        },

        Nlogin() {
      const self = this;
      const loginform = new FormData();
      loginform.append('email', self.form.email)
      loginform.append('pwd', self.form.pwd)
      self.$axios.post('http://localhost:8181/members/login', loginform)
        .then(function (res) { //결과 
          if (res.status == 200) {
            sessionStorage.setItem('token', res.data.token)
            sessionStorage.setItem('loginId', res.data.loginId)
            const addform = new FormData();
            addform.append('email', self.form.email)
            addform.append('token', self.form.navertoken)
            console.log(self.form.navertoken)
            self.$axios.post('http://localhost:8988/naver/token', addform)
              .then(function (rep) {
                if (rep.status == 200) {
                  location.href = "/"
                } else {
                  alert('여기서 오류다~ ')
                }
              });
          }
        })
    },
      // state 난수 생성
      generateRandomState() {
            const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            let state = "";
      
            for (let i = 0; i < 16; i++) {
                const randomIndex = Math.floor(Math.random() * characters.length);
                state += characters.charAt(randomIndex);
            }
            return state;
        },

        naverLogin() {
            let client_id = "uW8BXEv6IHbwQSCwO9jn";
            let redirect_uri = encodeURIComponent("http://localhost:8989/NaverJoin", "UTF-8");
            const state = this.generateRandomState();
            const apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
             + "&client_id=" + client_id
             + "&redirect_uri=" + redirect_uri
                + "&state=" + state
            window.location.href = apiURL;
        }
  
    }
        }
    




</script>