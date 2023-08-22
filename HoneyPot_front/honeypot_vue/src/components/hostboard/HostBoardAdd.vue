<template>
    <div>

        <div class="ott" v-show="firstStep">
            <p style="padding-top: 2%; font-size: 28px;">어떤 파티를 만드시겠어요?</p>
            <div class="firstline">
                <input type="radio" class="radiobtn" v-model="type" value="Netflix" id="img_Neflix">
                <label for="img_Neflix">
                    <img :src="require('@/assets/images/netflix.png')" />
                    <p>넷플릭스</p>
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Tving" id="img_Tving">
                <label for="img_Tving">
                    <img :src="require('@/assets/images/tving.png')" />
                    <p>티빙</p>
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Wave" id="img_Wave">
                <label for="img_Wave">
                    <img :src="require('@/assets/images/wave1.png')" />
                    <p>웨이브</p>
                </label>

                <input type="radio" class="radiobtn" v-model="type" value="Disney+" id="img_Disney">
                <label for="img_Disney">
                    <img :src="require('@/assets/images/disney.png')" />
                    <p>디즈니플러스</p>
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Watcha" id="img_Watcha">
                <label for="img_Watcha">
                    <img :src="require('@/assets/images/watcha.png')" />
                    <p>왓챠</p>
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Apple" id="img_Apple">
                <label for="img_Apple">
                    <img :src="require('@/assets/images/apple.png')" />
                    <p>애플티비</p>
                </label>
            </div>

            <button @click="oneStep">다음</button>
        </div>


        <div class="secondcontainer" v-show="secondStep">
            <div class="ottaccinfo" >
             <p style ="font-size:25px">[파티장 가이드] </p>
            <p style ="font-size:16px">
            <span>{{ type }}</span> 프리미엄 요금제를 결제해 주세요. <br/>
            <br/>
            🎫 파티장이 '{{ type }} 프리미엄' 요금제를 먼저 결제하여 파티원과 공유하면, <br/>
            허니팟이 매달 파티 요금을 적립해 드려요.<br/>
            <br/>

           공유해도 안전한 비밀번호를 사용해 주세요. <br/>
           <br/>
            🔐 파티장의 계정을 파티원과 함께 사용하게 될거예요. <br/>
            평소에 자주 쓰는 비밀번호를 공유하면 안돼요.<br/>
            <br/>
            성인 인증을 완료해 주세요.<br/>
            <br/>
            🔞 파티장이 성인 인증을 하지 않으면, <br/>
            파티원이 성인 콘텐츠 시청을 할 수 없어요.<br/>
            <br/>
            결제 카드 등록이 필요해요. <br/>
            <br/>
            🪙 파티장 귀책사유 발생시 위약금을 부과하기 위해 결제 카드 등록이 필요해요.<br/>
             등록 과정에서 100원이 결제된 후 바로 취소될 거예요.<br/>
            </p>
        </div>

        <div class="ottId" >
            <p>{{ type }} 계정의 아이디와 비밀번호를 입력해 주세요 </p>
            <div>아이디 : <input type="text" v-model="ottAcct"></div>
            <div>비밀번호 : <input type="password" v-model="ottPwd"></div>
            <div><button @click="twoStep">다음</button></div>
        </div>

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
div{
    color : #444444;
}
p {
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;
}

.radiobtn {
    opacity: 0;
    padding: 2%;
}

img {
    height: 120px;
    width: 120px;
    border-radius: 23px;
    cursor: pointer;
    display: block;
}

input[type=radio]:checked+label {
    border: 5px solid #Fdd000;
    border-radius: 23px;
    padding: 5px 5px 5px 5px;
}

.firstline {
    padding-top: 3%;
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


    margin: 30px 0 0 70px;
    font-weight: 900;
    font-size: 16px;
}

button:hover {
    background-color: white;
    color: #444444;
    font-family: 'AppleSDGothicNeoB';
}



.secondcontainer {
    display: flex;
    justify-content: space-between; 
    align-items: flex-start; 
}

.ottaccinfo{
    flex-basis: 50%; 
    padding: 10px;
    margin: 6% 15% 0 -10%;

}
.ottId {
    flex-basis: 50%; 
    padding: 10px;
    margin: 18% 6% 0 -10%;

}


</style>