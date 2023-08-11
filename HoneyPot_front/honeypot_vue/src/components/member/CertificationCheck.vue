<template>
    <div>허니팟은 100% 인증을 통해 믿을 수 있는 구독 환경을 제공합니다.</div>
    <div>실명 인증을 진행해주세요.</div>
    <button @click="certification">확인</button>
</template>

<script>
export default {
    name: 'CertificationCheck',
    components: {

    },
    data() {
        return {
            username: '',
            phone: ''
        }
    },

    methods: {
        certification() {
            const self = this;
            const IMP = window.IMP;
            IMP.init("imp24063873}");

            console.log("왔나?")

            IMP.certification({
                pg: 'MIIiasTest',
                merchant_uid: 'merchant_' + new Date().getTime(),
                m_redirect_url: "http://localhost:8988/member/HoneypotJoin", // 어디로 보내지..?
                popup: false
                }, function (rsp) {
                    if (rsp.success) {
                        console.log(rsp.imp_uid);
                        console.log(rsp.merchant_uid);
                            const data = {
                                imp_uid: rsp.imp_uid
                            };
                            self.$axios.get("http://localhost:8988/member/certification/redirect", { params: data })
                            .then(function (res) {
                                if (res.status == 200) {
                                    console.log(self.username);
                                    console.log(self.phone);
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
    }
};


</script>

