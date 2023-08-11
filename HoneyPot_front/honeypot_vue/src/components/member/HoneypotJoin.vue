<template>
    <div class="joinForm">
    <input v-model="name" type="text" :key="name">{{ name }}

    <input v-model="phone" type="text" :key="phone">{{ phone }}

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
</template>

<script>
const { IMP } = window;

export default {
    name: 'HoneypotJoin',
    components: {

    },
    data() {


    },
    mounted() {
        this.certification()
    },

    methods: {
        certification() {
            IMP.init("{imp24063873}");
            IMP.certification({
                pg: 'MIIiasTest',
                merchant_uid: 'merchant_' + new Date().getTime(),
                m_redirect_url: "http://localhost:8988/member",
            }, function(rsp) {
                if (rsp.success) {
                    self.$axios({
                        url: "http://localhost:8989/member/NaverJoin",
                        method: "POST",
                        headers: { "Content-Type": "application/json" },
                        data: { imp_uid: rsp.imp_uid }
                    });
                } else {
                    alert("인증에 실패하였습니다." + rsp.error_msg);
                }
            });
        },

        checkPwd() {
            const pwdRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{8,})/;   // 8자리 + 대문자 및 특수문자 포함
            this.pwdValid = pwdRegex.test(this.pwd);
        },

   
        
    }
}

</script>