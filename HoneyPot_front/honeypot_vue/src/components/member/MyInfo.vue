<template>
<div class="myInfo">

    <!-- <img :src="profile" alt="프로필 이미지"> -->
    <input v-model="name" type="text">
    <input v-model="phone" type="text">
    <button @click="certification">변경하기</button><br>

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
</div>
</template>

<script>

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
            

            // profile: '',
            userNum: sessionStorage.getItem('userNum'),
            name: self.name,
            phone: self.phone,
            email: self.email,
            enterCode: '',              // 이메일 인증번호 입력값
            pwd: self.pwd,
            nickname: self.nickname
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
                self.$axios.get('http://localhost:8988/members/edit/' + self.userNum)
                .then(function (res) {
                    if (res.status == 200) {
                        console.log("name: " + res.data.name);
                        console.log("phone: " + res.data.phone);
                        console.log("email: " + res.data.email);
                        console.log("pwd: " + res.data.pwd);
                        console.log("nickname: " + res.data.nickname);
                        console.log("hnpAccount: " + res.data.hnpAccount);

                        self.name = res.data.name;
                        self.phone = res.data.phone;
                        self.email = res.data.email;
                        self.pwd = res.data.pwd;
                        self.nickname = res.data.nickname;     
                        self.hnpAccount = res.data.hnpAccount;  // 허니팟 계정 여부 판단, 아닐 시 메일 수정 불가  
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

            let formdata = new FormData();
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

        editInfo() {

        }

    }


}
</script>