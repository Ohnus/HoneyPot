<template>
    <div>
        <!-- <p>안녕 email: {{ self.email }} /password: {{ self.password }}</p> -->
        <p>안녕 {{ email }}</p>
    </div>
</template>

<script>
export default {
    name: 'KakaoJoin',
    data() {
        return {
            email:'',
            password:''
          
        }
    },

    created (){
        this.code = this.$route.query.code;
        console.log('this.code : '+this.code)
        this.getToken()
    },
    methods: {
        
        getToken(){
            const self = this;
            self.$axios
                .get("http://localhost:8988/kakaoJoin/" + this.code)
                .then(function (res) {
                    if (res.status == 200) {
                       console.log('res.data.access_token : ' +res.data.access_token);
                       console.log('res.data.userInfo : ' +res.data.userInfo.kakao_account.email);
                        
                       self.email = res.data.userInfo.kakao_account.email;
                     
                    } else {
                        alert("에러코드 : " + res.status);
                    }
                });
        }
    }
}


</script>