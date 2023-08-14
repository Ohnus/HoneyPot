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
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router