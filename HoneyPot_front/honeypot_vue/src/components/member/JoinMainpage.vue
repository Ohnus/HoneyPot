<template>
    <div class="joinMain">
        <div>허니팟에 가입하고 다양한 서비스를 이용해보세요</div>
            <router-link to="/HoneypotJoin"><button>허니팟 계정으로 시작하기</button></router-link>
            
            <img :src="require('@/assets/images/kakao_login_button.png')" @click="kakaoLogin()"/>
            <router-link to="/NaverLogin"><button>네이버 계정으로 시작하기</button></router-link>
        </div>
        
        <div> 
            <router-link to="/FindUserId"><button>아이디 찾기</button></router-link> | 
            <router-link to="/FindUserPwd"><button>비밀번호 찾기</button></router-link>
        </div>
</template>

<script>

export default {
    name: 'JoinMainpage',
    data() {
        return{
      kakao_email:'',
      
        }
    },
    methods: {
        findLoginInfo() {
            
        },
        kakaoLogin(){
            const client_id='d836a71e352daf8a9987274ee1b74912'
            const redirect_uri='http://localhost:8989/KakaoJoin'
           
            const uri = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${client_id}&redirect_uri=${redirect_uri}`;
          
            window.location.replace(uri);
        }


        // kakaoLogin(){
        //     console.log('window.Kakao' + window.Kakao)
        //     window.Kakao.Auth.login({ 
        //         success:this.getKakao
        //     })
        // },
        // getKakao(){
        //     const self = this;
        //     window.Kakao.API.request({
        //     url:'/v2/user/me',
        //     success:res =>{
        //         console.log(res);
        //         console.log(res.kakao_account);
        //         console.log(res.kakao_account.email);
        //         self.email = res.kakao_account.email
        //         const data = {
        //             email:res.kakao_account.email                };
                
        //   self.$axios
        //   .get('http://localhost:8988/members/emailCheck', { params: data })
        //   .then (function(res) {
        //     console.log(res);
        //     if (res.status == 200) {
        //             if( res.data.flag){
        //             alert('이미 가입된 이메일')}
        //                 else{
        //                     alert('가입 진행시켜')
                            
        //                     self.$router.push({name:'KakaoJoin', query:{email: self.email}});
        //                 }
        //         } else {
        //             alert("에러코드 : " + res.status);
        //         }
        //   });
        //     },
        //     fail:error => {
        //         console.log(error);
        //     }
        // })
        // }
        
    }
}



</script>
