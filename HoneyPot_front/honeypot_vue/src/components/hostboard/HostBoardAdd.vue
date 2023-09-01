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

            <button @click="oneStep" style="margin: 30px 0 0 70px;">다음</button>
        </div>


        <div class="secondcontainer" v-show="secondStep">
            <div class="ottaccinfo">
                <p style="font-size:25px; font-weight: 700;">[파티장 가이드] </p>
                <p style="font-size:18px; font-weight: 500;">
                    🎫 <span>{{ type }}</span> 프리미엄 요금제를 결제해 주세요. <br />
                </p>
                <p style="font-size:15px; margin-top:-15px;">
                    파티장이 '{{ type }} 프리미엄' 요금제를 먼저 결제하여 파티원과 공유하면, <br />
                    허니팟이 매달 파티 요금을 적립해 드려요.<br />
                </p>
                <br />
                <p style="font-size:18px; font-weight: 500;">
                    🔐 공유해도 안전한 비밀번호를 사용해 주세요. <br />
                </p>
                <p style="font-size:15px; margin-top:-15px;">
                    파티장의 계정을 파티원과 함께 사용하게 될거예요. <br />
                    평소에 자주 쓰는 비밀번호를 공유하면 안돼요.<br />
                </p>
                <br />
                <p style="font-size:18px; font-weight: 500;">
                    🔞성인 인증을 완료해 주세요.<br />
                </p>
                <p style="font-size:15px; margin-top:-15px;">
                    파티장이 성인 인증을 하지 않으면, <br />
                    파티원이 성인 콘텐츠 시청을 할 수 없어요.<br />
                </p>
                <br />

                <p style="font-size:18px; font-weight: 500;">
                    🪙결제 카드 등록이 필요해요. <br />
                </p>
                <p style="font-size:15px; margin-top:-15px;">
                    파티장 귀책사유 발생시 위약금을 부과하기 위해 결제 카드 등록이 필요해요.<br />
                    등록 과정에서 100원이 결제된 후 바로 취소될 거예요.<br />
                </p>
            </div>

            <div class="ottId">
                <p>{{ type }} 계정의 아이디와 비밀번호를 입력해 주세요 </p>
                <div><input type="text" v-model="ottAcct" @input="validateInputId('ottAcct')" placeholder="아이디"></div>
                <div><input type="password" v-model="ottPwd" @input="validateInputId('ottPwd')" placeholder="비밀번호"></div>
                <div><button @click="twoStep">다음</button></div>
            </div>

        </div>

        <div class="thirdcontainer" v-show="thirdStep">
            <div class="PplInfo" style="padding-top: 13%;">
                <p style="font-size:25px; font-weight: 700;">
                    [모집 인원 가이드]</p>
                <p style="font-size:18px; font-weight: 500;">
                    👫몇 명의 파티원을 모집하실 건가요?</p>
                <br />
                <p style="font-size:15px; margin-top:-15px;">
                    본인을 포함한 파티 시작 최소 인원과 최대 인원을 입력해 주세요.<br />
                    최소 인원만 모여도 파티는 시작되며 최대 인원까지 계속 모집해 드려요. <br/>
                금액은 최대 인원으로 측정 되며 최소 인원으로 시작 했다면 파티장이 1인 요금을 더 부담해야 해요.</p>
            </div>
            <div class="pplSelect">
                <p> 모집인원을 입력하여 주세요 </p>
                <div><input type="number" v-model="minPpl" placeholder="최소인원" @input="validateInput('minPpl')" >
                </div>
                <div><input type="number" v-model="maxPpl" placeholder="최대인원"  @input="validateInput('maxPpl')" >
                </div>
                <button @click="threeStep">다음</button>
            </div>
        </div>

        <div class="fourthcontainer" v-show="fourthStep">
            <div class="dateInfo" style="padding-top: 10%;">
                <p style="font-size:25px; font-weight: 700;">
                    [파티 기간 가이드]</p>
                <p style="font-size:18px; font-weight: 500;">
                    📅 몇 개월동안 파티를 진행 하실껀가요? </p>
                <br />
                <p style="font-size:15px; margin-top:-15px;">
                    <span>{{ type }} </span> 시작 날짜와 원하는 구독 개월 수를 입력해 주세요. <br />
                    구독마감 1개월 전까지 꾸준히 파티원을 모집해 드리고 있어요.<br />
                    구독 기간 중 파티장의 과실로 파티를 지속하지 못한다면 위약금을 돌려 받을 수 없어요.
                </p>
            </div>
            <div class="dateSelect">
                <div>
                    <input type="date" :min=minDate data-placeholder="시작 날짜" v-model="subStart" required>
                </div>
                <div><input type="number" placeholder="진행 할 개월 수 " v-model="month" :min="1"></div>
                <button @click="fourStep">등록하기</button>
            </div>
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
            type: '',
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
            } else {
                alert('파티 만들기 원하는 OTT 사이트를 선택해주세요');
            }
        },
        twoStep() {
            //매크로보내면 되긴하는데... 흠... 
            if (this.ottAcct && this.ottPwd) {
                this.secondStep = false;
                this.thirdStep = true;
            } else {
                alert('OTT 아이디와 비밀번호를 입력해주세요');
            }
        },
        validateInputId(field) {
      // 입력값에서 한글 안되게 하기
      this[field] = this[field].replace(/[ㄱ-ㅎㅏ-ㅣ가-힣]/g, '');
    },

        threeStep() {
            if (this.minPpl && this.maxPpl && this.minPpl <= this.maxPpl) {
                this.thirdStep = false;
                this.fourthStep = true;
            } else {
                alert('올바른 인원을 선택하여 주세요');
            }
        },
        validateInput(field) {
            // 입력값을 숫자로 변환
            this[field] = Number(this[field]);

            // 최소값과 최대값 설정
            const minValue = 2;
            const maxValue = this.type === 'Apple' ? 5 : 4;

            // 최소값 미만 또는 최대값 초과인 경우 최소값 또는 최대값으로 설정
            if (this[field] < minValue) {
                this[field] = minValue;
            } else if (this[field] > maxValue) {
                this[field] = maxValue;
            }
        },
 
        fourStep() {

            if (this.subStart && this.month) {
                this.fourthStep = false;
                this.save(); //여기서 결제로 넘어가고 결제에서 this.save() 메서드 실행 시켜주면 됌 
            } else {
                alert('날짜 입력을 해주세요');
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
div {
    color: #444444;
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
    border-bottom: 5px solid #Fdd000;
    /* border-radius: 23px; */
    /* padding: 5px 5px 5px 5px; */
    /* padding-bottom: 2px; */
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
    font-weight: 900;
    font-size: 16px;
}

button:hover {
    background-color: white;
    color: #444444;
    font-family: 'AppleSDGothicNeoB';
}



.secondcontainer,
.thirdcontainer,
.fourthcontainer {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
}

.ottaccinfo,
.PplInfo,
.dateInfo {
    flex-basis: 50%;
    padding-top: 10px;
    margin: 6% 15% 0 -10%;

}


.ottId,
.pplSelect,
.dateSelect {
    flex-basis: 50%;
    padding: 10px;
    margin: 18% 6% 0 -10%;

}

input[type=text],
input[type=password],
input[type=number],
input[type=date] {
    width: 200px;
    height: 32px;
    font-size: 15px;
    border: 0;
    border-radius: 15px;
    outline: none;
    padding-left: 10px;
    margin-bottom: 3%;
    background-color: rgb(233, 233, 233);
}

input[type=date]::before {
    color: #444444;
    width: 100%;
    content: attr(data-placeholder);
}

input[type="date"]:valid::before {
    display: none;
}





input[type=date] {
    position: relative;
}

input[type=date]::-webkit-clear-button,
input[type=date]::-webkit-inner-spin-button {
    display: none;
}


input[type=date]::-webkit-calendar-picker-indicator {
    position: absolute;
    left: 0;
    right: 0;
    width: 100%;
    height: 100%;
    background: transparent;
    color: transparent;
    cursor: pointer;
}
</style>