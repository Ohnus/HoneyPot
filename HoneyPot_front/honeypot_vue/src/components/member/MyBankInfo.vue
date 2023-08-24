<template>
    <div class="myBankInfo">
    
        <select v-model="bankCode">
            <option value="null">은행선택</option>
            <option value="004">국민은행</option>
            <option value="020">우리은행</option>
            <option value="088">신한은행</option>
            <option value="003">기업은행</option>
            <option value="023">SC제일은행</option>
            <option value="011">농협은행</option>
            <option value="005">외환은행</option>
            <option value="090">카카오뱅크</option>
            <option value="032">부산은행</option>
            <option value="071">우체국</option>
            <option value="031">대구은행</option>
            <option value="035">제주은행</option>
            <option value="007">수협은행</option>
            <option value="027">씨티은행</option>
        </select>
        <input v-model="bankAcc" type="text" placeholder="계좌번호를 입력주세요('-' 제외)">
    
        <button @click="bankCheck">수정완료</button>

    </div>
</template>
    
<script>
    
export default {
    name: 'MyBankInfo',
    data() {
        return {
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
        getBankInfo() { // 기존 정보 가져오기
            const self = this;

            self.$axios.get('http://localhost:8988/members/edit/' + self.userNum)
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


        bankCheck() {  // 계좌정보 유효성 체크
            const self = this;

            const data = {
                bankCode: self.bankCode,
                bankAcc: self.bankAcc
            }

            console.log(self.name + " / " + this.bankCode + " / " + this.bankAcc);

            self.$axios.get("http://localhost:8988/members/certifications/checkAccount", { params : data })
            .then(function (res) {
                if (res.status == 200) {
                    console.log("예금주명: " + res.data.bankHolderInfo);
                    console.log(self.name);

                    if (res.data.bankHolderInfo != null) {
                        if (self.name == res.data.bankHolderInfo) {
                            self.editBankInfo();
                        } else if (self.name != res.data.bankHolderInfo) {
                            alert ('본인 명의의 계좌만 등록 가능합니다.')
                        }
                    } else {
                        alert ('유효한 계좌정보가 아닙니다.')
                    }        
                } else {
                    alert ('에러코드' + res.status)
                }
            })
            .catch(function (error) {
                console.error(error);
            }); 
        },

        editBankInfo() {  // 새 정보 저장
            const self = this;
    
            const formdata = new FormData();            
            
            formdata.append('bankCode', self.bankCode);
            formdata.append('bankAcc', self.bankAcc);

            console.log(self.name + " / " + self.bankCode + " / " + self.bankAcc);

            self.$axios.post("http://localhost:8988/members/editBankInfo/" + self.userNum, formdata)
            .then(function (res) {
                if (res.status == 200) {
                    const dto = res.data.dto;
                    console.log(dto);
                    window.location.href = "/MyBankInfo";
                } else {
                    alert ('에러코드' + res.status)
                }
            })
            .catch (function (error) {
                console.error (error)
            })
        }
        
    }
}
</script>