<template lang="">
<div class="host-board-detail">

    <div class="otttypecontainer">
        <img class="otttypedetail" v-if="dto.type.type === 'Netflix'" :src="require('@/assets/images/netflix.png')" alt="Netflix Logo" />
        <img class="otttypedetail" v-else-if="dto.type.type === 'Tving'" :src="require('@/assets/images/tving.png')" alt="Tving Logo" />
        <img class="otttypedetail" v-else-if="dto.type.type === 'Wave'" :src="require('@/assets/images/wave1.png')" alt="Wave Logo" />
        <img class="otttypedetail" v-else-if="dto.type.type === 'Disney+'" :src="require('@/assets/images/disney.png')" alt="Disney Logo" />
        <img class="otttypedetail" v-else-if="dto.type.type === 'Watcha'" :src="require('@/assets/images/watcha.png')" alt="Watcha Logo" />
        <img class="otttypedetail" v-else-if="dto.type.type === 'Apple'" :src="require('@/assets/images/apple.png')" alt="Apple Logo" /> 
        <span style="padding-left:2%; font-size:23px;"> {{ dto.userNum.nickname}} 님이 모집하는 {{dto.type.type}} 파티입니다.
       </span>
    </div>

    <div class="info" >

        <div class="infomention"> 
        <div v-if="remainingDays === 0">  
            지금 파티에 가입하고 <span style="color : #Fdd000; "> 내일부터 </span>{{dto.type.type}} 프리미엄을 이용해 보세요. </div>
          <div v-else-if="remainingDays > 0">
            지금 파티에 가입하고  <span style="color : #Fdd000; font-weight :900; font-size:30px;"> {{remainingDays}} </span> 일 후 {{dto.type.type}} 프리미엄을 이용해 보세요. </div>
        </div>

        <div class="infoprice">
    <div class="left">
        <span style="font-size:20px; border-bottom: 3px solid #Fdd000"> 파티요금 </span> <br />
        <br/>
        <span>파티 분담금 (월)</span> <br />
        <span>허니팟 수수료 (10%)</span>
    </div>
    <div class="right">
        <br />
        <br/>
        <span>{{ calculateValue(dto.type.price, dto.maxPpl) }} (원) </span> <br/>
        <span>{{ Math.round(calculateValue(dto.type.price, dto.maxPpl) * 0.9) }} (원) </span>
    </div>
</div>


        <div class="infodeposit">
            <div class="left" style="margin-top:2%;">
           <span> 허니팟 보증금(최초 1회) </span>
            </div>
            <div class="right" style="margin-top:2%;">
            <span>  {{dto.type.price}}  (원)  </span>    
        </div>
                  
    
    </div>
    </div>

    <button v-if="dto.userNum.userNum !== userNum" @click="addParty">파티 참여하기</button>
<button v-else @click="delParty"> 파티 삭제하기 </button>
</div>

</template>
<script>

import dayjs from 'dayjs'
export default {
    name: 'HostBoardDetail',
    data() {
        return {
            boardNum: this.$route.query.boardNum,
            userNum: sessionStorage.getItem("userNum"),
            remainingDays: null,
            dto: {
                userNum: '',
                type: '',
                subStart: null,
                subEnd: null,
                month: '',
                maxPpl: ''
            }


        }
    },
    created: function () {
        const self = this;
        console.log(self.boardNum)
        self.$axios.get('http://localhost:8988/hostboard/' + self.boardNum)
            .then(function (res) {
                if (res.status == 200) {
                    self.dto = res.data.dto
                    self.dto.subStart = dayjs(self.dto.subStart).format('YYYY-MM-DD')
                    self.dto.subEnd = dayjs(self.dto.subEnd).format('YYYY-MM-DD')
                    self.remainingDays = self.calculateDate(res.data.dto.subStart)
                }
            })
    },
    methods: {
        addParty() {
            const self = this;
            if (self.userNum != null){
            self.$axios.post('http://localhost:8988/partygroup/' + self.boardNum + '/' + self.userNum)
                .then(function (res) {
                    if (res.status == 200) {
                        if (res.data.msg) { //msg가 있으면 오류가 있는 것임 
                            alert(res.data.msg)
                        } else {
                            alert("성공적으로 파티에 가입 되었습니다")
                            self.$router.push({ name: 'MyPartyList', query: { userNum: self.userNum } })
                        }
                    }
                })
            }else {
                alert ("로그인 후 진행 해 주세요")
                self.$router.push("/LoginPage")
                
            }
        },
        delParty(){
            const self = this;
            self.$axios.delete('http://localhost:8988/hostboard/' + self.boardNum )
            .then(function(res){
                if (res.status == 200) {
                    if(res.data.msg){
                        alert(res.data.msg)
                    } else {
                        alert('파티가 삭제 되었습니다')
                        self.$router.push("/HostBoardList")
                    }
                }
            })
           
        },
        calculateValue(price, maxPpl) {
            // price와 maxPpl를 이용하여 계산한 값을 반올림해서 반환
            const calculatedValue = Math.round(price / maxPpl);
            return calculatedValue;
        },
        calculateDate(subStart) {
            //시작 날짜 - 오늘 날짜 계산해서 오늘날짜보다 전이면 내일 이라고 표기하기
            const today = dayjs();
            const startDate = dayjs(subStart);

            // 시작 날짜가 현재 날짜보다 이전인지 확인
            if (startDate.isBefore(today)) {
                return 0; // Return 0 if it's in the past
            } else {
                // 시작 날짜가 현재 날짜보다 이후인 경우, 날짜 차이를 계산하여 반환
                return startDate.diff(today, 'day');
            }
        }
    }

}
</script>

<style scoped>
p {
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;
}

span {
    font-family: 'AppleSDGothicNeoB';
    font-size: 17px;
}

.otttypedetail {
    height: 100px;
    width: 100px;
    border-radius: 23px;
    vertical-align: middle;
}

.otttypecontainer {
    margin-top: 4%;
    text-align: center;
}

.infomention {
    margin-top: 3%;
    font-family: 'AppleSDGothicNeoB';
    font-size: 22px;
}

.infoprice,
.infodeposit {
    display: flex;
    justify-content: space-between;
    margin-left: 40px;
}

.left {
    text-align: left;
    margin-left: 32%;
    margin-top: 5%;

}

.right {
    text-align: right;
    margin-right: 35%;
    margin-top: 5%;
}

.title {
    margin-top: 5%
}

div {
    color: #444444;
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

    margin: 80px 0 0 70px;
    /*위치조정*/
    font-weight: 400;
    /*폰트 굵기 -> 글씨체 적용하면 좀 바뀔 것 같은데 굵은게 예쁠것 같음 */
    font-size: 16px;
    /* 지금 버튼 사이즈에는 이게 딱임 */

}

button:hover {
    background-color: white;
}
</style>