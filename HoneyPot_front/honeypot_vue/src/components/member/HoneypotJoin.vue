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

<div v-else-if="certified" class="HoneypotJoinForm">    

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
<input v-model="email" type="email" class="form-control" placeholder="이메일 입력(email@email.com)">
<button @click="sendEmail">메일 인증</button>
</div>
</div>
</div>

<div class="row justify-content-center" style="margin-top:22px">
<div class="col-md-4">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check2-square" viewBox="0 0 16 16">
<path d="M3 14.5A1.5 1.5 0 0 1 1.5 13V3A1.5 1.5 0 0 1 3 1.5h8a.5.5 0 0 1 0 1H3a.5.5 0 0 0-.5.5v10a.5.5 0 0 0 .5.5h10a.5.5 0 0 0 .5-.5V8a.5.5 0 0 1 1 0v5a1.5 1.5 0 0 1-1.5 1.5H3z"/>
<path d="m8.354 10.354 7-7a.5.5 0 0 0-.708-.708L8 9.293 5.354 6.646a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0z"/>
</svg>
</span>
<input v-model="enterCode" type="text" class="form-control" placeholder="인증번호 입력">
<button @click="authCodeCheck">확인</button>
</div>
</div>
</div>
<span v-if="authCodeMsgBlank" id="checkMsg" style="color:transparent">'</span> 
<span v-else-if="!authCodeValid" id="checkMsg" style="color:red;">인증코드를 재확인해주세요.</span>
<span v-else-if="authCodeValid" id="checkMsg" style="color:blue">이메일 인증이 완료되었습니다.</span>


<div class="row justify-content-center">
<div class="col-md-2">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
<path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
</svg>
</span>
<input v-model="pwd" type="password" class="form-control" placeholder="비밀번호 입력" @blur="checkPwd">
</div>
<span v-show="!pwdValid" id="checkMsg" style="color:red">대문자, 특수문자 포함 8자리 이상으로 설정해주세요.</span>
</div>

<div class="col-md-2">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
<path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/>
</svg>
</span>
<input v-model="pwdCheck" type="password" class="form-control" placeholder="비밀번호 재입력" @blur="samePwdCheck">
</div>
<span v-if="pwdMsgBlank" id="checkMsg" style="color:transparent">'</span> 
<span v-else-if="!pwdCheckValid" id="checkMsg" style="color:#FF0000">비밀번호가 일치하지 않습니다.</span>
<span v-else-if="pwdCheckValid" id="checkMsg" style="color:blue">비밀번호가 일치합니다.</span>
</div>
</div>


<div class="row justify-content-center">
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
<span v-if="!nickNameRexegValid" id="checkMsg" style="color:#FF0000">띄어쓰기 없이 8자리 미만으로 설정해주세요.</span>
<span v-if="nickNameMsgBlank" id="checkMsg" style="color:transparent"></span> 
<span v-else-if="!nicknameValid" id="checkMsg" style="color:#FF0000">중복된 닉네임입니다.</span>
<span v-else-if="nicknameValid" id="checkMsg" style="color:blue">닉네임 설정이 완료되었습니다.</span>
</div>


<div class="row justify-content-center" style="margin-top:22px; margin-bottom:22px">   
<div class="col-md-4">

<div class="terms">
<br>
<p style="font-family: 'AppleSDGothicNeoR'; color:#BDBDBD; font-size:15px">회원가입을 위해 이용약관에 동의해주세요.</p>

<label class="container"><span style="color:#Fdd000; font-weight: bold;"> (필수)</span> 이용 약관
  <input type="checkbox" v-model ="serviceAgree">
  <span class="checkmark"></span>
</label>
<label class="container"><span style="color:#Fdd000; font-weight: bold;"> (필수)</span> 개인정보 수집 및 이용 동의
  <input type="checkbox" v-model="personalInfoAgree">
  <span class="checkmark"></span>
</label>

</div>
</div>
</div>


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
    name: 'HoneypotJoin',
    data() {
        return {
            duplicated1: false,         // 핸드폰 + 이름 중복, true일 경우 회원가입 불가
            duplicated2: false,         // 핸드폰 중복, true일 경우 회원가입 불가
            certified: false,           // 중복 값 없는 상태, true일 경우 회원가입 가능
            authCodeValid: false,       // 이메일 인증번호 유효성 체크 
            pwdValid: true,            // 비밀번호 정규식 체크
            pwdCheckValid: true,       // 비밀번호 일치 여부 체크
            nickNameRexegValid: true,  // 닉네임 정규식 체크
            nickNameValid: true,       // 닉네임 중복 체크
            authCodeMsgBlank: true,
            pwdMsgBlank: true,
            nickNameMsgBlank: true,
            serviceAgree: false,
            personalInfoAgree: false,
            
            name: self.name,    // 본인인증한 이름 정보
            phone: self.phone,  // 본인인증한 핸드폰 번호 정보
            email: '',
            enterCode: '',      // 이메일 인증번호 입력값
            pwd: '',
            pwdCheck: '',       // 비밀번호 재입력
            nickname: '',
            snsType: ''
        }
    },

    methods: {

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


        // 인증 메일 발송      
        sendEmail() {
            const self = this;

            if (this.email == '') {
                alert('이메일을 입력해주세요.');
            } else {
                let formdata = new FormData();
                formdata.append('email', self.email);
                
                console.log(this.email);

                self.$axios.post("http://localhost:8988/members/emailConfirm", formdata)
                .then(function (res) {
                    if(res.status == 200) {
                        if (res.data.flag) {
                            alert("중복된 메일이 존재합니다. 다시 확인해주세요.")
                        } else {
                            self.authCode = res.data.authCode;
                            alert("인증 메일이 전송되었습니다.");
                            console.log(self.authCode);
                        }
                    } else {
                        alert('에러코드: ' + res.status);
                    }
                })
                .catch(function (error) {
                    console.error(error);
                });
            }
        },


        // 인증번호 유효 여부 체크
        authCodeCheck() { // 
            if (this.enterCode == '') {
                alert('인증번호를 입력해주세요');
            } else {
                if (this.enterCode == this.authCode) {
                    this.authCodeValid = true;
                    this.authCodeMsgBlank = false;
                } else {
                    this.authCodeValid = false;
                    this.authCodeMsgBlank = false;
                }
            }
        },

        // 비밀번호 정규식
        checkPwd() {
            const pwdRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{8,})/;   // 8자리 + 대문자 및 특수문자 포함
            this.pwdValid = pwdRegex.test(this.pwd);
        },


        // 비밀번호 일치 여부 체크
        samePwdCheck() {
            if (this.pwd == this.pwdCheck) {
            this.pwdCheckValid = true;
            this.pwdMsgBlank = false;
            } else {
                this.pwdCheckValid = false;
                this.pwdMsgBlank = false;
            }
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
            if (this.checkEmpty(self.email) && this.checkEmpty(self.pwd) && this.checkEmpty(self.pwdCheck) && this.checkEmpty(self.nickname)
                && self.authCodeValid && self.pwdValid && self.pwdCheckValid && self.nicknameValid && self.serviceAgree && self.personalInfoAgree) {
                const formdata = new FormData();

                formdata.append('name', self.name);
                formdata.append('phone', self.phone);
                formdata.append('email', self.email);
                formdata.append('pwd', self.pwd);
                formdata.append('nickname', self.nickname);
                formdata.append('snsType', 0);

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
};


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

.terms {
    border: 1px solid #ece9e9;
    border-radius: 7px;
    font-family: 'AppleSDGothicNeoR';
    font-size: 13px;
    color: #444444;
}

.container {
  display: block;
  position: relative;
  padding-left: 150px;
  margin-bottom: 12px;
  cursor: pointer;
  font-size: 12px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  font-family: 'AppleSDGothicNeoR';
  text-align: left;
}

/* Hide the browser's default checkbox */
.container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

/* Create a custom checkbox */
.checkmark {
  position: absolute;
  top: 2px;
  left: 125px;
  height: 13px;
  width: 13px;
  background-color: #e1e0e0;
}

/* On mouse-over, add a grey background color */
.container:hover input ~ .checkmark {
  background-color: #e1e0e0;
}

/* When the checkbox is checked, add a blue background */
.container input:checked ~ .checkmark {
  background-color: #Fdd000;
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

/* Show the checkmark when checked */
.container input:checked ~ .checkmark:after {
  display: block;
}

/* Style the checkmark/indicator */
.container .checkmark:after {
  left: 4.5px;
  top: 1px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}


</style>