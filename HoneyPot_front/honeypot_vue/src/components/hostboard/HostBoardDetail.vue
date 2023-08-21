<template lang="">
    <div>       
        <div>
                <div class="col">
                    글 번호 : {{boardNum}}
                </div>
                <div class="col">
                    사이트 : {{dto.type.type}}
                </div>
                <div class="col">
                    작성자 
                </div>
                <div class="col">
                    구독시작일  : {{dto.subStart}}
                </div>
                <div class="col">
                    구독기간
                </div>
        </div>

        <div @click="addParty"> 파티참여하기 </div>



    </div>
</template>
<script>
import dayjs from 'dayjs'
export default {
    name: 'HostBoardDetail',
    data() {
        return {
            boardNum: this.$route.query.boardNum,
            // userNum: sessionStorage.getItem('username'),
            userNum : 3,
            dto: {
                type: '',
                subStart: null,
                month: '',
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

                }
            })


    },
    methods :{
        addParty(){
            const self = this;
            self.$axios.post('http://localhost:8988/partygroup/' + self.boardNum + '/' + self.userNum)
            .then(function (res) {
                if (res.status == 200) {
                    if (res.data.msg) { //msg가 있으면 오류가 있는 것임 
                        alert (res.data.msg)
                    }else { 
                        alert("성공적으로 파티에 가입 되었습니다")
                   self.$router.push({ name: 'MyPartyList', query: { userNum: self.userNum } })
                    }
                }})

        }
    }

}
</script>
<style lang="">
    
</style>