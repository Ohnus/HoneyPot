<template>
    <div class="joinMain">
        <div>허니팟에 가입하고 다양한 서비스를 이용해보세요</div>
        <router-link to="/HoneypotJoin"><button>허니팟 계정으로 시작하기</button></router-link>
        <router-link to=""><button>카카오 계정으로 시작하기</button></router-link>
        <router-link to="/NaverJoin"><button>네이버 계정으로 시작하기</button></router-link>
        <div class="findLoginInfo" @click="findLoginInfo">로그인 정보를 잊으셨나요?</div>
    </div>
</template>

<script>

export default {
    name: 'JoinMainpage',
    data() {
        
    },
    methods: {
        findLoginInfo() {
            
        }
        
    }
}



</script>
