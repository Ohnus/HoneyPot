import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import HostBoardAdd from '../components/hostboard/HostBoardAdd.vue'
import RegisterTest from '../components/paymenttest/RegisterTest.vue'
import PartyTest1 from '../components/paymenttest/PartyTest1.vue'
import PartyTest2 from '../components/paymenttest/PartyTest2.vue'
import RecurringTest from '../components/paymenttest/RecurringTest.vue'
import JoinMainpage from '../components/member/JoinMainpage.vue'
import HoneypotJoin from '../components/member/HoneypotJoin.vue'
import NaverJoin from '../components/member/NaverJoin.vue'
import JoinComplete from '../components/member/JoinComplete.vue'
import LoginPage from '../components/member/LoginPage.vue'
import MyInfo from '../components/member/MyInfo.vue'
import MyBankInfo from '../components/member/MyBankInfo.vue'
import FindUserId from '../components/member/FindUserId.vue'
import FindUserPwd from '../components/member/FindUserPwd.vue'
import HostBoardList from '../components/hostboard/HostBoardList.vue'
import HostBoardDetail from '../components/hostboard/HostBoardDetail.vue'
import ChattingRoomList from '../components/chat/ChattingRoomList.vue'
import ChattingRoom from '../components/chat/ChattingRoom.vue'
import MyPartyList from '../components/partygroup/MyPartyList.vue'
import PartyDetail from '../components/partygroup/PartyDetail.vue'




const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path : '/HostBoardAdd',
    name : 'HostBoardAdd',
    component: HostBoardAdd
  },
  {
    path : '/HostBoardList',
    name : 'HostBoardList',
    component: HostBoardList
  },
  {
    path : '/HostBoardDetail',
    name : 'HostBoardDetail',
    component: HostBoardDetail
  },
  {
    path : '/MyPartyList',
    name : 'MyPartyList',
    component: MyPartyList
  },
  {
    path: '/PartyDetail',
    name: 'PartyDetail',
    component: PartyDetail
  },
  {
    path: '/RegisterTest',
    name: 'RegisterTest',
    component: RegisterTest
  },
  {
    path: '/PartyTest1',
    name: 'PartyTest1',
    component: PartyTest1
  },
  {
    path: '/PartyTest2',
    name: 'PartyTest2',
    component: PartyTest2
  },
  {
    path: '/RecurringTest',
    name: 'RecurringTest',
    component: RecurringTest
  },
  {
    path: '/JoinMainpage',
    name: 'JoinMainpage',
    component: JoinMainpage
  },
  {
    path: '/HoneypotJoin',
    name: 'HoneypotJoin',
    component: HoneypotJoin
  },
  {
    path: '/NaverJoin',
    name: 'NaverJoin',
    component: NaverJoin
  },
  {
    path: '/JoinComplete',
    name: 'JoinComplete',
    component: JoinComplete
  },
  {
    path: '/LoginPage',
    name: 'LoginPage',
    component: LoginPage
  },
  {
    path: '/MyInfo',
    name: 'MyInfo',
    component: MyInfo
  },
   {
    path: '/MyBankInfo',
    name: 'MyBankInfo',
    component: MyBankInfo
  },
  {
    path: '/FindUserId',
    name: 'FindUserId',
    component: FindUserId
  },
  {
    path: '/FindUserPwd',
    name: 'FindUserPwd',
    component: FindUserPwd
  },
  {
    path: '/ChattingRoomList',
    name: 'ChattingRoomList',
    component: ChattingRoomList
  },
  {
    path: '/ChattingRoom',
    name: 'ChattingRoom',
    component: ChattingRoom
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router