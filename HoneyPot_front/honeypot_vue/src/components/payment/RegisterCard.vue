<template>
    <div class="myBankInfo">
        <p class="menuTitle">결제 수단 관리</p>
        <hr>
        <p class="menuComment">본인 명의의 카드만 등록 가능합니다. 허니팟은 고객의 카드 정보를 저장하지 않습니다.</p>
        <div class="has-billing-key" v-if="isCard == false">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <div class="input-group">
                        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                class="bi bi-emoji-sunglasses" viewBox="0 0 16 16">
                                <path
                                    d="M4.968 9.75a.5.5 0 1 0-.866.5A4.498 4.498 0 0 0 8 12.5a4.5 4.5 0 0 0 3.898-2.25.5.5 0 1 0-.866-.5A3.498 3.498 0 0 1 8 11.5a3.498 3.498 0 0 1-3.032-1.75zM7 5.116V5a1 1 0 0 0-1-1H3.28a1 1 0 0 0-.97 1.243l.311 1.242A2 2 0 0 0 4.561 8H5a2 2 0 0 0 1.994-1.839A2.99 2.99 0 0 1 8 6c.393 0 .74.064 1.006.161A2 2 0 0 0 11 8h.438a2 2 0 0 0 1.94-1.515l.311-1.242A1 1 0 0 0 12.72 4H10a1 1 0 0 0-1 1v.116A4.22 4.22 0 0 0 8 5c-.35 0-.69.04-1 .116z" />
                                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-1 0A7 7 0 1 0 1 8a7 7 0 0 0 14 0z" />
                            </svg>
                        </span>
                        <input v-model="cardName" type="text" class="form-control" id="cardName" size="10" readonly>
                        <button @click="modalOpenAdd">결제수단 등록</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="has-billing-key" v-if="isCard == true">
            <div class="row justify-content-center">
                <div class="col-md-10">
                    <div class="input-group">
                        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                class="bi bi-emoji-sunglasses" viewBox="0 0 16 16">
                                <path
                                    d="M4.968 9.75a.5.5 0 1 0-.866.5A4.498 4.498 0 0 0 8 12.5a4.5 4.5 0 0 0 3.898-2.25.5.5 0 1 0-.866-.5A3.498 3.498 0 0 1 8 11.5a3.498 3.498 0 0 1-3.032-1.75zM7 5.116V5a1 1 0 0 0-1-1H3.28a1 1 0 0 0-.97 1.243l.311 1.242A2 2 0 0 0 4.561 8H5a2 2 0 0 0 1.994-1.839A2.99 2.99 0 0 1 8 6c.393 0 .74.064 1.006.161A2 2 0 0 0 11 8h.438a2 2 0 0 0 1.94-1.515l.311-1.242A1 1 0 0 0 12.72 4H10a1 1 0 0 0-1 1v.116A4.22 4.22 0 0 0 8 5c-.35 0-.69.04-1 .116z" />
                                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-1 0A7 7 0 1 0 1 8a7 7 0 0 0 14 0z" />
                            </svg>
                        </span>
                        <input v-model="cardName" type="text" class="form-control" id="cardName" size=10 readonly>
                        <button @click="deleteCard">결제수단 삭제</button>
                    </div>
                </div>
            </div>
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
                        <button v-on:click="registerCard">결제수단 등록</button>
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
                            self.cardName = res.data.cardName;
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
    height: 40%;
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