<template>

    <div v-if="duplicated1" class="duplicateNotice1">
        <div>본인 인증된 정보로 이미 가입된 이력이 존재합니다.</div>
        <router-link to="/메인페이지링크"><button>메인으로</button></router-link>
    </div>

    <div v-else-if="duplicated2" class="duplicateNotice2">
        <div>해당 핸드폰번호로 가입된 이력이 존재합니다. 고객센터에 문의해주세요.</div>
        <router-link to="/메인페이지링크"><button>메인으로</button></router-link>
    </div>

    <div v-else-if="certified" class="HoneypotJoinForm">
        <input v-model="name" type="text" readonly><br>

        <input v-model="phone" type="text" readonly><br>

        <input v-model="email" type="email" placeholder="이메일 입력(email@email.com)">
        <button @click="sendEmail">메일 인증</button><br>
        <span v-if="!emailCheck" style="color:red">올바른 형태의 이메일을 입력해주세요</span>
     
        <input v-model="enterCode" type="text" placeholder="인증번호 입력">
        <button @click="authCodeCheck">확인</button><br>
        <span v-if="!authCodeValid" style="color:red">인증코드를 재확인해주세요.</span>
        <span v-else-if="authCodeValid" style="color:blue">이메일 인증이 완료되었습니다.</span>
   
        <div>
        <input v-model="pwd" type="password" placeholder="비밀번호 입력" @input="checkPwd">
        <span v-if="!pwdValid" style="color:red">대문자, 특수문자 포함 8자리 이상으로 설정해주세요.</span>
        </div>

        <!-- 비번 일치여부 담을 수 있는 변수 추가 필요 -->
        <div>
        <input v-model="pwdCheck" type="password" placeholder="비밀번호 재입력">
        <span v-if="pwd !== pwdCheck" style="color:#FF0000">비밀번호가 불일치합니다.</span> 
        </div>

        <div>
        <input v-model="nickname" type="text" placeholder="닉네임 입력" @input="checkNickname"><br>
        <span v-if="!nicknameValid" style="color:#FF0000">중복된 닉네임입니다.</span>
        </div>

        <div> 약관 추가 예정.. ㅎ </div>

        <button @click="join">가입</button>
    </div>

    <div v-else class="certificationNotice">
        <div>허니팟은 100% 인증을 통해 믿을 수 있는 구독 환경을 제공합니다.</div>
        <div>실명 인증을 진행해주세요.</div>
        <button @click="certification">확인</button>
    </div>


</template>


<script>
export default {
    name: 'HoneypotJoin',
    data() {
        return {
            duplicated1: false,     // 핸드폰 + 이름 중복, true일 경우 회원가입 불가
            duplicated2: false,     // 핸드폰 중복, true일 경우 회원가입 불가
            certified: false,       // 중복 값 없는 상태, true일 경우 회원가입 가능
            emailCheck: false,      // 이메일 유효성 체크 
            authCodeValid: false,   // 이메일 인증번호 유효성 체크 
            pwdValid: false,        // 비밀번호 정규식 체크

            name: self.name,    // 본인인증한 이름 정보
            phone: self.phone,  // 본인인증한 핸드폰 번호 정보
            email: '',
            enterCode: '',      // 이메일 인증번호 입력값
            pwd: '',
            pwdCheck: '',       // 비밀번호 재입력
            nickname: '',
            snsType: ''
        }
    },

    methods: {

        // 본인 인증
        certification() {
        const self = this;

        const IMP = window.IMP;

        IMP.init("imp24063873");

        IMP.certification({
            pg: 'MIIiasTest',
            merchant_uid: 'merchant_' + new Date().getTime(),
            m_redirect_url: "http://localhost:8081/member/HoneypotJoin", 
            popup: false
            }, function (rsp) {
                if (rsp.success) {
                    console.log(rsp.imp_uid);
                    console.log(rsp.merchant_uid);

                const data = {
                    imp_uid: rsp.imp_uid,
                };

                self.$axios.get("http://localhost:8988/members/certifications/redirect", { params: data })
                    .then(function (res) {
                        if (res.status == 200) {
                            console.log("name: " + res.data.name);
                            console.log("phone: " + res.data.phone);
                            console.log("회원가입 진행해도 되니: " + res.data.certified);
                            console.log("핸드폰번호 + 이름 중복이니: " + res.data.duplicated1);
                            console.log("핸드폰번호 중복이니: " + res.data.duplicated2);

                            self.name = res.data.name;
                            self.phone = res.data.phone;

                            if (res.data.certified) {   // 이 경우에만 회원가입 진행됨
                                self.certified = true;
                            } else if (res.data.duplicated1) {  // 핸드폰 + 이름 중복
                                self.duplicated1 = true;
                            } else if (res.data.duplicated2) {  // 핸드폰 중복
                                self.duplicated2 = true;
                            }

                        } else {
                            alert('에러코드: ' + res.status)
                        }
                    }) 
                    .catch(function (error) {
                        console.error(error);
                    });

                }
            });
        },
      
        sendEmail() {
            const self = this;

            if (this.email == '') {
                alert('이메일 입력 필요');
            } else {
                const formdata = new FormData();
                formdata.append('email', self.email);
                
                console.log(this.email);

                self.$axios.post("http://localhost:8988/members/emailConfirm", formdata)
                .then(function (res) {
                    if(res.status == 200) {
                        self.authCode = res.data.authCode;
                        alert("메일 전송 완료");
                        console.log(self.authCode);
                    } else {
                        alert('에러코드: ' + res.status);
                    }
                })
                .catch(function (error) {
                    console.error(error);
                });
            }
        },

        authCodeCheck() { // 
            if (this.enterCode === '') {
                alert('인증번호를 입력해주세요');
            } else {
                if (this.enterCode === this.authCode) {
                    alert('인증이 완료되었습니다');
                    this.authCodeValid = true;
                } else {
                    alert('인증번호가 일치하지 않습니다.');
                    this.authCodeValid = false;
                }
            }
        },

        // 비밀번호 정규식
        checkPwd() {
            const pwdRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{8,})/;   // 8자리 + 대문자 및 특수문자 포함
            this.pwdValid = pwdRegex.test(this.pwd);
        },


        // 회원가입 페이지에서 빈 값, 잘못된 값 등 있는지 체크
        checkEmpty(value) {
            if (value == '' || value == null || value == undefined) {
                return false;
            } else {
                return true;
            }
        },


        // 회원가입
        join() {
            const self = this;

            // 값이 모두 입력되어있고, 유효성 검사를 모두 통과한 경우(추가 예정)만 회원가입 진행

            if (this.checkEmpty(self.email) && this.checkEmpty(self.pwd) && this.checkEmpty(self.pwdCheck) && this.checkEmpty(self.nickname)
                && self.pwdValid && self.pwdCheck) {
                const formdata = new FormData();

                formdata.append('name', self.name);
                formdata.append('phone', self.phone);
                formdata.append('email', self.email);
                formdata.append('pwd', self.pwd);
                formdata.append('nickname', self.nickname);
                formdata.append('snsType', 0);

                self.$axios.post("http://localhost:8988/members/join", formdata)
                .then(function (res) {
                    if(res.status == 200) {
                        const dto = res.data.dto;
                        console.log(dto);
                        window.location.href = "/JoinComplete";
                    } else {
                        alert ('에러코드' + res.status)
                    }
                });
            } else {
                alert('입력되지 않은 값 혹은 유효성을 체크하지 않은 값이 존재합니다. 다시 확인해주세요.')
            }
        }
    }
};


</script>
