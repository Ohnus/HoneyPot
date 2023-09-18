import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import MyInfo from './components/member/MyInfo.vue'
import MyBankInfo from './components/member/MyBankInfo.vue'
import MyPartyList from './components/partygroup/MyPartyList.vue'
import MyAutoMatchingList from './components/AutoMatching/MyAutoMatchingList.vue'
import RegisterCard from './components/payment/RegisterCard.vue'
import CashList from './components/cash/CashList.vue'

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/css/bootstrap.css'

//window.Kakao.init("bb62c20662c03ee0780acbc7749790fc");

const app = createApp(App) 
app.use(store)
app.use(router)
app.config.globalProperties.$axios = axios; 
app.component('my-info', MyInfo);
app.component('my-bankinfo', MyBankInfo);
app.component('my-partylist', MyPartyList);
app.component('my-automatcinglist', MyAutoMatchingList);
app.component('register-card', RegisterCard);
app.component('cash-list', CashList);



app.mount('#app')

