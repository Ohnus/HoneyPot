<template lang="">
        <div class="card-container" v-if="list != null && list.length > 0">
                <div v-for="order in displayedList" :key="order.groupNum">
                 
                    <div class="card" :class="{ 'expired-card': order.startCheck == 2 || order.startCheck == 3 }">


                <div style="display: flex; align-items: center; justify-content: center;">
                    <img class="card-img-top" v-if="order.boardNum.type.type === 'Netflix'" :src="require('@/assets/images/netflix.png')"
                        alt="Netflix Logo" />
                    <img class="card-img-top" v-else-if="order.boardNum.type.type === 'Tving'" :src="require('@/assets/images/tving.png')"
                        alt="Tving Logo" />
                    <img class="card-img-top" v-else-if="order.boardNum.type.type === 'Wave'" :src="require('@/assets/images/wave1.png')"
                        alt="Wave Logo" />
                    <img class="card-img-top" v-else-if="order.boardNum.type.type === 'Disney+'" :src="require('@/assets/images/disney.png')"
                        alt="Disney Logo" />
                    <img class="card-img-top" v-else-if="order.boardNum.type.type === 'Watcha'" :src="require('@/assets/images/watcha.png')"
                        alt="Watcha Logo" />
                    <img class="card-img-top" v-else-if="order.boardNum.type.type === 'Apple'" :src="require('@/assets/images/apple.png')"
                        alt="Apple Logo" />
                </div>  

                <div class="card-body" style="text-align:center;"  @click="detail(order.boardNum.boardNum)">          
                            <div style="font-family: 'AppleSDGothicNeoB'; font-size: 20px; color:#Fdd000; font-weight:500 ; ">
                            <span v-if="order.startCheck ==0" >시작 대기 중</span>
                            <span v-else-if="order.startCheck ==1">진행 중</span>
                            <span v-else-if="order.startCheck ==2">종료</span>
                            <span v-else-if="order.startCheck ==3">중간 탈퇴</span>
                            <span v-else-if="order.startCheck ==4">진행 중 </span>
                            </div>

                            <div style="font-family: 'AppleSDGothicNeoB'; font-size: 18px;" >
                            <span v-if="order.boardNum.userNum.userNum == userNum">👑파티장 </span>
                            <span v-else>👥파티원 </span>  
                        </div>

                        <div style="margin-top:3%; font-family: 'AppleSDGothicNeoSB'; font-size: 16px;" >
                            <span> {{ formatDate(order.boardNum.subStart) }} 부터 </span>
                        </div>
                        <div style="font-family: 'AppleSDGothicNeoSB'; font-size: 16px;" >
                            <span>  {{ formatDate(order.boardNum.subEnd) }} 까지</span>
                        </div>
                        <div style=" margin-top:2%; font-family: 'AppleSDGothicNeoSB'; font-size: 16px;">
                          <span v-if="order.startCheck == 1 ||order.startCheck == 4 " >
                              <span style="font-family: 'AppleSDGothicNeoB'; font-size: 18px; font-weight:500; color:#Fdd000"> {{ calculateRemainingDays(order.boardNum.subEnd) }}</span> <span> 일 남음 </span></span>
                        </div>
                        <div style="margin-top:2%; font-family: 'AppleSDGothicNeoSB'; font-size: 16px;" >
                            <span> 구독료 {{ Math.round (order.boardNum.monthPrice * 0.1 + order.boardNum.monthPrice)}}원 </span>
                        </div>
                        <div style="font-family: 'AppleSDGothicNeoSB'; font-size: 16px;" >
                            <span>보증금 {{ order.boardNum.type.price}}원</span> 
                        </div>
    
                    </div>
                </div>
            </div>
      
        </div>

            <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1"><span>이전</span></button>
      <span style="margin: 5px 1% 1% 1%; font-size: 16px;">{{ currentPage }}</span>
      <button @click="nextPage" :disabled="currentPage * pageSize >= list.length"><span>다음</span></button>
    </div>

        
          
   
</template>
<script>
import dayjs from 'dayjs'
export default {
    name: 'MyPartyList',
    data() {
        return {
            list: [],
            userNum: sessionStorage.getItem("userNum"),
            currentPage: 1, // 현재 페이지
            pageSize: 3, // 페이지당 표시할 카드 수

        }

    },
    created: function () {

        const self = this;
        self.userNum = sessionStorage.getItem("userNum"),
            self.$axios.get('http://localhost:8988/partygroup/' + self.userNum)
                .then(function (res) {
                    if (res.status == 200) {
                        console.log(res.data.list)
                        self.list = res.data.list
                    }
                })

    },
    computed: {
        // 현재 페이지에 표시할 카드 목록을 계산합니다.
        displayedList() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            return this.list.slice(startIndex, endIndex);
        }
    },
    methods: {
        // 날짜 형식 바꿔주는 것
        formatDate(dateString) {
            const date = dayjs(dateString);
            return date.format('YYYY[년] M[월] D[일]');
        },
        // 남은 날짜 계산해주는 것
        calculateRemainingDays(endDate) {
            const currentDate = new Date(); //오늘 날짜 생성 
            const end = new Date(endDate); //마감날짜 불러오고 
            const timeDifference = end - currentDate; //뺀날짜 계산하고 
            const daysRemaining = Math.ceil(timeDifference / (1000 * 60 * 60 * 24)); //그걸 일로 바꿔

            return daysRemaining;
        },
        prevPage() {
            if (this.currentPage > 1) {
                this.currentPage--;
            }
        },
        nextPage() {
            const lastPage = Math.ceil(this.list.length / this.pageSize);
            if (this.currentPage < lastPage) {
                this.currentPage++;
            }
        },
        detail(boardNum) {
            this.$router.push({ name: 'MyPartyDetail',  query: { 
    boardNum: boardNum,
    userNum: sessionStorage.getItem("userNum"),

  } })

        },
    }

}
</script>
<style scoped>
p {
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;

}

/* 종료시 뒷배경 css */
.expired-card {
    background-color: #dedede;
}

/* 카드 크기, 위치 등등 css */
.card-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    margin-left: 2%;
}

.card {
    width: 18rem;
    height: 140%;
    border-radius: 20px;
}

.card-body {
    height: 140px;
}

/* ott이미지 css */
img {
    height: 80px;
    width: 80px;
    border-radius: 23px;
    cursor: pointer;
    display: block;
    margin-top: 10%;
}

/* 페이지 넘기는 것 위치 css */
.pagination {
    display: flex;
    justify-content: center;
    margin-top: 13%;
}

/* 페이지넘기는 버튼css */
.pagination button {
    font-family: 'AppleSDGothicNeoB';
    padding: 10px 20px;
    height: 40px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    transition-duration: 0.4s;
    cursor: pointer;
    background-color: #Fdd000;
    color: #444444;
    border: 2px solid #Fdd000;
    border-radius: 10px;
    font-weight: 400;
    font-size: 14px;
}

.pagination button:hover {
    background-color: white;
    color: #444444;
    font-family: 'AppleSDGothicNeoB';
}
</style>