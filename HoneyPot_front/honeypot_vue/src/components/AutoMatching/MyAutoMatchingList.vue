<template lang="">
    <div class="card-container" v-if="list != null && list.length > 0">
            <div v-for="order in displayedList" :key="order.matchingNum">
             
                <div class="card" :class="{ 'match-card': order.isMatching == 1,'expired-card': order.isMatching == 2 }">
<!-- 0 : 매칭 중(화이트) / 1 : 매칭 완료 (테두리?) / 2 : 기간 마감되어 매칭 취소 (회색) -->

            <div style="display: flex; align-items: center; justify-content: center;">
                <img class="card-img-top" v-if="order.type.type === 'Netflix'" :src="require('@/assets/images/netflix.png')"
                    alt="Netflix Logo" />
                <img class="card-img-top" v-else-if="order.type.type === 'Tving'" :src="require('@/assets/images/tving.png')"
                    alt="Tving Logo" />
                <img class="card-img-top" v-else-if="order.type.type === 'Wave'" :src="require('@/assets/images/wave1.png')"
                    alt="Wave Logo" />
                <img class="card-img-top" v-else-if="order.type.type === 'Disney+'" :src="require('@/assets/images/disney.png')"
                    alt="Disney Logo" />
                <img class="card-img-top" v-else-if="order.type.type === 'Watcha'" :src="require('@/assets/images/watcha.png')"
                    alt="Watcha Logo" />
                <img class="card-img-top" v-else-if="order.type.type === 'Apple'" :src="require('@/assets/images/apple.png')"
                    alt="Apple Logo" />
            </div>  

            <div class="card-body" style="text-align:center;" >          
                        <div style="font-family: 'AppleSDGothicNeoB'; font-size: 20px; color:#Fdd000; font-weight:500 ; ">
                        <span v-if="order.isMatching ==0" >매칭 대기 중</span>
                        <span v-else-if="order.isMatching ==1">매칭 완료 </span>
                        <span v-else-if="order.isMatching ==2">매칭 종료</span>
                        </div>

                

                    <div style="margin-top:7%; font-family: 'AppleSDGothicNeoB'; font-size: 18px;" >
                       <span> 매칭 기한 </span><br/>
                    </div>
                    <div style="font-family: 'AppleSDGothicNeoSB'; font-size: 16px;" >
                        <span> {{ formatDate(order.startDate) }}부터 </span>
                        <span>  {{ formatDate(order.exDate) }}까지</span>
                    </div>

                    <div style=" margin-top:6%; font-family: 'AppleSDGothicNeoB'; font-size: 18px;">
                        <span> 구독 기한 </span><br/>
                    </div>
                        <div>
                          <span style="font-family: 'AppleSDGothicNeoSB'; font-size: 16px;" > 
                            {{ order.monthMin }}개월 ~ {{ order.monthMax }}개월 사이</span>
                    </div>
                    <div style="margin-top:10%; font-family: 'AppleSDGothicNeoSB'; font-size: 16px;"> 
                    <button v-if="order.isMatching == 0" @click="delMatching(order.matchingNum)"> <span>대기 매칭 삭제</span></button>
                    <button v-else-if="order.isMatching === 1" @click="detail(order.matchingNum)"> <span>매칭 상세</span></button>
                    <button v-else-if="order.isMatching == 2" @click="delMatching(order.matchingNum)"> <span>내역 삭제</span></button>

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
    name: 'MyAutoMatchingList',
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
            self.$axios.get('http://localhost:8988/automatching/' + self.userNum)
                .then(function (res) {
                    if (res.status == 200) {
                        console.log(res.data.list)
                        self.list = res.data.list
                    }
                })
    },
    computed: {
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
            return date.format(' M[월] D[일]');
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
        detail(matchingNum) {
            this.$router.push({
                name: 'MyAutoMatchingDetail',
                query: {
                    matchingNum: matchingNum,
                    userNum: sessionStorage.getItem("userNum"),
                }
            })

        },
        delMatching(matchingNum){
            const self = this;
            self.$axios.delete('http://localhost:8988/automatching/' + matchingNum)
            .then(function(res) {
                if (res.status == 200) {
                    alert('삭제 되었습니다')
                    self.reloadData()
                }

            })
            
        },
        reloadData() {
            const self = this;
        self.userNum = sessionStorage.getItem("userNum"),
            self.$axios.get('http://localhost:8988/automatching/' + self.userNum)
                .then(function (res) {
                    if (res.status == 200) {
                        console.log(res.data.list)
                        self.list = res.data.list
                    }
                })
    }
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
    border: 3px dotted #dedede;
}

.match-card {
    border: 3px dotted #444444;
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
    height: 160px;
}

/* ott이미지 css */
img {
    height: 80px;
    width: 80px;
    border-radius: 23px;
    cursor: pointer;
    display: block;
    margin-top: 6%;
}

/* 페이지 넘기는 것 위치 css */
.pagination {
    display: flex;
    justify-content: center;
    margin-top: 14%;
}

/* 페이지넘기는 버튼css */
.pagination button,
button {
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

.pagination button:hover,
button:hover {
    background-color: white;
    color: #444444;
    font-family: 'AppleSDGothicNeoB';
}
</style>