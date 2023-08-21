<template>
    <div>

        <div class="ott" v-show="firstStep">
            <div class="firstline">
            <input type="radio" class="radiobtn" v-model="type" value="Netflix" id="img_Neflix">
            <label for="img_Neflix">
                <img :src="require('@/assets/images/netflix.png')" /> 넷플릭스
            </label>
            <input type="radio" class="radiobtn" v-model="type" value="Tving" id="img_Tving">
            <label for="img_Tving">
                <img :src="require('@/assets/images/tving.png')" /> 티빙
            </label>
            <input type="radio" class="radiobtn"  v-model="type" value="Wave" id="img_Wave">
            <label for="img_Wave">
                <img :src="require('@/assets/images/wave1.png')" /> 웨이브
            </label>

            <input type="radio" class="radiobtn" v-model="type" value="Disney+" id="img_Disney">
            <label for="img_Disney">
                <img :src="require('@/assets/images/disney.png')" /> 디즈니플러스
            </label>
            <input type="radio" class="radiobtn" v-model="type" value="Watcha" id="img_Watcha">
            <label for="img_Watcha">
                <img :src="require('@/assets/images/watcha.png')" /> 왓챠
            </label>
            <input type="radio" class="radiobtn" v-model="type" value="Apple" id="img_Apple">
            <label for="img_Apple">
                <img :src="require('@/assets/images/apple.png')" /> 애플티비
            </label>
        </div>
        
            <button @click="oneStep">다음</button>
    
        </div>



        <div class="ottId" v-show="secondStep">
            아이디 : <input type="text" v-model="ottAcct">
            비밀번호 : <input type="password" v-model="ottPwd">
            <button @click="twoStep">다음</button>
        </div>

        <div class="pplSelect" v-show="thirdStep">
            (본인을 포함한) 최소인원 <input type="number" v-model="minPpl">
            (본인을 포함한) 최대인원 <input type="number" v-model="maxPpl">
            <button @click="threeStep">다음</button>
        </div>

        <div class="dateSelect" v-show="fourthStep">
            구독 시작날짜 : <input type="date" :min="minDate" v-model="subStart">
            몇개월 진행 : <input type="number" v-model="month">
            <button @click="fourStep">등록하기</button>
        </div>

    </div>
</template>
<script>
import dayjs from 'dayjs'
export default {
    name: 'HostBoardAdd',
    data() {
        return {

            // userNum: sessionStorage.getItem('username'),
            userNum: 1,
            type: null,
            ottAcct: null,
            ottPwd: null,
            minPpl: null,
            maxPpl: null,
            subStart: null,
            month: null,

            //true 랑 false 
            firstStep: true,
            secondStep: false,
            thirdStep: false,
            fourthStep: false,


        }
    },
    computed: {
        minDate() {
            return dayjs().format('YYYY-MM-DD');
        },
    },
    methods: {
        oneStep() {
            if (this.type) {
                this.firstStep = false;
                this.secondStep = true;
            }
        },
        twoStep() {
            if (this.ottAcct && this.ottPwd) {
                this.secondStep = false;
                this.thirdStep = true;
            }
        },
        threeStep() {
            if (this.minPpl && this.maxPpl) {
                this.thirdStep = false;
                this.fourthStep = true;
            }
        },
        fourStep() {

            if (this.subStart && this.month) {
                this.fourthStep = false;
                this.save();
            }
        },
        save() {

            const self = this;

            let formdata = new FormData();
            formdata.append('userNum', self.userNum)
            formdata.append('type', self.type)
            formdata.append('ottAcct', self.ottAcct)
            formdata.append('ottPwd', self.ottPwd)
            formdata.append('minPpl', self.minPpl)
            formdata.append('maxPpl', self.maxPpl)
            formdata.append('subStart', self.subStart)
            formdata.append('month', self.month)

            self.$axios.post('http://localhost:8988/hostboard', formdata)
                .then(function (res) {
                    if (res.status == 200) {
                        let dto = res.data.dto;
                        alert(dto.boardNum);
                    }
                });
        }
    }
}
</script>
<style scoped>
.radiobtn{
    opacity:0;
    padding : 2%;
}
img {
    height: 120px;
    width: 120px;
    border-radius: 23px;
    cursor : pointer;
    display: block;
}
input[type=radio]:checked + label{
    border: 2px solid #Fdd000; 
    border-radius: 23px;
}
.firstline{
    padding-top: 5%;
    display: grid;
    gap: 50px;
    grid-template-columns: repeat(6, auto);
    align-content: stretch;
    justify-content: center;
}

button {
    padding: 12px 28px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    
    transition-duration: 0.4s;
    cursor: pointer;
    background-color: #Fdd000;
    color: #444444;
    border: 2px solid #Fdd000;
    border-radius: 10px;

    
    margin: 30px 0 0 70px; /*위치조정*/
    font-weight: 900; /*폰트 굵기 -> 글씨체 적용하면 좀 바뀔 것 같은데 굵은게 예쁠것 같음 */ 
    font-size: 16px; /* 지금 버튼 사이즈에는 이게 딱임 */
    
}
button:hover {
  background-color: white;
  color: #444444;
}

</style>