<template>

    <div v-if="duplicated1" class="duplicateNotice">
    <div>본인 인증된 정보로 이미 가입된 이력이 존재합니다.</div>
    <br>
    <router-link to="/FindUserId"><button>아이디 찾기</button></router-link>
    </div>
    
    <div v-else-if="duplicated2" class="duplicateNotice">
    <div>해당 핸드폰번호로 가입된 이력이 존재합니다. 고객센터에 문의해주세요.</div>
    <br>
    <router-link to=""><button>메인으로</button></router-link>
    </div>
    
    <div v-else-if="certified" class="NaverJoinForm">    
    
    <br>
    <p style="font-family: AppleSDGothicNeoB; font-size: 20px">회원가입</p>
    
    <div class="row justify-content-center">   
    <div class="col-md-2">
    <div class="input-group">
    <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="1 1 14 14">
    <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0Zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4Zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10Z"/>
    </svg>
    </span>
    <input v-model="name" type="text" class="form-control" readonly>
    </div>
    </div>
    
    <div class="col-md-2">
    <div class="input-group">
    <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-telephone" viewBox="0 0 16 16">
    <path d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.568 17.568 0 0 0 4.168 6.608 17.569 17.569 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.678.678 0 0 0-.58-.122l-2.19.547a1.745 1.745 0 0 1-1.657-.459L5.482 8.062a1.745 1.745 0 0 1-.46-1.657l.548-2.19a.678.678 0 0 0-.122-.58L3.654 1.328zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"/>
    </svg>
    </span>
    <input v-model="phone" type="text" class="form-control" readonly>
    </div>
    </div>
    </div>
    
    <div class="row justify-content-center" style="margin-top:22px">
    <div class="col-md-4">
    <div class="input-group">
    <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
    <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
    </svg>
    </span>
    <input v-model="form.email" type="email" class="form-control" readonly>
    </div>
    </div>
    </div>
    
    <div class="row justify-content-center" style="margin-top:22px">
    <div class="col-md-4">
    <div class="input-group">
    <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-emoji-sunglasses" viewBox="0 0 16 16">
    <path d="M4.968 9.75a.5.5 0 1 0-.866.5A4.498 4.498 0 0 0 8 12.5a4.5 4.5 0 0 0 3.898-2.25.5.5 0 1 0-.866-.5A3.498 3.498 0 0 1 8 11.5a3.498 3.498 0 0 1-3.032-1.75zM7 5.116V5a1 1 0 0 0-1-1H3.28a1 1 0 0 0-.97 1.243l.311 1.242A2 2 0 0 0 4.561 8H5a2 2 0 0 0 1.994-1.839A2.99 2.99 0 0 1 8 6c.393 0 .74.064 1.006.161A2 2 0 0 0 11 8h.438a2 2 0 0 0 1.94-1.515l.311-1.242A1 1 0 0 0 12.72 4H10a1 1 0 0 0-1 1v.116A4.22 4.22 0 0 0 8 5c-.35 0-.69.04-1 .116z"/>
    <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-1 0A7 7 0 1 0 1 8a7 7 0 0 0 14 0z"/>
    </svg>
    </span>
    <input v-model="nickname" type="text" class="form-control" placeholder="닉네임 입력">
    <button @click="checkNickname">중복 체크</button>
    </div>
    </div>
    <span v-if="nicknameMsgBlank" id="checkMsg" style="color:transparent">공란</span> 
    <span v-if="!nickNameRexegValid" id="checkMsg" style="color:#FF0000">띄어쓰기 없이 8자리 미만으로 설정해주세요.</span>
    <span v-if="nickNameMsgBlank" id="checkMsg" style="color:transparent">공란</span> 
    <span v-else-if="!nicknameValid" id="checkMsg" style="color:#FF0000">중복된 닉네임입니다.</span>
    <span v-else-if="nicknameValid" id="checkMsg" style="color:blue">닉네임 설정이 완료되었습니다.</span>
    </div>
    
    <div> 약관 추가 예정.. ㅎ </div>
    
    <button @click="join">허니팟 시작하기</button>
    </div>
    
    <div v-else class="certificationNotice" style="margin-top:100px">
    <br>
    <div style="font-size:20px">허니팟은 100% 인증을 통해<br>믿을 수 있는 구독 환경을 제공합니다.</div><br>
    <div style="color:#BDBDBD">실명 인증을 진행해주세요.</div><br>
    <button @click="certification">확인</button>
    <br>
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
                nickNameMsgBlank: true,
                nickNameRexegValid: false,  // 닉네임 정규식 체크
                nickNameValid: false,       // 닉네임 중복 체크
                
                code: this.$route.query.code,
                state: this.$route.query.state,
                name: self.name,            // 본인인증한 이름 정보
                phone: self.phone,          // 본인인증한 핸드폰 번호 정보
                nickname: '',               // 네이버 회원과 허니팟 회원 간 닉네임 중복될 수 있어 새로 입력하는 것으로 설정
                snsType: '',
                form: {                     // 네이버에서 가져온 정보
                    email: 'self.form.email',
                    navertoken: '' 
                }
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
    
                self.$axios.post('http://localhost:8988/naver/login/' + self.code + '/' + self.state)
                .then(function (res) {
                    if (res.status == 200) {
                        self.form.email = res.data.userinfo.email;
                        self.form.navertoken = res.data.access_token;

                        console.log("res.form.email: " + self.form.email);

                        self.$axios.get('http://localhost:8988/members/naverCheck', { params: { email: self.form.email } })
                        .then(function (res) {
                            if (res.status == 200) {
                                if (!res.data.flag) {    // 중복된 네이버 아이디 없음
                                    console.log(res.data.flag);
                                } else if (res.data.flag2) {
                                    console.log(res.data.flag2);
                                    alert ("이미 동일한 네이버 메일로 가입된 이력이 존재합니다.\n고객센터로 문의해주세요.")
                                    window.location.href = "/";
                                } else if (res.data.flag) {
                                    sessionStorage.setItem('token', res.data.token);   
                                    sessionStorage.setItem('loginId', res.data.dto.email);
                                    sessionStorage.setItem('userNum', res.data.dto.userNum);
                                    sessionStorage.setItem('snsType', res.data.dto.snsType);
                                    window.location.href = "/";
                                }
                            }
                        });
                    }
                });
            },
    
            // 본인 인증
            certification() {
            const self = this;
    
            const IMP = window.IMP;
    
            IMP.init("imp24063873");
    
            IMP.certification({
                pg: 'MIIiasTest',
                merchant_uid: 'merchant_' + new Date().getTime(),
                m_redirect_url: "http://localhost:8989/member/HoneypotJoin", 
                popup: false
                }, function (rsp) {
                    if (rsp.success) {
                        console.log(rsp.imp_uid);
                        console.log(rsp.merchant_uid);
    
                    const data = {
                        imp_uid: rsp.imp_uid,
                    };
    
                    self.$axios.get("http://localhost:8988/members/certifications/redirect", { params: data })
                        .then(function (res) {
                            if (res.status == 200) {
                                console.log("name: " + res.data.name);
                                console.log("phone: " + res.data.phone);
                                console.log("회원가입 진행해도 되니: " + res.data.certified);
                                console.log("핸드폰번호 + 이름 중복이니: " + res.data.duplicated1);
                                console.log("핸드폰번호 중복이니: " + res.data.duplicated2);
    
                                self.name = res.data.name;
                                self.phone = res.data.phone;
    
                                if (res.data.certified) {   // 이 경우에만 회원가입 진행됨
                                    self.certified = true;
                                } else if (res.data.duplicated1) {  // 핸드폰 + 이름 중복
                                    self.duplicated1 = true;
                                } else if (res.data.duplicated2) {  // 핸드폰 중복
                                    self.duplicated2 = true;
                                }
    
                            } else {
                                alert('에러코드: ' + res.status)
                            }
                        }) 
                        .catch(function (error) {
                            console.error(error);
                        });
                    }
                });
            },
    
    
            // 닉네임 유효성 체크: 정규식 + 중복 체크
            checkNickname() {
                const self = this;
    
                // 닉네임 띄어쓰기, 8자 이하
                const nickNameRexeg = /^(?!.*\s).{1,8}$/;
                this.nickNameRexegValid = nickNameRexeg.test(this.nickname);
    
                // 닉네임 중복 체크
                if (this.nickNameRexegValid) {
                    console.log(this.nickname);
    
                    const data = {
                        nickname: self.nickname
                    };
    
                    self.$axios.get("http://localhost:8988/members/nicknameConfirm", { params: data } )
                    .then(function (res) {
                        if (res.status == 200) {
                            if (res.data.flag) {
                                self.nicknameValid = true;
                                self.nickNameMsgBlank = false;
                            } else {
                                self.nicknameValid = false;
                                self.nickNameMsgBlank = false;
                            }
                        } else {
                            alert('에러코드' + res.status);
                        }
                    })
                    .catch(function (error) {
                        console.error(error);
                    });
               }
            },
    
    
            // 회원가입 페이지에서 빈 값, 잘못된 값 등 있는지 체크
            checkEmpty(value) {
                if (value == '' || value == null || value == undefined) {
                    return false;
                } else {
                    return true;
                }
            },
    
    
            // 회원가입
            join() {
                const self = this;
    
                // 값이 모두 입력되어있고, 유효성 검사를 모두 통과한 경우만 회원가입 진행
                if (this.checkEmpty(self.form.email) && this.checkEmpty(self.nickname) && self.nicknameValid) {
                    const formdata = new FormData();
    
                    formdata.append('name', self.name);
                    formdata.append('phone', self.phone);
                    formdata.append('email', self.form.email);
                    formdata.append('pwd', "undefined");
                    formdata.append('nickname', self.nickname);
                    formdata.append('snsType', 2);
    
                    self.$axios.post("http://localhost:8988/members/join", formdata)
                    .then(function (res) {
                        if(res.status == 200) {
                            const dto = res.data.dto;
                            console.log(dto);
                            window.location.href = "/JoinComplete";
                        } else {
                            alert ('에러코드' + res.status)
                        }
                    });
                } else {
                    alert('입력되지 않은 값 혹은 유효성을 체크하지 않은 값이 존재합니다 \n다시 확인해주세요.')
                }
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
     
    #checkMsg {
        font-size: 11px;
        font-family: 'AppleSDGothicNeoR';
    }
    
    .form-control {
        font-family: 'AppleSDGothicNeoR';
        font-size: 15px;
    }
    
    input::-webkit-input-placeholder {
      color: #BDBDBD;
    }
    
    .certificationNotice {
        position: absolute;
        top: 20%;
        left: 40%;
        font-family: 'AppleSDGothicNeoB';
    }
    
    .duplicateNotice {
        position: absolute;
        top: 40%;
        left: 40%;
        font-family: 'AppleSDGothicNeoB';
    }
    
    
    </style>