<template lang="">

        <div class="image-container">
                <input type="radio" class="radiobtn" v-model="type" value="Netflix" id="img_Neflix">
                <label for="img_Neflix">
                    <img :src="require('@/assets/images/netflix.png')" />
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Tving" id="img_Tving">
                <label for="img_Tving">
                    <img :src="require('@/assets/images/tving.png')" />
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Wave" id="img_Wave">
                <label for="img_Wave">
                    <img :src="require('@/assets/images/wave1.png')" />
                </label>

                <input type="radio" class="radiobtn" v-model="type" value="Disney+" id="img_Disney">
                <label for="img_Disney">
                    <img :src="require('@/assets/images/disney.png')" />
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Watcha" id="img_Watcha">
                <label for="img_Watcha">
                    <img :src="require('@/assets/images/watcha.png')" />
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Apple" id="img_Apple">
                <label for="img_Apple">
                    <img :src="require('@/assets/images/apple.png')" />
                </label>
            </div>

            <div v-if="list != null && list.length > 0">
                <div class="order-container">
      <div v-for="order in filteredList" :key="order.boardNum" class="order-item">
        <div v-if="order.ing === 0" @click="detail(order.boardNum)" class="order-details">
          <span class="highlight">{{ order.type.type }} </span><span> 을(를) </span>
          <span class="highlight">{{ formatDate(order.subEnd) }} </span> <span> 까지 </span>
                <span> 월</span> <span class="highlight"> {{ calculateValue(order.type.price, order.maxPpl) }} </span> <span> 원에</span>
                    <span> 이용해 보세요</span>
          

        </div>
      </div>
    </div>
  </div>

</template>
<script>
export default {
    name: 'HostBoardList',
    data() {
        return {
            list: [],
            boardNum: 0,
            // userNum : sessionStorage.getItem('loginId'),
            userNum: 1,
            type: null,
            subStart: null,
            subEnd: null,
            maxPpl: null,
            month: null
        }
    },
    created: function () {
        const self = this;
        self.$axios.get('http://localhost:8988/hostboard')
            .then(function (res) {
                if (res.status == 200) {
                    self.list = res.data.list
                    console.log(self.list)
                }
            })


    },
    computed: {
        filteredList() {
            // OTT 유형(type)에 따라 필터링된 글 목록 반환
            if (!this.type) {
                // type이 선택되지 않았을 때는 전체 목록 반환
                return this.list;
            } else {
                // type에 해당하는 글만 필터링
                return this.list.filter(order => order.type.type === this.type);
            }
        },
    },
    methods: {
        detail(boardNum) {
            this.$router.push({ name: 'HostBoardDetail', query: { boardNum: boardNum } })

        },
        formatDate(subEnd) {
            // 배열에서 [ ]를 없애고 ,를 .으로 바꿔서 날짜 문자열 생성
            const formattedDate = subEnd.join('.').replace(/\[|\]/g, '');
            return formattedDate;
        },
        calculateValue(price, maxPpl) {
            // price와 maxPpl를 이용하여 계산한 값을 반올림해서 반환
            const calculatedValue = Math.round(price / maxPpl);
            return calculatedValue;
        }
    }
}
</script>

<style scoped>
span {
    font-family: 'AppleSDGothicNeoB';
    font-size: 18px;
    font-weight: 400;
 }

.highlight {
    font-family: 'AppleSDGothicNeoB';
     font-size: 26px; 
     color: #Fdd000;
     font-weight: 500;
     
}
.image-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-right: 20%;
    padding-top: 3%;
}

.radiobtn {
    opacity: 0;
    padding: 2%;
}

img {
    height: 100px;
    width: 100px;
    border-radius: 23px;
    cursor: pointer;
    display: block;
    margin: 0 10px;
    /* 이미지 사이의 간격 설정 */
}

input[type=radio]:checked+label {
    border-bottom: 3px solid #Fdd000;
    padding-bottom: 1%;
}


.order-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 3%;
}

.order-item {
    border: 3px dotted #444444;
    border-radius: 23px;
    margin-bottom: 15px;
    width: 50%;
    max-width: 60%;
    align-self: center;
    margin: 10px 10px 10px 10px;
    padding: 2%;

}
</style>
