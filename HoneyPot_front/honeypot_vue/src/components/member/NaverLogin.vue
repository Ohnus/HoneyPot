<template>
    <div></div>
    
    </template>
    
    <script>
    export default {
        name: 'NaverLogin',
        data() {
            return {
    
            }
        },
    
        created() {
            this.naverLogin();
        }, 
    
        methods: {
            naverLogin() {
                let client_id = "XUfF5HOL1SVNILfyWR1b";
                let redirect_uri = encodeURIComponent("http://localhost:8989/NaverJoin", "UTF-8");
                const state = this.generateRandomState();
                const apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
                 + "&client_id=" + client_id
                 + "&redirect_uri=" + redirect_uri
                    + "&state=" + state
                window.location.href = apiURL;
            },
    
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