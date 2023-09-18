<template>

<p class="menuTitle">인출 계좌 관리</p><hr>
<p class="menuComment">본인 명의의 계좌만 등록 가능합니다.</p>

<div class="myBankInfo">
<div class="row justify-content-center" style="margin-top:120px;">
<div class="col-md-2" style="postion: absolute; margin-right:-15px">
<select v-model="bankCode" class="form-select form-select-sm" aria-label="Small select example">
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
</div>

<div class="col-md-5" style="position:relative">
<input v-model="bankAcc" class="form-control form-control-sm" type="text" placeholder="계좌번호를 입력주세요('-' 제외)" aria-label=".form-control-sm example">
</div>

<div class="col-md-2" style="margin-left:-45px">
<button @click="bankCheck">수정완료</button>
</div>
</div>
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
        this.getInfo();
        console.log(this.userNum + " / " + this.name);
    }, 
    
    methods: {
        getInfo() { // 기존 정보 가져오기
            const self = this;

            self.$axios.get('http://localhost:8988/members/' + self.userNum)
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
                    window.location.href = "/MyInfoSidebar";
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

button {
    padding: 4px 13px;
    text-align: center;
    text-decoration: none;
    display: inline-block;

    transition-duration: 0.4s;
    cursor: pointer;
    background-color: #Fdd000;
    color: #444444;
    border: 2px solid #Fdd000;
    border-radius: 7px;

    font-weight: 500; /*폰트 굵기 -> 글씨체 적용하면 좀 바뀔 것 같은데 굵은게 예쁠것 같음 */
    font-size: 13.5px; /* 지금 버튼 사이즈에는 이게 딱임 */
    font-family: 'AppleSDGothicNeoB';
    
}
    
button:hover {
    background-color: white;
    color: #444444;
    font-family: 'AppleSDGothicNeoB';
}

.myBankInfo {
    font-family: 'AppleSDGothicNeoR';
}

input::-webkit-input-placeholder {
  color: #BDBDBD;
}

</style>