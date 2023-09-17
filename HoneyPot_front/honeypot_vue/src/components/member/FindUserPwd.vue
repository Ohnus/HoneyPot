<template>
<br>

<p style="font-family: AppleSDGothicNeoB; font-size: 20px; color: #444444; margin-top:80px">비밀번호 찾기</p>


<div v-show="inputInfo">

<div class="row justify-content-center" style="z-index 1;">   
<div class="authbox">
<p id="notice">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-exclamation-triangle" viewBox="0 0 16 16">
<path d="M7.938 2.016A.13.13 0 0 1 8.002 2a.13.13 0 0 1 .063.016.146.146 0 0 1 .054.057l6.857 11.667c.036.06.035.124.002.183a.163.163 0 0 1-.054.06.116.116 0 0 1-.066.017H1.146a.115.115 0 0 1-.066-.017.163.163 0 0 1-.054-.06.176.176 0 0 1 .002-.183L7.884 2.073a.147.147 0 0 1 .054-.057zm1.044-.45a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566z"/>
<path d="M7.002 12a1 1 0 1 1 2 0 1 1 0 0 1-2 0zM7.1 5.995a.905.905 0 1 1 1.8 0l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995z"/>
</svg> 회원가입 시 등록한 정보를 입력해주시기 바랍니다.</p>

<div class="row justify-content-center" style="margin-top:15px">
<div class="col-md-8">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="1 1 14 14">
<path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0Zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4Zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10Z"/>
</svg>
</span>
<input v-model="name" type="text" class="form-control" placeholder="이름 입력">
</div>
</div>
</div>

<div class="row justify-content-center" style="margin-top:15px">
<div class="col-md-8">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
<path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
</svg>
</span>
<input v-model="email" type="email" class="form-control" placeholder="이메일 입력(email@email.com)">
<button class="button" @click="sendEmail">메일 인증</button>
</div>
</div>
</div>

<div class="row justify-content-center" style="margin-top:15px">
<div class="col-md-8">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check2-square" viewBox="0 0 16 16">
<path d="M3 14.5A1.5 1.5 0 0 1 1.5 13V3A1.5 1.5 0 0 1 3 1.5h8a.5.5 0 0 1 0 1H3a.5.5 0 0 0-.5.5v10a.5.5 0 0 0 .5.5h10a.5.5 0 0 0 .5-.5V8a.5.5 0 0 1 1 0v5a1.5 1.5 0 0 1-1.5 1.5H3z"/>
<path d="m8.354 10.354 7-7a.5.5 0 0 0-.708-.708L8 9.293 5.354 6.646a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0z"/>
</svg>
</span>
<input v-model="enterCode" type="text" class="form-control" placeholder="인증번호 입력">
<button class="button" @click="authCodeCheck">확인</button>
</div>
</div>
</div>
<span v-if="authCodeMsgBlank" id="checkMsg" style="color:transparent">''</span> 
<span v-else-if="!authCodeValid" id="checkMsg" style="color:red">인증코드를 재확인해주세요.</span>
<span v-else-if="authCodeValid" id="checkMsg" style="color:blue">이메일 인증이 완료되었습니다.</span>

<div style="margin-top:15px; z-index: 2;">
<button class="button" @click="updatePwPage">비밀번호 재설정</button>
</div>
</div>
</div>
</div>
 
<div v-show="resetPw" class="resetPw">
    
<div class="row justify-content-center" style="z-index 1;">   
<div class="authbox">
<div class="row justify-content-center" style="margin-top:35px">
<p style="font-family: AppleSDGothicNeoB; font-size: 14px; color: #444444; margin-top:35px">비밀번호 재설정</p>
<div class="col-md-5" style="margin-top: 15px">
<div class="input-group">
<span class="input-group-text" id="basic-addon1" style="background-color: transparent">
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
<path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
</svg>
</span>
<input v-model="pwd" type="password" class="form-control" placeholder="비밀번호 입력" @blur="checkPwd">
</div>
<span v-if="pwdMsgBlank" id="checkMsg" style="color:transparent">'</span> 
<span v-else-if="!pwdMsgBlank" v-show="!pwdValid" id="checkMsg" style="color:red">대문자, 특수문자 포함 8자리 이상으로 설정</span>
</div>

<div class="col-md-5"  style="margin-top: 15px">
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
    

<button class="button" @click="updatePw" style="margin-top:15px">확인</button>

</div>
</div>
</div>

</template>

<script> 
export default {
    name: 'FindUserPwd',
    data() {
        return {
            inputInfo: true,
            resetPw: false,
            authCodeValid: false,
            pwdValid: false,
            pwdCheckValid: false,
            authCodeMsgBlank: true,
            pwdMsgBlank: true,
            isSentEmail: false,

            name: '',
            email: '',
            enterCode: '',
            pwd: '',
            pwdCheck: ''
           
        }
    },

    methods: {
        sendEmail() {
            const self = this;

            const data = {
                name: self.name,
                email: self.email
            }

            console.log(self.name + " / " + self.email);

            const formdata = new FormData();
            formdata.append('email', self.email);
            
            console.log(self.email);

            if (self.name == '' || self.email == '') {
                alert('입력하지 않은 항목이 있습니다. 다시 확인해주세요.');
            } else {
                self.$axios.get("http://localhost:8988/members/getId2", { params: data } )
                .then(function (res) {
                    if(res.status == 200) {
                        if (!res.data.flag) {
                            alert("입력하신 정보와 일치하는 아이디가 없습니다.\n다시 확인해주세요.");

                        } else if (res.data.snsType != "허니팟") {
                            alert("허니팟 외의 계정은 해당 계정 서비스에서 비밀번호를 찾아주세요.");

                        } else if (res.data.snsType == "허니팟") {
                            self.userNum = res.data.userNum;
                            self.name = res.data.name;
                            self.email = res.data.email;
                            self.snsType = res.data.snsType;

                            console.log(self.name + " / " + self.email + " / " + self.snsType);

                            self.$axios.post("http://localhost:8988/members/findIdEmailConfirm", formdata)
                            .then(function (res2) {
                                if (res2.status == 200) {
                                    self.authCode = res2.data.authCode;
                                    alert("인증 메일이 전송되었습니다." );
                                    console.log(self.authCode);
                                    
                                    self.isSentEmail = true;
                                } else {
                                    alert('에러코드: ' + res2.status);
                                }
                            })
                            .catch(function (error) {
                                console.error(error);
                            });
                        } 
                    }
                })
                .catch(function (error) {
                    console.error(error);
                });
            }
        },

        // 메일 인증번호 유효여부 체크
        authCodeCheck() {
            if (this.enterCode == '') {
                alert('인증번호를 입력해주세요');
            } else {
                if (this.enterCode == this.authCode) {
                    this.authCodeMsgBlank = false;
                    this.authCodeValid = true;
                } else {
                    this.authCodeMsgBlank = false;
                    this.authCodeValid = false;
                }
            }
        },

        checkEmpty(value) {
            if (value == '' || value == null || value == undefined) {
                return false;
            } else {
                return true;
            }
        },

        updatePwPage() {
            if (this.checkEmpty(this.name) && this.checkEmpty(this.email) && this.checkEmpty(this.enterCode) && this.isSentEmail && this.authCodeValid) {
                this.inputInfo = false;
                this.resetPw = true;
            } else {
                alert ("입력되지 않은 값이 존재하거나 인증코드가 유효하지 않습니다. 다시 확인해주세요.")
            }
        },

        // 비밀번호 정규식
        checkPwd() {
            const pwdRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{8,})/;   // 8자리 + 대문자 및 특수문자 포함
            this.pwdValid = pwdRegex.test(this.pwd);
            this.pwdMsgBlank = false;
        },


        // 비밀번호 일치 여부 체크
        samePwdCheck() {
            if (this.pwd == this.pwdCheck) {
                this.pwdMsgBlank = false;
                this.pwdCheckValid = true;
            } else {
                this.pwdMsgBlank = false;
                this.pwdCheckValid = false;

            }
        },

        updatePw() {
            const self = this;
            console.log("UserNum: " + this.userNum)

            const formdata = new FormData();

            if (self.pwdValid && !self.pwdCheckValid) {
                alert ('입력하신 비밀번호가 서로 일치하지 않습니다.')
            } else if (!self.pwdValid && self.pwdCheckValid) {
                alert ('입력하신 비밀번호가 조건을 충족하지 않습니다.')
            } else if (self.pwdValid && self.pwdCheckValid) {
                formdata.append('pwd', self.pwd);

                self.$axios.post("http://localhost:8988/members/editPwd/" + this.userNum, formdata)
                .then(function (res) {
                    if(res.status == 200) {
                        const dto = res.data.dto;
                        console.log(dto);

                        window.location.href = "/LoginPage";
                    } else {
                        alert ('에러코드' + res.status)
                    }
                })
                .catch(function (error) {
                    console.error(error);
                });
            } 
        }
    }
}

</script>


<style scoped>

button:hover {
  background-color: white;
  color: #444444;
  font-family: 'AppleSDGothicNeoB';
} 


.button {
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

    font-weight: 500;
    font-size: 14px; 
    font-family: 'AppleSDGothicNeoB';
}


.authbox {
  border: 1.5px solid #bdbdbd;
  background-color: transparent;
  width: 600px;
  height: 300px;
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

#notice {
    font-family: 'AppleSDGothicNeoR';
    font-size: 13px;
    color: #bdbdbd;
    text-align: left;
    margin-left: 100px;
    margin-top: 30px;
}

#info {
    font-family: 'AppleSDGothicNeoSB';
    font-size: 20px;
}



</style>