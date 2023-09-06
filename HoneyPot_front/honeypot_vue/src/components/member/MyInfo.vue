<template>
<div class="myInfo">

    <div class="userImg">
    <img :src="profile" style="width:300px; height:300px">
    <input type="file" id="file" @change="previewImage" accept="image/*">
    <button>기본 이미지로 변경</button>
    </div>
    <input v-model="name" type="text" readonly>
    <input v-model="phone" type="text" readonly>

    <input v-model="email" type="email">
    <button v-if="hnpAccount" @click="sendEmail">메일 인증</button><br>

    <div v-show="isSentEmail" class="authEmail">
    <input v-model="enterCode" type="text" placeholder="인증번호 입력">
    <button @click="authCodeCheck">확인</button><br>
    <span v-show="!authCodeValid" style="color:red">인증코드를 재확인해주세요.</span>
    <span v-if="authCodeValid" style="color:blue">이메일 인증이 완료되었습니다.</span>
    </div>
   

    <div>
    <input v-model="pwd" type="password">
    <span v-if="!pwdValid" style="color:red">대문자, 특수문자 포함 8자리 이상으로 설정해주세요.</span>
    </div>

    <div>
    <input v-model="pwdCheck" type="password" placeholder="비밀번호 재입력" @input="samePwdCheck">
    <span v-if="!pwdCheckValid" style="color:#FF0000">비밀번호가 일치하지 않습니다.</span> 
    <span v-else-if="pwdCheckValid" style="color:blue">비밀번호가 일치합니다.</span>
    </div>

    <!-- @input으로 처리하면 검색을 못하고 @blur로 처리하니까 검색은 잘 되는데 필드를 벗어나야 검사를 하는듯..? 그리고 한 번 검사 끝나면 중복값 다시 넣어도 메서드 재실행 안됨.. -->
    <div>
    <input v-model="nickname" type="text" @blur="checkNickname"><br> 
    <span v-if="!nickNameRexegValid" style="color:#FF0000">띄어쓰기 없이 8자리 미만으로 설정해주세요.</span>
    <span v-if="!nicknameValid" style="color:#FF0000">중복된 닉네임입니다.</span>
    </div>

    <button @click="editInfo">수정완료</button>


              <div class="input-group">
                <span class="input-group-text" id="basic-addon1">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-apple" viewBox="0 0 16 16">
  <path d="M11.182.008C11.148-.03 9.923.023 8.857 1.18c-1.066 1.156-.902 2.482-.878 2.516.024.034 1.52.087 2.475-1.258.955-1.345.762-2.391.728-2.43Zm3.314 11.733c-.048-.096-2.325-1.234-2.113-3.422.212-2.189 1.675-2.789 1.698-2.854.023-.065-.597-.79-1.254-1.157a3.692 3.692 0 0 0-1.563-.434c-.108-.003-.483-.095-1.254.116-.508.139-1.653.589-1.968.607-.316.018-1.256-.522-2.267-.665-.647-.125-1.333.131-1.824.328-.49.196-1.422.754-2.074 2.237-.652 1.482-.311 3.83-.067 4.56.244.729.625 1.924 1.273 2.796.576.984 1.34 1.667 1.659 1.899.319.232 1.219.386 1.843.067.502-.308 1.408-.485 1.766-.472.357.013 1.061.154 1.782.539.571.197 1.111.115 1.652-.105.541-.221 1.324-1.059 2.238-2.758.347-.79.505-1.217.473-1.282Z"/>
  <path d="M11.182.008C11.148-.03 9.923.023 8.857 1.18c-1.066 1.156-.902 2.482-.878 2.516.024.034 1.52.087 2.475-1.258.955-1.345.762-2.391.728-2.43Zm3.314 11.733c-.048-.096-2.325-1.234-2.113-3.422.212-2.189 1.675-2.789 1.698-2.854.023-.065-.597-.79-1.254-1.157a3.692 3.692 0 0 0-1.563-.434c-.108-.003-.483-.095-1.254.116-.508.139-1.653.589-1.968.607-.316.018-1.256-.522-2.267-.665-.647-.125-1.333.131-1.824.328-.49.196-1.422.754-2.074 2.237-.652 1.482-.311 3.83-.067 4.56.244.729.625 1.924 1.273 2.796.576.984 1.34 1.667 1.659 1.899.319.232 1.219.386 1.843.067.502-.308 1.408-.485 1.766-.472.357.013 1.061.154 1.782.539.571.197 1.111.115 1.652-.105.541-.221 1.324-1.059 2.238-2.758.347-.79.505-1.217.473-1.282Z"/>
</svg>
                </span>
                <input type="text" class="form-control" placeholder="Input group example" aria-label="Input group example" aria-describedby="basic-addon1">
              </div>
   

            

</div>

    
<div class="Out">
    <button @click="userOut">회원탈퇴</button>
</div>

<router-link to="/MyBankInfo">계좌수정</router-link>

</template>

<script>
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-icons/font/bootstrap-icons.css'

export default {
    name: 'MyInfo',
    data() {
        return {
            hnpAccount: false,          // true(허니팟 계정)일 때만 이메일 수정 가능
            isSentEmail: false,         // 인증메일 보냈을 때만 인증번호 입력할 수 있는 영역 뜸 
            authCodeValid: false,       // 이메일 인증번호 유효성 체크
            pwdValid: false,            // 비밀번호 정규식 체크
            nickNameRexegValid: false,  // 닉네임 정규식 체크
            nickNameValid: false,       // 닉네임 중복 체크

            userNum: sessionStorage.getItem('userNum'),
            profile: '',
            name: '',
            phone: '',
            email: '',
            enterCode: '',              // 이메일 인증번호 입력값
            pwd: '',
            nickname: ''
        }
    },

    created() {
        this.getInfo();
    }, 

    methods: {
        getInfo() { // 기존 정보 가져오기
            const self = this;
            const userNum = self.userNum;

            if (userNum == null) {    // 미로그인 상태면 로그인 페이지로 이동
                alert('로그인되지 않은 상태입니다.\n로그인 페이지로 이동합니다.')
                window.location.href = "/LoginPage";
            } else {    // 로그인 상태면 기존 정보 가져옴
                self.$axios.get('http://localhost:8988/members/' + self.userNum)
                .then(function (res) {
                    if (res.status == 200) {
                        self.name = res.data.name;
                        self.phone = res.data.phone;
                        self.email = res.data.email;
                        self.pwd = res.data.pwd;
                        self.nickname = res.data.nickname;     
                        self.profile = res.data.profile;    
                        self.hnpAccount = res.data.hnpAccount;  // 허니팟 계정 여부 판단, 아닐 시 메일 수정 불가  

                        if (self.profile == null) {
                            self.profile = require('../../assets/images/BasicUserImg.png');
                        } else {
                            self.profile = res.data.profile;    // 이걸로 가져와도 되나..? 흠
                        }
                    } else {
                        alert('에러코드: ' + res.status)
                    }
                })
                .catch(function (error) {
                    console.error(error);
                })
            }
        }, 

        sendEmail() {   // 인증메일 보내기
            const self = this;

            const formdata = new FormData();
            formdata.append('email', self.email);
                
            console.log(this.email);

            self.$axios.post("http://localhost:8988/members/emailConfirm", formdata)
            .then(function (res) {
                if(res.status == 200) {
                    if (res.data.flag) {
                        alert("기존 메일과 동일합니다. 새로운 메일 계정을 입력해주세요.")
                        self.isSentEmail = false;
                    } else {
                        self.authCode = res.data.authCode;
                        alert("인증 메일이 전송되었습니다.");
                        console.log(self.authCode);
                        self.isSentEmail = true;
                    }
                } else {
                    alert('에러코드: ' + res.status);
                }
            })
            .catch(function (error) {
                console.error(error);
            });
        },


        // 인증번호 유효 여부 체크
        authCodeCheck() { 
            if (this.enterCode == '') {
                alert('인증번호를 입력해주세요');
            } else {
                if (this.enterCode == this.authCode) {
                    this.authCodeValid = true;
                } else {
                    this.authCodeValid = false;
                }
            }
        },

        
        // 마이페이지에서 빈 값, 잘못된 값 등 있는지 체크
        checkEmpty(value) {
            if (value == '' || value == null || value == undefined) {
                return false;
            } else {
                return true;
            }
        },


        editInfo() {
            const self = this;

            const formdata = new FormData();

            formdata.append('email', self.email);
            formdata.append('pwd', self.pwd);
            formdata.append('nickname', self.nickname);

            console.log(self.email + " / " + self.pwd + " / " + self.nickname);

            self.$axios.post("http://localhost:8988/members/edit/" + self.userNum, formdata)
            .then(function (res){
                if (res.status == 200) {
                    const dto = res.data.dto;
                    console.log(dto);
                    window.location.href = "/MyInfo";
                } else {
                    alert ('에러코드' + res.status)
                }
            })
            .catch(function (error) {
                console.error(error);
            });
        }, 

        userOut() {

            
            
        }
    }
}
</script>