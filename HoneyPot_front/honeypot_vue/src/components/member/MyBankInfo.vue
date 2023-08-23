<template>
    <div class="myBankInfo">
    
        <input v-model="bankCode" type="text">
        <input v-model="bankAcc" type="text" placeholder="계좌번호를 입력주세요('-' 제외)">
    
        <button @click="editBankInfo">수정완료</button>

    </div>
</template>
    
<script>
    
export default {
    name: 'MyBankInfo',
    data() {
        return {
            bankValid: false,       // 계좌번호 유효성 체크
    
            userNum: sessionStorage.getItem('userNum'),
            name: self.name,
            bankCode: '',
            bankAcc: ''
        }
    },
    
    created() {
        this.getBankInfo();
    }, 
    
    methods: {
        getInfo() { // 기존 정보 가져오기
            const self = this;

            self.$axios.get('http://localhost:8988/members/edit' + self.userNum)
            .then(function (res) {
                if(res.status == 200) {
                    console.log("name: " + res.data.name);
                    console.log("bankCode: " + res.data.bankCode);
                    console.log("bankAcc: " + res.data.bankAcc);

                    self.name = res.data.name;
                    self.bankCode = res.data.bankCode;
                    self.bankAcc = res.data.bankAcc;
                } else {
                    alert ('에러코드' + res.status)
                }
            })
            .catch(function (error) {
                console.error(error);
            })
        },

    
        editBankInfo() {
            const self = this;
    
            const formdata = new FormData();

            formdata.append('bankCode', self.bankCode);
            formdata.append('bankAcc', self.bankAcc);
    
            console.log(self.name + " / " + self.bankCode + " / " + self.bankAcc);

            // 예금주명 체크
            self.$axios.get("http://localhost:8988/members/certifications/bankCheck", formdata)
            .then(function (res) {
                if (res.status == 200) {
                    console.log(res.data.bankHolderInfo);
                    console.log(self.name);

                    } if (self.name == res.data.bankHolderInfo) {
                        self.$axios.post("http://localhost:8988/members/editBankInfo" + self.userNum, formdata)
                        .then (function (res) {
                            if (res.status == 200) {
                                const dto = res.data.dto;
                                console.log(dto);
                                window.location.href = "/MyBankInfo";
                            } else {
                                alert ('에러코드' + res.status);
                            }
                        });
                    } else {
                        alert ('에러코드' + res.status);
                    }
                })
                .catch(function (error) {
                    console.error(error);
                });
            }
        }
    }
</script>