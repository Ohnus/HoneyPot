<template>
<br>

<p style="font-family: AppleSDGothicNeoB; font-size: 20px; color: #444444; margin-top:80px">비밀번호 찾기</p>


<div v-show="findPwProcedure">
<input v-model="name" placeholder="이름 입력">
<input v-model="email" placeholder="아이디(메일주소) 입력">
<button @click="sendEmail">인증 메일 전송</button>
<div v-if="findId">
<br>아이디를 모르겠다면 <router-link to="/FindUserId">아이디 찾기</router-link>
</div>

<div v-show="isSentEmail" class="authEmail">
<input v-model="enterCode" type="text" placeholder="인증번호 입력">
<button @click="authCodeCheck">확인</button><br>
<span v-if="!authCodeValid" style="color:red">인증코드를 재확인해주세요.</span>
</div>
</div>

<div v-show="resetPw" class="resetPw">
<div>
<input v-model="pwd" type="password" placeholder="비밀번호 입력" @input="checkPwd">
<span v-if="!pwdValid" style="color:red">대문자, 특수문자 포함 8자리 이상으로 설정해주세요.</span><br>
<input v-model="pwdCheck" type="password" placeholder="비밀번호 재입력" @input="samePwdCheck">
<span v-if="!pwdCheckValid" style="color:#FF0000">비밀번호가 일치하지 않습니다.</span> 
<span v-else-if="pwdCheckValid" style="color:blue">비밀번호가 일치합니다.</span><br>
<button @click="updatePw">확인</button>
</div>
</div>

</template>

<script> 
export default {
    name: 'FindUserPwd',
    data() {
        return {
            findPwProcedure: true,
            findId: true,
            isSentEmail: false,
            authCodeValid: true,
            resetPw: false,
            pwdValid: true,
            pwdCheckValid: false,

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
            self.findId = false;

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
                    this.findPwProcedure = false;
                    this.resetPw = true;
                } else {
                    this.authCodeValid = false;
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
            } else {
                this.pwdCheckValid = false;
            }
        },

        updatePw() {
            const self = this;

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