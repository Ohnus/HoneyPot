<template>
    <div class="myBankInfo">
        <p class="menuTitle">결제 수단 관리</p>
        <hr>
        <p class="menuComment">본인 명의의 카드만 등록 가능합니다. 허니팟은 고객의 카드 정보를 저장하지 않습니다.</p>
        <div class="has-billing-key" v-if="isCard == false">
            <div class="row justify-content-center" style="margin-top: 130px;">
                <div class="col-md-4">
                    <input v-model="cardName" type="text" class="form-control" id="cardName"
                        style="postion: absolute; margin-left: 60px; text-align: center; height: 35px;" readonly>
                </div>
                <div class="col-md-3">
                    <button @click="modalOpenAdd" style="position:relative; margin-left:-30px">결제수단 등록</button>
                </div>
            </div>
        </div>
        <div class="has-billing-key" v-if="isCard == true">
            <div class="row justify-content-center" style="margin-top: 130px;">
                <div class="col-md-4">
                    <input v-model="cardName" type="text" class="form-control" id="cardName"
                        style="postion: absolute; margin-left: 60px; text-align: center; height: 35px;" readonly>
                </div>
                <div class="col-md-3">
                    <button @click="deleteCard" style="position:relative; margin-left:-30px">결제수단 삭제</button>
                </div>
            </div>
        </div>

        <div class="addKey" id="addKey" style="display: none;">
            <div class="row justify-content-center">
                <div class="col-md-3">
                    카드 등록
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-2">
                    <input v-model="cardNum1" type="text" style="text-align: center;" class="form-control">
                </div>
                <div class="col-md-2">
                    <input v-model="cardNum2" type="text" style="text-align: center;" class="form-control">
                </div>
                <div class="col-md-2">
                    <input v-model="cardNum3" type="text" style="text-align: center;" class="form-control">
                </div>
                <div class="col-md-2">
                    <input v-model="cardNum4" type="text" style="text-align: center;" class="form-control">
                </div>
            </div>
            <div class="row justify-content-center">
                <div class="col-md-1">
                    <input v-model="cardExpiry1" type="text" style="text-align: center;" class="form-control">
                </div>
                <div class="col-md-1">
                    <input v-model="cardExpiry2" type="text" style="text-align: center;" class="form-control">
                </div>
            </div><br>
            <div class="row justify-content-center">
                <div class="col-md-1">
                    <input v-model="birth" type="text" style="text-align: center;" class="form-control">
                </div>
            </div><br>
            <div class="row justify-content-center">
                <div class="col-md-1">
                    <input v-model="pwdDigit2" type="password" style="text-align: center;" class="form-control">
                </div>
            </div><br>
            <button v-on:click="registerCard">결제수단 등록</button>&nbsp;
            <button v-on:click="modalCloseAdd">취소</button>
        </div>



        <!-- 결제수단 모달창 -->
        <div class="modal-wrap-add" v-show="modalCheckAdd" @click="modalCloseAdd" id="modalWrapAdd">
            <div class="modal-container-add" @click.stop="" id="containerAdd">
                <div class="addKey">
                    <div class="row justify-content-center">
                        <div class="col-md-3">
                            카드 등록
                        </div>
                    </div><br>
                    <div class="row justify-content-center">
                        <div class="col-md-2">
                            <input v-model="cardNum1" type="text" style="text-align: center;" class="form-control">
                        </div>
                        <div class="col-md-2">
                            <input v-model="cardNum2" type="text" style="text-align: center;" class="form-control">
                        </div>
                        <div class="col-md-2">
                            <input v-model="cardNum3" type="text" style="text-align: center;" class="form-control">
                        </div>
                        <div class="col-md-2">
                            <input v-model="cardNum4" type="text" style="text-align: center;" class="form-control">
                        </div>
                    </div> <br>
                    <div class="row justify-content-center">
                        <div class="col-md-2">
                            <input v-model="cardExpiry1" type="text" style="text-align: center;" class="form-control">
                        </div>
                        <div class="col-md-2">
                            <input v-model="cardExpiry2" type="text" style="text-align: center;" class="form-control">
                        </div>
                    </div><br>
                    <div class="row justify-content-center">
                        <div class="col-md-3">
                            <input v-model="birth" type="text" style="text-align: center;" class="form-control">
                        </div>
                    </div><br>
                    <div class="row justify-content-center">
                        <div class="col-md-2">
                            <input v-model="pwdDigit2" type="password" style="text-align: center;" class="form-control">
                        </div>
                    </div><br>
                    <button v-on:click="registerCard">결제수단 등록</button>&nbsp;
                    <button v-on:click="modalCloseAdd">취소</button>
                </div>
            </div>
        </div>
    </div>
</template>


<script>
export default {
    name: 'RegisterCard',
    data() {
        return {
            userNum: sessionStorage.getItem('userNum'),
            billingKey: '',
            showCard4: '',
            isCard: false,
            cardName: '',
            modalCheckAdd: false,
            cardNum1: '9436',
            cardNum2: '4607',
            cardNum3: '0756',
            cardNum4: '1024',
            cardExpiry1: '2025',
            cardExpiry2: '01',
            birth: '890617',
            pwdDigit2: '05',
            token: ''
        }
    },
    created: function () {
        this.getBillingKey();
    },
    methods: {
        openAdd() {
            document.getElementById('addKey').style.display = 'block';
        },
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
            let formdata = new FormData();
            formdata.append('userNum', self.userNum);
            self.$axios.post('http://localhost:8988/payments/getBillingKey', formdata)
                .then(function (res) {
                    if (res.status == 200) {
                        self.billingKey = res.data.billingKey;
                        self.showCard4 = self.billingKey.split('_')[1];
                        if (self.billingKey != 0) {
                            self.isCard = true;
                            self.getCardName(self.billingKey);
                        } else {
                            self.isCard = false;
                            self.getCardName(self.billingKey);
                        }
                    } else {
                        alert("에러코드: " + res.status);
                    }
                });
        },
        getCardName(billingKey) {
            const self = this;
            if (billingKey != 0) {
                let formdata = new FormData();
                formdata.append('customerUid', billingKey);
                self.$axios.post('http://localhost:8988/payments/getCardName', formdata)
                    .then(function (res) {
                        if (res.status == 200) {
                            self.showCard4 = self.billingKey.split('_')[1];
                            self.cardName = res.data.cardName + ' - ' + self.showCard4;

                        } else {
                            alert("에러코드: " + res.status);
                        }
                    });
            } else {
                self.cardName = "등록된 결제수단이 없습니다.";
            }
        },
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
                .then(function (res) {
                    if (res.status == 200) {
                        self.modalCheckAdd = !self.modalCheckAdd;
                        window.location.href = "/MyInfoSideBar";
                        // let withdrawl = res.data.withdrawl;
                    } else {
                        alert("에러코드: " + res.status);
                    }
                });
        },
        deleteCard() {
            const self = this;
            let formdata = new FormData();
            formdata.append("userNum", self.userNum);
            self.$axios.post('http://localhost:8988/payments/deleteCards', formdata)
                .then(function (res) {
                    if (res.status == 200) {
                        // let msg = res.data.msg;
                        window.location.href = "/MyInfoSideBar";
                    } else {
                        alert("에러코드: " + res.status);
                    }
                })

        }
    }
}
</script>


<style scoped>
.myBankInfo {
    font-family: 'AppleSDGothicNeoB';
}

button {
    padding: 5px 13px;
    text-align: center;
    text-decoration: none;
    display: inline-block;

    transition-duration: 0.4s;
    cursor: pointer;
    background-color: #Fdd000;
    color: #444444;
    border: 2px solid #Fdd000;
    border-radius: 7px;

    font-weight: 500;
    /*폰트 굵기 -> 글씨체 적용하면 좀 바뀔 것 같은데 굵은게 예쁠것 같음 */
    font-size: 14px;
    /* 지금 버튼 사이즈에는 이게 딱임 */
    font-family: 'AppleSDGothicNeoB';

}

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
    width: 500px;
    height: 50%;
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