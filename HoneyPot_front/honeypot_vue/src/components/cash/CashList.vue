<template>
    <div class="myBankInfo">
        <p class="menuTitle">캐시 내역</p>
        <hr>
        <p class="menuComment">캐시 적립 내역</p>
        <div v-for="(item, index) in list" :key="index">
            {{ item.addDate }} / {{ item.amount }} / {{ item.msg }}
        </div>
    </div>
</template>


<script>
export default {
    name: 'CashList',
    data() {
        return {
            userNum: sessionStorage.getItem('userNum'),
            list: [],
            modalCheckAdd: true
        }
    },
    created: function () {
        this.getBillingKey();
    },
    methods: {
        // add 모달창 열기
        modalOpenAdd() {
            const self = this;
            self.modalCheckAdd = !self.modalCheckAdd;
        },
        modalCloseAdd() {
            this.modalCheckAdd = !this.modalCheckAdd;
        },
        getBillingKey() {
            const self = this;
            self.$axios.get('http://localhost:8988/cashs/cashList/' + self.userNum)
                .then(function (res) {
                    if (res.status == 200) {
                        self.list = res.data.list;
                    } else {
                        alert("에러코드: " + res.status);
                    }
                });
        }
    }
}
</script>


<style scoped>
.menuTitle {
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;
    text-align: left;
}

.menuComment {
    font-family: 'AppleSDGothicNeoR';
    font-size: 14px;
    color: #bdbdbd;
    text-align: left;
}

.modal-wrap-add {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.6);
    z-index: 2;
}

.modal-container-add {
    /* overflow: auto; */
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 400px;
    height: 30%;
    background: #fff;
    box-shadow: 0 20px 10px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    padding-top: 23px;
    box-sizing: border-box;
    transition: width 0.3s ease;
    /* display: flex; */
    /* 가운데정렬 */
    /* flex-direction: column; */
    /* 내용 수직으로 배치 */
    /* justify-content: center; */
    /* 수직방향 가운데 정렬 */
    /* align-items: center; */
    /* 수평방향 가운데 정렬 */
}
</style>