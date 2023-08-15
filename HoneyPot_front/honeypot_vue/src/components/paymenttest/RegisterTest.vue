<template>
    <div class="test1">
        결제 수단 등록(빌링키 발급 및 저장)<br>
        카드번호:
        <input v-model="cardNum1" type="text" size="5">-<input v-model="cardNum2" type="text" size="5">-<input v-model="cardNum3" type="text" size="5">-<input v-model="cardNum4" type="text" size="5"><br>
        카드 유효기간:
        <input v-model="cardExpiry1" type="text" size="5">-<input v-model="cardExpiry2" type="text" size="3"><br>
        생년월일:
        <input v-model="birth" type="text" size="6"><br>
        카드 비밀번호 앞 두자리:
        <input v-model="pwdDigit2" type="text" size="2"><br>
        <button v-on:click="registerCard">결제수단 등록</button>
    </div>
    <br><br><br>
    <div class="test2">
        결제수단 삭제(PG사 DB에서 빌링키 삭제 & 회원정보 빌링키 삭제)<br>
        <button v-on:click="deleteCard">결제수단 삭제</button>
    </div>
</template>


<script>
export default {
    name: 'RegisterTest',
    data() {
        return {
            cardNum1: '5118',
            cardNum2: '4500',
            cardNum3: '0791',
            cardNum4: '6781',
            cardExpiry1: '2027',
            cardExpiry2: '12',
            birth: '930502',
            pwdDigit2: '13',
            userNum: 'HNP1234567', // 로그인 후 불러왔다고 가정
            token: ''
        }
    },
    created: function() {

    },
    methods: {
        registerCard() {
            const self = this;
            let cardNum = self.cardNum1 + '-' + self.cardNum2 + '-' + self.cardNum3 + '-' + self.cardNum4;
            let cardExpiry = self.cardExpiry1 + '-' + self.cardExpiry2;
            let billingKey = self.userNum + '_' + self.cardNum4
            let formdata = new FormData();
            formdata.append('cardNum', cardNum);
            formdata.append('cardExpiry', cardExpiry);
            formdata.append('birth', self.birth);
            formdata.append('cardPwd', self.pwdDigit2);
            formdata.append('billingKey', billingKey);
            formdata.append('userNum', self.userNum);
            self.$axios.post('http://localhost:8988/payments/registerCards', formdata)
            .then(function(res) {
                if(res.status == 200) {
                    let msg = res.data.msg;
                    let accessToken = res.data.accessToken;
                    // let withdrawl = res.data.withdrawl;
                    alert(msg);
                    alert(accessToken);
                } else {
                    alert("에러코드: " + res.status);
                }
            });
        },
        deleteCard(){
            const self = this;
            let formdata = new FormData();
            formdata.append("userNum", self.userNum);
            self.$axios.post('http://localhost:8988/payments/deleteCards', formdata)
            .then(function(res) {
                if(res.status == 200) {
                    let msg = res.data.msg;
                    alert(msg);
                } else {
                    alert("에러코드: " + res.status);
                }
            })
            
        }
    }
}
</script>


<style scoped>

</style>