<template>

    <div v-if="duplicated1" class="duplicateNotice1">
        <div>본인 인증된 정보로 이미 가입된 이력이 존재합니다.</div>
        <router-link to="/메인페이지링크"><button>메인으로</button></router-link>
    </div>

    <div v-else-if="duplicated2" class="duplicateNotice2">
        <div>해당 핸드폰번호로 가입된 이력이 존재합니다. 고객센터에 문의해주세요.</div>
        <router-link to="/메인페이지링크"><button>메인으로</button></router-link>
    </div>

    <div v-else-if="certified" class="NaverJoin">
        <button @click="naverLogin">네이버로그인</button>
    </div>

    <div v-else class="certificationNotice">
        <div>허니팟은 100% 인증을 통해 믿을 수 있는 구독 환경을 제공합니다.</div>
        <div>실명 인증을 진행해주세요.</div>
        <button @click="certification">확인</button>
    </div>

</template>

<script>
export default {
    name: 'NaverJoin',
    data() {
        return {
        certified: false,
        duplicated1: false,
        duplicated2: false
        }
    },
    methods: {
        
        // 본인인증
        certification() {
        const self = this;

        const IMP = window.IMP;

        IMP.init("imp24063873");

        IMP.certification({
            pg: 'MIIiasTest',
            merchant_uid: 'merchant_' + new Date().getTime(),
            m_redirect_url: "http://localhost:8081/member/HoneypotJoin", 
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

        naverLogin() {
            let client_id = "uW8BXEv6IHbwQSCwO9jn";
            let redirect_uri = encodeURIComponent("redirct_url", "UTF-8");
            const state = this.generateRandomState();
            const apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
             + "&client_id=" + client_id
             + "&redirect_uri=" + redirect_uri
                + "&state=" + state
            window.location.href = apiURL;
        }
        ,

        // state 난수 생성
        generateRandomState() {
            const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            let state = "";
      
            for (let i = 0; i < 16; i++) {
                const randomIndex = Math.floor(Math.random() * characters.length);
                state += characters.charAt(randomIndex);
            }

            return state;
        }
  
    }
}


</script>