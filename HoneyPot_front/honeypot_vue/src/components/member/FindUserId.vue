<template>

아이디 찾기

    <div v-show="findIdProcedure">
    <div v-show="select" class="select">
    <button @click="certification">핸드폰 본인 인증</button>
    <button @click="emailCertification">이메일 인증</button>
    </div>

    <div v-show="selectEmailCertification" class="inputInfo">
    <input v-model="name" type="text" placeholder="이름 입력">
    <input v-model="email" type="email" placeholder="메일 입력">
    <button @click="sendEmail">인증 메일 전송</button>
    </div>

    <div v-show="isSentEmail" class="authEmail">
    <input v-model="enterCode" type="text" placeholder="인증번호 입력">
    <button @click="authCodeCheck">확인</button><br>
    
    <span v-if="!authCodeValid" style="color:red">인증코드를 재확인해주세요.</span>
    </div>
    </div>

    <div v-show="getId" class="getId">
    <div v-if="findId"> 
    {{ name }} 님은 {{ snsType }} 계정으로 가입하셨습니다. <br>
    아이디는 {{ email }} 입니다. <br>
    <router-link to="/LoginPage"><button>로그인 페이지로 이동</button></router-link>
    </div>
    <div v-else-if="!findId">
    인증하신 정보와 일치하는 아이디가 없습니다.
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
            selectEmailCertification: false,
            getId: false,
            findId: false,
            isSentEmail: false,
            authCodeValid: true,

            name: '',
            email:'',
            snsType:'',
            enterCode: ''

        }
    },
    methods: {

        // 핸드폰 본인 인증
        certification() {
            const self = this;
            self.selectEmailCertification = false;  // 이메일 인증 관련 div 숨김

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

        emailCertification() {    
            const self = this;
            self.selectEmailCertification = true;   // 이메일 인증 관련 div 보여줌
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

                    this.findIdProcedure = false;
                    this.getId = true;
                    this.findId = true;
                } else {
                    this.authCodeValid = false;
                }
            }
        }
    }
}


</script>