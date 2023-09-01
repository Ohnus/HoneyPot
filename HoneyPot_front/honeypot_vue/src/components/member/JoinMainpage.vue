<template>
<br>

<div class="joinMain">

<div style="font-family:AppleSDGothicNeoB; font-size: 20px; margin-top:120px; margin-bottom:30px">허니팟에 가입하고 
<br>다양한 서비스를 이용해보세요</div>

<div class="row justify-content-center">   
<div class="col-md-3" style="margin-bottom:10px">
<router-link to="/HoneypotJoin">
<button><img src="../../assets/images/honeypotJoin.png" style="width:19px; height:19px; margin-right:7px">허니팟 계정으로 시작하기</button></router-link>
</div>
</div>

<div class="row justify-content-center">   
<div class="col-md-3" style="margin-bottom:10px">
<router-link to="/NaverLogin">
<button><img src="../../assets/images/naverJoin.png" style="width:19px; height:19px; margin-right:7px">네이버 계정으로 시작하기</button></router-link>
</div>
</div>

<div class="row justify-content-center">   
<div class="col-md-3" style="margin-bottom:10px">
<router-link to="">
<button><img src="../../assets/images/kakaoJoin.png" style="width:20px; height:20px; margin-right:7px; margin-bottom:2px">카카오 계정으로 시작하기</button></router-link>
</div>
</div>

<!-- <img :src="require('@/assets/images/kakao_login_button.png')" @click="kakaoLogin()"/> -->
      
<div class="row justify-content-center">
<div class="findMenu" style="margin-top:20px">
<router-link to="/FindUserId"><button class="findButton">아이디 찾기</button></router-link> | 
<router-link to="/FindUserPwd"><button class="findButton">비밀번호 찾기</button></router-link>
</div>
</div>

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

<style scoped>
button {
  border: 2px solid #BDBDBD;
  border-radius: 7px;
  

}

button {
    padding: 11px 25px;
    text-align: center;
    text-decoration: none;
    display: inline-block;

    transition-duration: 0.4s;
    cursor: pointer;
    background-color: transparent;
    color: #444444;
    border: 1px solid #dbd6d6;
    border-radius: 10px;

    font-weight: 500; /*폰트 굵기 -> 글씨체 적용하면 좀 바뀔 것 같은데 굵은게 예쁠것 같음 */
    font-size: 14px; /* 지금 버튼 사이즈에는 이게 딱임 */
    font-family: 'AppleSDGothicNeoM';

}

.findMenu {
  font-family: 'AppleSDGothicNeoR';
  fort-size:13px;
  color: #BDBDBD;
}

.findButton {
  background-color: transparent;
  border: 0px;
  font-family: 'AppleSDGothicNeoM';
  font-size: 13px;
  color: #BDBDBD;

}


</style>
