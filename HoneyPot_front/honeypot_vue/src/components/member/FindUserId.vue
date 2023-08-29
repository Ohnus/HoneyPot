<template>

아이디 찾기

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
    <span v-show="!authCodeValid" style="color:red">인증코드를 재확인해주세요.</span>
    <span v-if="authCodeValid" style="color:blue">이메일 인증이 완료되었습니다.</span>
    </div>

    <div v-show="getId" class="getId">
    <div v-if="findId">
    아이디 : {{ email }} <br>
    계정유형 : {{ snsType }} <br>
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
            select: true,
            selectEmailCertification: false,
            getId: false,
            findId: false,

            userNum: '',
            name: '',
            email:'',
            snsType:''

        }
    },
    methods: {

        // 핸드폰 본인 인증
        certification() {
            const self = this;
            self.selectEmailCertification = false;

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
                                console.log("email: " + res2.data.email);
                                console.log("snsType: " + res2.data.snsType);

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
                    self.select = false;

                    
                })
                .catch(function (error) {
                    console.error(error);
                });
            }
        });
    },


        

        emailCertification() {    // 이메일 인증 선택 시 이메일 인증 창 보이게 함
            const self = this;
            self.selectEmailCertification = true;
        },


        
    }
}

</script>