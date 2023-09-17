<template>
  <div v-if="userNum == null">
  <LoginPage />
</div>

<div v-else> 
 

  <div style="margin-top:1%" id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img :src="require('@/assets/images/001.png')" class="d-block w-100" alt="1">
    </div>
    <div class="carousel-item">
      <img :src="require('@/assets/images/002.png')"  class="d-block w-100" alt="2">
    </div>
    <div class="carousel-item">
      <img :src="require('@/assets/images/003.png')" class="d-block w-100" alt="3">
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>


</div>
</template>

<script>
// @ is an alias to /src
import LoginPage from '@/components/member/LoginPage.vue'

export default {
  name: 'HomeView',
  components: {
    LoginPage
  },
  created() {
    this.userNum = sessionStorage.getItem('userNum')
    console.log(this.userNum);
  },
}
</script>


<style scoped>
.carousel-control-prev:hover,
.carousel-control-next:hover {
    background-color: transparent; 
}
</style>