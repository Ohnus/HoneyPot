import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'

//window.Kakao.init("bb62c20662c03ee0780acbc7749790fc");

const app = createApp(App) 
app.use(store)
app.use(router)
app.config.globalProperties.$axios = axios; 
app.mount('#app')