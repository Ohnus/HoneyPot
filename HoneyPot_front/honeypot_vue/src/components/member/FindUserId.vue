<template>
<br>

<p style="font-family: AppleSDGothicNeoB; font-size: 20px; color: #444444; margin-top:80px">아이디 찾기</p>

<div v-show="findIdProcedure">

<div class="row justify-content-center">   
<div v-show="select" class="select">
<button class="selectbutton1" @click=phoneCertification>본인 인증</button>
<button class="selectbutton2" @click="emailCertification">이메일 인증</button>
</div>
</div>
<br>

<div v-show="selectCertification">
<div class="row justify-content-center">   
<div class="authbox1">
<div><img id="phoneImg" src="../../assets/images/phone.png" style="width:90px; height: 130px"></div>
<div class="p1">휴대전화 인증</div>
<div class="p2">본인 명의의 휴대전화를 통한 인증입니다</div>
<div><button class="cbutton" @click="certification">인증하기</button></div>
</div>
</div>
</div>

<div v-show="selectEmailCertification" class="inputInfo">
<div class="row justify-content-center">   
<div class="authbox2">
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
<span v-if="authCodeMsgBlank" id="checkMsg" style="color:transparent">공란</span> 
<span v-else-if="!authCodeValid" id="checkMsg" style="color:red">인증코드를 재확인해주세요.</span>
<span v-else-if="authCodeValid" id="checkMsg" style="color:blue">이메일 인증이 완료되었습니다.</span>

<div class="row justify-content-center" style="margin-top:10px">
<div class="col-md-8">
<button class="button" @click="findIdMethod">아이디 찾기</button>
</div>
</div>
</div>
</div>
</div>
</div>

<div v-show="getId" class="getId">
<div v-if="findId"> 
<span id="info">{{ name }}</span> 님은 <span id="info">{{ snsType }}</span> 계정으로 가입하셨습니다. <br>
아이디는 <span id="info">{{ email }}</span> 입니다. <br>
<br>
<router-link to="/LoginPage"><button class="button">로그인 페이지로 이동</button></router-link>
</div>
<div v-else-if="!findId">
<span id="info">인증하신 정보와 일치하는 아이디가 없습니다.</span>
</div>

</div>

</template>

<script>
export default {
    name: 'FindUserId',
    data() {
        return {
            findIdProcedure: true,
            select: true,
            selectCertification: true,
            selectEmailCertification: false,
            getId: false,
            findId: false,
            isSentEmail: false,
            authCodeValid: true,
            authCodeMsgBlank: true,

            name: '',
            email:'',
            snsType:'',
            enterCode: ''

        }
    },
    methods: {

        phoneCertification() {    
            const self = this;
            self.selectCertification = true;         // 핸드폰 인증 관련 div 보여줌
            self.selectEmailCertification = false;   
        },

        emailCertification() {    
            const self = this;
            self.selectEmailCertification = true;   // 이메일 인증 관련 div 보여줌
            self.selectCertification = false;
        },



        // 핸드폰 본인 인증
        certification() {
            const self = this;
            // self.selectEmailCertification = false;  // 이메일 인증 관련 div 숨김

            const IMP = window.IMP;
            IMP.init("imp24063873");

            IMP.certification({
                pg: 'MIIiasTest',
                merchant_uid: 'merchant_' + new Date().getTime(),
                m_redirect_url: "http://localhost:8989/member/FindUserId", 
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

                            const data = {
                                name: res.data.name,
                                phone: res.data.phone
                            };

                            console.log(data);

                            self.$axios.get('http://localhost:8988/members/getId', { params: data })
                            .then(function (res2) {
                                if (res2.status == 200) {
                                    self.name = res2.data.name;
                                    self.email = res2.data.email;
                                    self.snsType = res2.data.snsType;

                                    self.findId = true;
                                } else {
                                    self.findId = false;
                                }
                            })
                            .catch(function (error) {
                                console.error(error);
                            });
                        } else {
                            alert('에러코드: ' + res.status);
                        }

                        self.getId = true;
                        self.findIdProcedure = false;
                    })
                    .catch(function (error) {
                        console.error(error);
                    });
                }
            });
        },

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
                            alert("입력하신 정보와 일치하는 아이디가 없습니다.\n다시 확인해주세요");
                        } else {
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
                    self.name = this.name;
                    self.email = this.email;
                    self.snsType = this.snsType;

                    this.authCodeValid = true;
                    this.authCodeMsgBlank = false;
                } else {
                    this.authCodeValid = false;
                    this.authCodeMsgBlank = false;
                }
            }
        }, 

        findIdMethod() {
            this.findIdProcedure = false;
            this.getId = true;
            this.findId = true;
        }
    }
}


</script>

<style scoped>

.cbutton {
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

    position: absolute;
    top: 65%;
    left: 44%;

}

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


.selectbutton1 {
    border: 1.5px solid #bdbdbd;
    border-right: none;
    border-bottom: none;
    width: 300px;
    height: 50px;
    background-color: transparent;
    font-family: 'AppleSDGothicNeoB';
}

.selectbutton2 {
    border: 1.5px solid #bdbdbd;
    border-bottom: none;
    width: 300px;
    height: 50px;
    background-color: transparent;
    font-family: 'AppleSDGothicNeoB';
}

.selectbutton1:hover {
  background-color: #Fdd000;
  color: #444444;
  font-family: 'AppleSDGothicNeoB';
}

.selectbutton2:hover {
  background-color: #Fdd000;
  color: #444444;
  font-family: 'AppleSDGothicNeoB';
}

.authbox1 {
  border: 1.5px solid #f8f6f6;
  background-color: #f8f6f6;
  width: 600px;
  height: 300px;
}

#phoneImg {
  position: absolute;
  top: 52%;
  left: 36%;
}

.p1 {
  position: absolute;
  top: 52%;
  left: 44%;
  color: #444444;
  font-family: 'AppleSDGothicNeoB';
  font-size: 30px;
  text-align: left;
}

.p2 {
  position: absolute;
  top: 58%;
  left: 44%;
  color: #706f6f;
  font-family: 'AppleSDGothicNeoB';
  font-size: 18px;
  text-align: left;
}

.authbox2 {
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

.getId {
    font-family: 'AppleSDGothicNeoR';
    font-size: 15px;
}

#info {
    font-family: 'AppleSDGothicNeoSB';
    font-size: 20px;
}



</style>