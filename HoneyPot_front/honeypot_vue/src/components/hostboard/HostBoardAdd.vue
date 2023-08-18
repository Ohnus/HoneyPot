<template>
    <div>

        <div class="ott" v-show="firstStep">
            <input type="radio" v-model="type" value="Netflix">넷플릭스
            <input type="radio" v-model="type" value="Tving"> 티빙
            <input type="radio" v-model="type" value="Wave"> 웨이브
            <input type="radio" v-model="type" value="Disney+"> 디즈니플러스
            <input type="radio" v-model="type" value="Watcha"> 와차
            <input type="radio" v-model="type" value="Apple"> 애플
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
<style scoped></style>