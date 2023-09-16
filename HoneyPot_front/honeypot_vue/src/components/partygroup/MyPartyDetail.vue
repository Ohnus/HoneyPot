<template lang="">
    <div class="host-board-detail">
    
        <div class="otttypecontainer">
            <img class="otttypedetail" v-if="dto.type.type === 'Netflix'" :src="require('@/assets/images/netflix.png')" alt="Netflix Logo" />
            <img class="otttypedetail" v-else-if="dto.type.type === 'Tving'" :src="require('@/assets/images/tving.png')" alt="Tving Logo" />
            <img class="otttypedetail" v-else-if="dto.type.type === 'Wave'" :src="require('@/assets/images/wave1.png')" alt="Wave Logo" />
            <img class="otttypedetail" v-else-if="dto.type.type === 'Disney+'" :src="require('@/assets/images/disney.png')" alt="Disney Logo" />
            <img class="otttypedetail" v-else-if="dto.type.type === 'Watcha'" :src="require('@/assets/images/watcha.png')" alt="Watcha Logo" />
            <img class="otttypedetail" v-else-if="dto.type.type === 'Apple'" :src="require('@/assets/images/apple.png')" alt="Apple Logo" /> 
            
            <span v-if="dto.userNum.userNum== userNum" style="padding-left:2%; font-size:23px;"> 본인이 운영하는 {{dto.type.type}} 파티입니다.  </span>
            <span v-else style="padding-left:2%; font-size:23px;"> {{ dto.userNum.nickname}} 님이 모집 한 {{dto.type.type}} 파티입니다.
           </span>
        </div>
    
        <div class="info" >
    
            <div class="infomention"> 
                <div v-if="partygroupdto.startCheck ==0"> 
                    <span> 파티 <span style="color : #Fdd000; font-weight :500; font-size:26px;"> 시작 전</span> 입니다. </span>
                </div>
                <div v-else-if="partygroupdto.startCheck ==1"> 
                    <span> 파티가 <span style="color : #Fdd000; font-weight :500; font-size:26px;"> 진행 중 </span>입니다. </span>
                </div>
                <div v-else-if="partygroupdto.startCheck ==2"> 
                    <span> 파티가 <span style="color : #Fdd000; font-weight :500; font-size:26px;"> 종료 </span> 되었습니다. </span>
                </div>
                <div v-else-if="partygroupdto.startCheck ==3"> 
                    <span> 파티에 <span style="color : #Fdd000; font-weight :500; font-size:26px;"> 탈퇴 </span> 되셨습니다. </span>
                </div>
                <div v-else-if="partygroupdto.startCheck ==4"> 
                    <span> 파티 <span style="color : #Fdd000; font-weight :500; font-size:26px;"> 탈퇴 </span>를 진행 중 입니다. </span>
                </div>
            </div>


            <div class="infodate">
        <div class="left" >
            <span style="font-size:20px; border-bottom: 3px solid #Fdd000"> 파티 일정 </span> <br />
            <br/>
            <span>구독 시작일 </span> <br />
            <span>구독 종료일 </span>
        </div>
        <div class="right">
            <br />
            <br/>
            <span>{{ dto.subStart }} </span> <br/>
            <span>{{ dto.subEnd }} </span>
           </div>
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
            <span>{{ Math.round(calculateValue(dto.type.price, dto.maxPpl) * 0.1) }} (원) </span>
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
    <!-- 파티 삭제는 아직 파티가 시작이 안되었고, 내가 주인일때 가능 -->
    <button v-if="dto.userNum.userNum== userNum && partygroupdto.startCheck == 0 " @click="delParty"> <span>파티 삭제하기</span> </button>
    <!-- 파티 나가기는 내가 파티원인데 아직 파티가 시작이 안되었을 떄 가능 -->
<button v-else-if="dto.userNum.userNum != userNum && partygroupdto.startCheck == 0" @click="beforeoutParty"> <span>파티 나가기</span> </button>  
<!-- 파티 탈퇴는 내가 파티원이고 파티가 시작 되었을 때 나가고 싶을때 사용 -->
<button v-else-if="dto.userNum.userNum != userNum && partygroupdto.startCheck ==1" @click="outmsg=true,outout=false" v-show="outout"> <span>파티 탈퇴하기</span> </button>  
<div v-else class="noneofthem" >
<span class="noneofthem"> 더 이상 참여 하실 수 없습니다. </span> </div>

<div v-show="outmsg" class="outmsgprint">
    <div style="margin-top:3%">
    <span>정말 파티를 탈퇴 하시겠습니까?</span> <br/>
    <span>파티 기간이 한 달 미만이라면 탈퇴가 불가능해요.</span> <br/>
    <span> 또한, 파티장 보호를 위해 <br/> 대체 파티원을 찾지 못한다면 탈퇴가 불가능합니다.</span> <br/>
    <span>파티 탈퇴에 동의 하시면 확인 버튼을 눌러주세요</span><br/>
    </div>
    <div class="buttonclass">
    <button @click="outParty"> <span>동의</span> </button>
    <button @click="outmsg=false,outout=true"> <span>닫기</span> </button>
    </div>
  
</div>


</div>
    
    </template>
<script>
import dayjs from 'dayjs'
export default {
    name: 'MyPartyDetail',
    data() {
        return {
            boardNum: this.$route.query.boardNum,
            userNum: sessionStorage.getItem("userNum"),
         
            dto: {
                userNum: '',
                type: '',
                subStart: null,
                subEnd: null,
                month: '',
                maxPpl: ''
            },
            partygroupdto: {
                groupNum: '',
                startCheck: ''
            },
            outmsg: false,
            outout:true

        }
    },
    created: function () {
        const self = this;
        console.log(self.boardNum)
        console.log(self.userNum)
        self.$axios.get('http://localhost:8988/partygroup/' + self.boardNum + '/' + self.userNum)
            .then(function (res) {
                if (res.status == 200) {
                    self.partygroupdto = res.data.dto
                }

                self.$axios.get('http://localhost:8988/hostboard/' + self.boardNum)
                    .then(function (res) {
                        if (res.status == 200) {
                            self.dto = res.data.dto
                            self.dto.subStart = dayjs(self.dto.subStart).format('YYYY[년] M[월] D[일]')
                            self.dto.subEnd = dayjs(self.dto.subEnd).format('YYYY[년] M[월] D[일]')

                            
                        }
                    })
            })

    },

    methods: {

        delParty() {
            const self = this;
            self.$axios.delete('http://localhost:8988/hostboard/' + self.boardNum)
                .then(function (res) {
                    if (res.status == 200) {
                        if (res.data.msg) {
                            alert(res.data.msg)
                        } else {
                            alert('파티가 삭제 되었습니다')
                            self.$router.push("/HostBoardList")
                        }
                    }
                })

        },
        outParty(){
            const self = this;
            self.outmsg=false
            self.$axios.get('http://localhost:8988/partygroup/out/' + self.boardNum + '/' + self.userNum)
            .then(function(res){
                if(res.status ==200){
                    if(res.data.msg){
                        alert(res.data.msg); 
                        self.$router.push("/MyInfoSidebar")
                    } else {
                        alert('파티 종료일이 한달 미만으로 탈퇴가 취소 되었습니다')
                        self.$router.push("/MyInfoSidebar")
                    }

                }
            })

        },
        beforeoutParty() {
            const self = this;
            self.$axios.delete('http://localhost:8988/partygroup/' + self.partygroupdto.groupNum)
                .then(function (res) {
                    if (res.data.msg) {
                        alert(res.data.msg)
                        self.$router.push("/MyInfoSidebar")
                    }
                })
        },
        calculateValue(price, maxPpl) {
            // price와 maxPpl를 이용하여 계산한 값을 반올림해서 반환
            const calculatedValue = Math.round(price / maxPpl);
            return calculatedValue;
        },

        
    }
}

</script>

<style scoped>
p {
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;
}

span {
    font-family: 'AppleSDGothicNeoSB';
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
.infodeposit,
.infodate {
    display: flex;
    justify-content: space-between;
    margin-left: 40px;
}

.left {
    text-align: left;
    margin-left: 32%;
    margin-top: 2%;

}

.right {
    text-align: right;
    margin-right: 35%;
    margin-top: 2%;
}

.title {
    margin-top: 5%
}

div {
    color: #444444;
}

.noneofthem {
    margin-top: 3%;
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;

}
.outmsgprint{
    position: fixed;
    top: 50%; 
    left: 50%; 
    transform: translate(-50%, -50%); 
    z-index: 1000; 
    background-color: white;
    padding: 20px;
    border: 1px solid #ccc;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    width: 500px;
    height: 300px;
    text-align: center;
   
}

.buttonclass{
    margin-right: 13%;
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

    margin: 60px 0 0 70px;
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