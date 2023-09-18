import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import HostBoardAdd from '../components/hostboard/HostBoardAdd.vue'
import RegisterCard from '../components/payment/RegisterCard.vue'
import PartyTest1 from '../components/paymenttest/PartyTest1.vue'
import CashList from '../components/cash/CashList.vue'
import JoinMainpage from '../components/member/JoinMainpage.vue'
import HoneypotJoin from '../components/member/HoneypotJoin.vue'
import NaverLogin from '../components/member/NaverLogin.vue'
import NaverJoin from '../components/member/NaverJoin.vue'
import KakaoJoin from '../components/member/KakaoJoin.vue'
import JoinComplete from '../components/member/JoinComplete.vue'
import LoginPage from '../components/member/LoginPage.vue'
import MyInfoSidebar from '../components/member/MyInfoSidebar.vue'
import MyBankInfo from '../components/member/MyBankInfo.vue'
import FindUserId from '../components/member/FindUserId.vue'
import FindUserPwd from '../components/member/FindUserPwd.vue'
import HostBoardList from '../components/hostboard/HostBoardList.vue'
import HostBoardDetail from '../components/hostboard/HostBoardDetail.vue'
import ChattingRoomList from '../components/chat/ChattingRoomList.vue'
import ChattingRoom from '../components/chat/ChattingRoom.vue'
import MyPartyList from '../components/partygroup/MyPartyList.vue'
import MyPartyDetail from '../components/partygroup/MyPartyDetail.vue'
import AddAutoMatching from '../components/AutoMatching/AddAutoMatching.vue'
import MyAutoMatchingList from '../components/AutoMatching/MyAutoMatchingList.vue'
import MyAutoMatchingDetail from '../components/AutoMatching/MyAutoMatchingDetail.vue'

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
    path : '/MyPartyDetail',
    name : 'MyPartyDetail',
    component: MyPartyDetail
  },
  {
    path : '/AddAutoMatching',
    name : 'AddAutoMatching',
    component: AddAutoMatching
  },
  {
    path : '/MyAutoMatchingList',
    name : 'MyAutoMatchingList',
    component: MyAutoMatchingList
  },
  {
    path : '/MyAutoMatchingDetail',
    name : 'MyAutoMatchingDetail',
    component: MyAutoMatchingDetail
  },

  {
    path: '/RegisterCard',
    name: 'RegisterCard',
    component: RegisterCard
  },
  {
    path: '/PartyTest1',
    name: 'PartyTest1',
    component: PartyTest1
  },
  {
    path: '/CashList',
    name: 'CashList',
    component: CashList
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
    path: '/NaverLogin',
    name: 'NaverLogin',
    component: NaverLogin
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
    path: '/MyInfoSidebar',
    name: 'MyInfoSidebar',
    component: MyInfoSidebar
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
  },
  {
    path: '/KakaoJoin',
    name: 'KakaoJoin',
    component: KakaoJoin
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router