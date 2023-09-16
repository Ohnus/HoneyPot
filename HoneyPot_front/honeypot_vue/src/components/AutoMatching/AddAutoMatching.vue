<template>
    <div>

        <div class="ott" v-show="firstStep">
            <p class="ottselect">어떤 파티를 찾고 계신가요? </p>
            <div class="firstline">
                <input type="radio" class="radiobtn" v-model="type" value="Netflix" id="img_Neflix">
                <label for="img_Neflix">
                    <img :src="require('@/assets/images/netflix.png')" />
                    <p class="ottname">넷플릭스</p>
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Tving" id="img_Tving">
                <label for="img_Tving">
                    <img :src="require('@/assets/images/tving.png')" />
                    <p class="ottname">티빙</p>
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Wave" id="img_Wave">
                <label for="img_Wave">
                    <img :src="require('@/assets/images/wave1.png')" />
                    <p class="ottname"> 웨이브</p>
                </label>

                <input type="radio" class="radiobtn" v-model="type" value="Disney+" id="img_Disney">
                <label for="img_Disney">
                    <img :src="require('@/assets/images/disney.png')" />
                    <p class="ottname">디즈니플러스</p>
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Watcha" id="img_Watcha">
                <label for="img_Watcha">
                    <img :src="require('@/assets/images/watcha.png')" />
                    <p class="ottname">왓챠</p>
                </label>
                <input type="radio" class="radiobtn" v-model="type" value="Apple" id="img_Apple">
                <label for="img_Apple">
                    <img :src="require('@/assets/images/apple.png')" />
                    <p class="ottname">애플티비</p>
                </label>
            </div>

            <button @click="oneStep" style="margin: 30px 0 0 70px;">다음</button>
        </div>


        <div class="secondcontainer" v-show="secondStep">
            <div class="autoinfo">
                <p style="font-size:25px; font-weight: 500;">[자동매칭 가이드] </p>
                <p style="font-size:18px; font-weight: 500;">
                    📺 <span>{{ type }}</span> 파티 시작 날짜를 선택 해 주세요 <br />
                </p>
                <p style="font-size:15px; margin-top:-15px;">
                    허니팟이 해당 날짜에 시작하는 파티에 자동으로 가입시켜 드려요. <br />
                </p>
                <br />
                <p style="font-size:18px; font-weight: 500;">
                    ⌛ 파티 매칭 대기 설정을 해주세요 <br /> </p>
                <p style="font-size:15px; margin-top:-15px;">
                    선택한 파티 시작일에 파티가 없는 경우, <br />
                    며칠 더 파티 매칭을 대기할지 선택해 주세요. <br />
                    최대 7일까지 연장 가능하며 <br />
                    대기 설정 이후에도 매칭이 되지 않으면 자동매칭은 삭제됩니다. <br />
                </p>
                <br />
                <p style="font-size:18px; font-weight: 500;">
                    📅파티 기간을 설정해 주세요.<br />
                </p>
                <p style="font-size:15px; margin-top:-15px;">
                    범위를 더 넓게 설정할수록 더 빨리 파티가 매칭될거예요. <br />
                    최대 범위는 24개월(2년)입니다. <br />
                    파티 매칭 이후에는 파티 기간을 변경할 수 없어요.<br />
                </p>
                <br />

                <p style="font-size:18px; font-weight: 500;">
                    🪙결제 카드 등록이 필요해요. <br />
                </p>
                <p style="font-size:15px; margin-top:-15px;">
                    수수료와 요금, 보증금 결제를 위해 카드 등록이 필요해요.<br />
                    등록 과정에서 100원이 결제된 후 바로 취소될 거예요.<br />
                </p>
            </div>

            <div class="autodetail">
                <div style="display: flex; align-items: center; justify-content: center;">

                    <img class="otttypedetail" v-if="type === 'Netflix'" :src="require('@/assets/images/netflix.png')"
                        alt="Netflix Logo" />
                    <img class="otttypedetail" v-else-if="type === 'Tving'" :src="require('@/assets/images/tving.png')"
                        alt="Tving Logo" />
                    <img class="otttypedetail" v-else-if="type === 'Wave'" :src="require('@/assets/images/wave1.png')"
                        alt="Wave Logo" />
                    <img class="otttypedetail" v-else-if="type === 'Disney+'" :src="require('@/assets/images/disney.png')"
                        alt="Disney Logo" />
                    <img class="otttypedetail" v-else-if="type === 'Watcha'" :src="require('@/assets/images/watcha.png')"
                        alt="Watcha Logo" />
                    <img class="otttypedetail" v-else-if="type === 'Apple'" :src="require('@/assets/images/apple.png')"
                        alt="Apple Logo" />
                </div>

                <div><input type="date" :min=minDate data-placeholder="시작 날짜" v-model="startDate" required></div>
                <div><input type="number" placeholder="매칭 대기 기간" :min="1" :max="7"
                        @input="validnumInput('exDate')" @blur="updateDate"></div>
                <div><input type="number" v-model="monthMin" placeholder="최소 파티 기간" @input="MaxValue(monthMin, 24)"></div>
                <div><input type="number" v-model="monthMax" placeholder="최대 파티 기간" @blur="finalMonth"
                        @input="MaxValue(monthMax, 24)"></div>

                <div style="margin : 1% 0px 0px 1%;"><span v-show="showcalculatedDate" class="DateUntill"> 매칭 대기 기한 : {{
                    exDate }} </span></div>
                <div style="margin : 1% 0px 0px 1%;"><span v-show="showMonth" class="DateUntill"> 파티 기한 : {{ monthMin }} 개월
                        ~ {{ monthMax }} 개월</span></div>


                <div><button @click="saveAutoMatching">등록하기</button></div>
            </div>

        </div>


    </div>
</template>
<script>
import dayjs from 'dayjs'

export default {
    name: 'AddAutoMatching',
    data() {
        return {
            userNum: sessionStorage.getItem("userNum"),
            type: ' ',
            startDate: null,
            exDate: null,
            monthMin: null,
            monthMax: null,

            firstStep: true,
            secondStep: false,
            showcalculatedDate: false,
            showMonth: false,
           



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
        validnumInput(field) {
            // 입력값을 숫자로 변환
            this[field] = Number(this[field]);

            // 최소값과 최대값 설정
            const minValue = 1;
            const maxValue = 7;

            // 최소값 미만 또는 최대값 초과인 경우 최소값 또는 최대값으로 설정
            if (this[field] < minValue) {
                this[field] = minValue;
            } else if (this[field] > maxValue) {
                this[field] = maxValue;
            }
        },
        updateDate() {

            const selectedDate = new Date(this.startDate);
            const plusDate = this.exDate;

            selectedDate.setDate(selectedDate.getDate() + plusDate);

            // YYYY-MM-DD 형식으로 변환
            const calculatedDate = selectedDate.toISOString().split('T')[0];

            // 계산된 날짜를 데이터에 저장
            this.exDate = calculatedDate;
            this.showcalculatedDate = true;
        },
        finalMonth() {
            this.showMonth = true;
        },

        MaxValue(value, maxValue) {
            if (value > maxValue) {
                this[value === this.monthMin ? 'monthMin' : 'monthMax'] = maxValue;
            }
        },
        saveAutoMatching() {
            const self = this;
            let formdata = new FormData();
            formdata.append('userNum', self.userNum)
            formdata.append('type', self.type)
            formdata.append('startDate', self.startDate)
            formdata.append('exDate', self.exDate)
            formdata.append('monthMin', self.monthMin)
            formdata.append('monthMax', self.monthMax)
            formdata.append('isMatching', 0)
            self.$axios.post('http://localhost:8988/automatching', formdata)
                .then(function (res) {
                    if (res.status == 200) {
                        alert('자동매칭 등록이 완료 되었습니다')
                        self.$router.push("/MyInfoSideBar")
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

.ottselect {
    font-size: 28px;
    margin-top: 4%;
}

.ottname {
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;
    margin-top: 4%;
    margin-bottom: 1%;
}

.radiobtn {
    opacity: 0;
    padding: 1%;
}

img {
    height: 120px;
    width: 120px;
    border-radius: 23px;
    cursor: pointer;
    display: block;
}

.otttypedetail {
    height: 100px;
    width: 100px;
    border-radius: 23px;
    margin-bottom: 10%;
}

.DateUntill {
    text-align: center;
    font-family: 'AppleSDGothicNeoB';
    font-size: 16px;
    color: #444444;
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
    padding: 10px 20px;
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
    font-size: 16px;
    margin: 4% 0 0 0%;
    font-family: 'AppleSDGothicNeoB';

    /*위치조정*/


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

.autoinfo {
    flex-basis: 50%;
    padding-top: 10px;
    margin: 6% 15% 0 0%;

}


.autodetail {
    flex-basis: 50%;
    padding: 10px;
    margin: 8% 20% 0 0%;
}

input::placeholder {
    color: #444444;
}

input::data-placeholder {
    color: #444444;
}

input[type=date] {
    width: 150px;
    height: 35px;
    font-size: 15px;
    border: 0;
    border-radius: 15px;
    outline: none;
    text-align: center;
    padding-left: 10px;
    margin-bottom: 3%;
    background-color: rgb(233, 233, 233);
}

input[type=text],
input[type=number] {
    width: 150px;
    height: 35px;
    font-size: 15px;
    border: 0;
    border-radius: 15px;
    outline: none;
    text-align: center;
    padding-left: 10px;
    margin-bottom: 3%;
    background-color: rgb(233, 233, 233);
    color: #444444
}

input[type=date]::before {
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