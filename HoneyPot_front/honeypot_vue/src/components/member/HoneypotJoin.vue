<template>

    <div v-if="duplicated1" class="duplicateNotice1">
        <div>본인 인증된 정보로 이미 가입된 이력이 존재합니다.</div>
        <router-link to="/메인페이지링크"><button>메인으로</button></router-link>
    </div>

    <div v-else-if="duplicated2" class="duplicateNotice2">
        <div>해당 핸드폰번호로 가입된 이력이 존재합니다. 고객센터에 문의해주세요.</div>
        <router-link to="/메인페이지링크"><button>메인으로</button></router-link>
    </div>

    <div v-else-if="certified" class="HoneypotJoinForm">
        <input v-model="name" type="text">

        <input v-model="phone" type="text">

        <input v-model="email" type="email" placeholder="이메일 입력">
        <button @click="certifiedEmail">인증하기</button>

        <div>
        <input v-model="pwd" type="password" placeholder="비밀번호 입력" @input="checkPwd">
        <span v-if="!pwdValid" style="color:#FF0000">대문자, 특수문자 포함 8자리로 설정 필요</span>
        </div>

        <div>
        <input v-model="pwdCheck" type="password" placeholder="비밀번호 재입력">
        <span v-if="pwd !== pwdCheck" style="color:#FF0000">비밀번호 불일치</span>
        </div>

        <div>
        <input v-model="nickname" type="text" placeholder="닉네임 입력" @input="checkNickname">
        <span v-if="!nicknameValid" style="color:#FF0000">중복된 닉네임</span>
        </div>
    </div>

    <div v-else class="certificationNotice">
        <div>허니팟은 100% 인증을 통해 믿을 수 있는 구독 환경을 제공합니다.</div>
        <div>실명 인증을 진행해주세요.</div>
        <button @click="certification">확인</button>
    </div>


</template>


<script>
export default {
    name: 'HoneypotJoin',
    data() {
        return {
            duplicated1: false,
            duplicated2: false,
            certified: false,
            name: self.name,
            phone: self.phone,
            email: '',
            pwd: '',
            pwdCheck: '',
            nickname: ''
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
            m_redirect_url: "http://localhost:8084/member/HoneypotJoin", 
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

                            if (res.data.certified) {
                                self.certified = true;
                            } else if (res.data.duplicated1) {
                                self.duplicated1 = true;
                            } else if (res.data.duplicated2) {
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
        }
    },

    checkPwd() {
        const pwdRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{8,})/;   // 8자리 + 대문자 및 특수문자 포함
        this.pwdValid = pwdRegex.test(this.pwd);
    }





};


</script>
