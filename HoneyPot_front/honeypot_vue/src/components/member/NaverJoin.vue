<template>
    <div>
    <div id="naver_id_login"></div>
    </div>
</template>

<script>
export default {
    mounted() {
        const naver_id_login = new window.naver_id_login("uW8BXEv6IHbwQSCwO9jn", "http://localhost:8989/member/NaverJoin")
        const state = naver_id_login.getUniqState();
        naver_id_login.setButton("white", 2, 40);
        naver_id_login.setState(state);
        naver_id_login.init_naver_id_login();
    }
}


</script>