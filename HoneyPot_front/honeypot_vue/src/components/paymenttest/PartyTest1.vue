<template>
    <div class="test1">
        파티 생성/찾기(100원 결제/환불)<br>
        <button v-on:click="add100">결제수단 유효성 확인</button>
    </div>
    <br><br><br>
    <div class="test2">
        정기결제 테스트<br>
        <button v-on:click="recurring">정기결제</button>
    </div>
</template>


<script>
export default {
    name: 'PartyTest1',
    data() {
        return {
            // userNum: 'HNP76554321', // 로그인 후 불러왔다고 가정
            userNum: 'HNP1234567'
        }
    },
    created: function() {

    },
    methods: {
        add100() {
            const self = this;

            let now = new Date();
            let fullyear = now.getFullYear().toString();
            let year = now.getFullYear().toString().slice(2);
            let month = (now.getMonth() + 1).toString().padStart(2, '0');
            let date = now.getDate().toString().padStart(2, '0');
            let hh = now.getHours().toString().padStart(2, '0');
            let mm = now.getMinutes().toString().padStart(2, '0');
            let ss = now.getSeconds().toString().padStart(2, '0');
            let orderNum = 'HNP' + year + month + date + hh + mm + ss;
            alert(orderNum);
            let payDate = fullyear + '-' + month + '-' + date;
            alert(payDate);
            let totalPayment = 100;
            let history = '결제수단 유효성 확인';

            let formdata = new FormData();
            formdata.append('orderNum', orderNum);
            formdata.append('userNum', self.userNum);
            formdata.append('payDate', payDate);
            formdata.append('totalPayment', totalPayment);
            formdata.append('history', history);

            self.$axios.post('http://localhost:8988/payments/add100', formdata)
            .then(function(res) {
                if(res.status == 200) {
                    let checkMsg = res.data.checkMsg;
                    let newDto = res.data.newDto;
                    let nextStep = res.data.nextStep; 
                    alert(checkMsg);
                    alert(newDto);
                    alert(nextStep);
                    // nextStep = 0 일 때, 유효성 확인(100원 결제/환불 완료) 됐으므로 게시글 등록 마무리
                    // nextStep = 1 일 때, 유효성 확인 실패했으므로 게시글 등록 x
                    // 게시글 등록 마무리되면 바로 이어서 100원 결제 저장된 payment 테이블 부분 마지막회차, 게시글번호, 정기결제일 부분 수정하기(@Postmapping 부분수정)
                    if(nextStep == 0) {
                        let formdata2 = new FormData();
                        formdata2.append("orderNum", orderNum);
                        formdata2.append("boardNum", 2222);
                        formdata2.append("finalInstallment", 6);
                        formdata2.append("recurringDay", 15); 
                        self.$axios.post('http://localhost:8988/payments/update100', formdata2)
                        .then(function(res) {
                            if(res.status == 200) {
                                let newDto = res.status.newDto;
                                alert(newDto);
                            } else {
                                alert("에러코드: " + res.status);
                            }
                        })
                    }
                    
                } else {
                    alert("에러코드: " + res.status);
                }
            })
        },
        recurring() {
            const self = this;
            self.$axios.post('http://localhost:8988/payments/recurringPayments')
            .then(function(res) {
                if(res.status == 200) {
                    let msg = res.data.msg;
                    let who = res.data.who;
                    alert(msg);
                    alert(who);
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