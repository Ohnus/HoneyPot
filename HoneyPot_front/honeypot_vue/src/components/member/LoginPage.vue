<template>
    <div>
        <input v-model="email" type="text">
        <input v-model="pwd" type="password">
        <button @click="login">로그인</button>
    </div>
    <div> 
        <router-link to="/FindUserId"><button>아이디 찾기</button></router-link> | 
        <router-link to="/FindUserPwd"><button>비밀번호 찾기</button></router-link>
    </div>
</template>

<script>
export default {
    name: 'LoginPage',
    data() {
        return {
            email: '',     
            pwd: '' 
        }
    },

    methods: {

        login() {
            const self = this;
            const formdata = new FormData();

            formdata.append('email', self.email);
            formdata.append('pwd', self.pwd);

            console.log(self.email);
            console.log(self.pwd);

            self.$axios.post("http://localhost:8988/members/login", formdata)
            .then(function (res) {
                if (res.status == 200) {
                    if (res.data.flag) {
                        sessionStorage.setItem('token', res.data.token);    // 세션에 뭐뭐 넣어야하지..?
                        sessionStorage.setItem('loginId', res.data.email);
                        sessionStorage.setItem('userNum', res.data.userNum);

                        console.log(sessionStorage.getItem("loginId") + "/" + sessionStorage.getItem("userNum"));
                        
                        window.location.href = "/";
                        
                    } else {
                        alert ('로그인에 실패하였습니다. 로그인 정보를 다시 확인해주세요.')
                    }
                }
            })                
            .catch(function (error) {
                console.error(error);
            });
        }
    }
}



</script>