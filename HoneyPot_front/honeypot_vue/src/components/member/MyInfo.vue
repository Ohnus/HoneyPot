<template>

    <div>
    
    <div style="position: fixed; top:570px; left:800px; z-index: 2;">
        <button @click="editInfo" style="margin-right: 5px;">수정완료</button>  
        <button @click="userOut" style="margin-left: 5px;">회원탈퇴</button>
    </div>
    
    
    <div class="myInfo" style="z-index: 1;">
        <p class="menuTitle">내 정보 수정</p><hr>
        <div class="row justify-content-center" style="margin-top: 30px;">
        <div class="col-md-4">
        <div class="userImg">
        <img id="profile" :src="profile">
    
        <div style="margin-top:10px">
        <label for="file-input" class="custom-file-upload"><span>파일 선택</span></label>
        <input id="file-input" type="file" style="display: none;" @change="previewImage" accept="image/*">
        <button class="defaultImage" @click="setDefaultProfile">기본 이미지로 변경</button>
        </div>
        </div>
        </div>
    
        <div class="col-md-7">
        <div class="userInfo">
    
        <div class="row justify-content-center">   
        <div class="col-md-5">
        <div class="input-group">
        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person" viewBox="1 1 14 14">
        <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0Zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4Zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10Z"/>
        </svg>
        </span>
        <input v-model="name" type="text" class="form-control" readonly>
        </div>
        </div>
    
        <div class="col-md-5">
        <div class="input-group">
        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-telephone" viewBox="0 0 16 16">
        <path d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.568 17.568 0 0 0 4.168 6.608 17.569 17.569 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.678.678 0 0 0-.58-.122l-2.19.547a1.745 1.745 0 0 1-1.657-.459L5.482 8.062a1.745 1.745 0 0 1-.46-1.657l.548-2.19a.678.678 0 0 0-.122-.58L3.654 1.328zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"/>
        </svg>
        </span>
        <input v-model="phone" type="text" class="form-control" readonly>
    
        </div>
        <span v-if="blank" id="checkMsg" style="color:transparent;">'</span> 
        </div>
        </div>
    
        <div class="row justify-content-center">
        <div class="col-md-10">
        <div class="input-group">
        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
        <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V4Zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1H2Zm13 2.383-4.708 2.825L15 11.105V5.383Zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741ZM1 11.105l4.708-2.897L1 5.383v5.722Z"/>
        </svg>
        </span>
        <input v-model="email" type="email" class="form-control">
        <button v-if="hnpAccount" @click="sendEmail">메일 변경</button>
        </div>    
        <span v-if="blank" id="checkMsg" style="color:transparent;">'</span> 
        </div>
        </div>
    
        <div v-show="isSentEmail" class="authEmail">
        <div class="row justify-content-center">
        <div class="col-md-10">
        <div class="input-group">
        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check2-square" viewBox="0 0 16 16">
        <path d="M3 14.5A1.5 1.5 0 0 1 1.5 13V3A1.5 1.5 0 0 1 3 1.5h8a.5.5 0 0 1 0 1H3a.5.5 0 0 0-.5.5v10a.5.5 0 0 0 .5.5h10a.5.5 0 0 0 .5-.5V8a.5.5 0 0 1 1 0v5a1.5 1.5 0 0 1-1.5 1.5H3z"/>
        <path d="m8.354 10.354 7-7a.5.5 0 0 0-.708-.708L8 9.293 5.354 6.646a.5.5 0 1 0-.708.708l3 3a.5.5 0 0 0 .708 0z"/>
        </svg>
        </span>
        <input v-model="enterCode" type="text" class="form-control" placeholder="인증번호 입력">
        <button @click="authCodeCheck">확인</button>
        </div>
        </div>
        </div>
        <span v-if="authCodeMsgBlank" id="checkMsg" style="color:transparent;">'</span> 
        <span v-else-if="!authCodeValid" id="checkMsg" style="color:red;">인증코드를 재확인해주세요.</span>
        <span v-else-if="authCodeValid" id="checkMsg" style="color:blue;">이메일 인증이 완료되었습니다.</span>
        </div>
        
        <div class="row justify-content-center" v-if="hnpAccount">
        <div class="col-md-5">
        <div class="input-group">
        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock" viewBox="0 0 16 16">
        <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2zM5 8h6a1 1 0 0 1 1 1v5a1 1 0 0 1-1 1H5a1 1 0 0 1-1-1V9a1 1 0 0 1 1-1z"/>
        </svg>
        </span>
        <input v-model="pwd" type="password" class="form-control" placeholder="비밀번호 입력" @blur="checkPwd">
        </div>
        <span v-if="pwdMsgBlank" id="checkMsg" style="color:transparent">'</span> 
        <span v-else-if="!pwdMsgBlank" v-show="!pwdValid" id="checkMsg" style="color:red">대문자, 특수문자 포함 8자리 이상으로 설정</span>
        </div>
    
        <div class="col-md-5">
        <div class="input-group">
        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
        <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/>
        </svg>
        </span>
        <input v-model="pwdCheck" type="password" class="form-control" placeholder="비밀번호 재입력" @blur="samePwdCheck">
        </div>
        <span v-if="pwdMsgBlank" id="checkMsg" style="color:transparent">'</span> 
        <span v-else-if="!pwdCheckValid" id="checkMsg" style="color:#FF0000">비밀번호가 일치하지 않습니다.</span>
        <span v-else-if="pwdCheckValid" id="checkMsg" style="color:blue">비밀번호가 일치합니다.</span>
        </div>
        </div>
    
        <div class="row justify-content-center">
        <div class="col-md-10">
        <div class="input-group">
        <span class="input-group-text" id="basic-addon1" style="background-color: transparent">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-emoji-sunglasses" viewBox="0 0 16 16">
        <path d="M4.968 9.75a.5.5 0 1 0-.866.5A4.498 4.498 0 0 0 8 12.5a4.5 4.5 0 0 0 3.898-2.25.5.5 0 1 0-.866-.5A3.498 3.498 0 0 1 8 11.5a3.498 3.498 0 0 1-3.032-1.75zM7 5.116V5a1 1 0 0 0-1-1H3.28a1 1 0 0 0-.97 1.243l.311 1.242A2 2 0 0 0 4.561 8H5a2 2 0 0 0 1.994-1.839A2.99 2.99 0 0 1 8 6c.393 0 .74.064 1.006.161A2 2 0 0 0 11 8h.438a2 2 0 0 0 1.94-1.515l.311-1.242A1 1 0 0 0 12.72 4H10a1 1 0 0 0-1 1v.116A4.22 4.22 0 0 0 8 5c-.35 0-.69.04-1 .116z"/>
        <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-1 0A7 7 0 1 0 1 8a7 7 0 0 0 14 0z"/>
        </svg>
        </span>
        <input v-model="nickname" type="text" class="form-control" placeholder="닉네임 입력">
        <button @click="checkNickname">닉네임 변경</button>
        </div>
        </div>
        </div>
        <span v-if="nickNameMsgBlank" id="checkMsg" style="color:transparent">'</span> 
        <span v-else-if="!nickNameRexegValid" id="checkMsg" style="color:#FF0000">띄어쓰기 없이 8자리 미만으로 설정</span>
        <span v-if="nickNameMsgBlank" id="checkMsg" style="color:transparent">'</span> 
        <span v-else-if="!nicknameValid" id="checkMsg" style="color:#FF0000">중복된 닉네임입니다.</span>
        <span v-else-if="nicknameValid" id="checkMsg" style="color:blue">닉네임 설정이 완료되었습니다.</span>
        </div>
        </div>
        </div>    
    </div>
    
    </div>
    
    
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
                blank: true,
                authCodeMsgBlank: true,
                pwdMsgBlank: true,
                nickNameMsgBlank: true,

                userNum: sessionStorage.getItem('userNum'),
                profile: '',
                name: '',
                phone: '',
                email: '',
                enterCode: '',              // 이메일 인증번호 입력값
                pwd: '',
                nickname: '',
                cash: ''
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
                            self.profile = self.profileImg();
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

            profileImg() {
                if (this.profile != null) {
                    return 'http://localhost:8988/members/imgs/' + this.userNum;
                } else {
                    return require('@/assets/images/BasicUserImg.png');
                }
            },

            previewImage(event) {
                const file = event.target.files[0];
                
                if (file) {
                    const reader = new FileReader();
                    reader.onload = () => {
                    this.profile = reader.result;
                    };
                reader.readAsDataURL(file);
                }
            },

            setDefaultProfile() {
                this.profile = require('@/assets/images/BasicUserImg.png');
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
            authCodeCheck() { // 
                if (this.enterCode == '') {
                    alert('인증번호를 입력해주세요');
                } else {
                    if (this.enterCode == this.authCode) {
                        this.authCodeValid = true;
                        this.authCodeMsgBlank = false;
                    } else {
                        this.authCodeValid = false;
                        this.authCodeMsgBlank = false;
                    }
                }
            },
    
    
            // 비밀번호 정규식
            checkPwd() {
                const pwdRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.{8,})/;   // 8자리 + 대문자 및 특수문자 포함
                this.pwdValid = pwdRegex.test(this.pwd);
            },
    
    
            // 비밀번호 일치 여부 체크
            samePwdCheck() {
                if (this.pwd == this.pwdCheck) {
                    this.pwdCheckValid = true;
                    this.pwdMsgBlank = false;
                } else {
                    this.pwdCheckValid = false;
                    this.pwdMsgBlank = false;
                }
            },
    
    
            // 닉네임 유효성 체크: 정규식 + 중복 체크
            checkNickname() {
                const self = this;
    
                // 닉네임 띄어쓰기, 8자 이하
                const nickNameRexeg = /^(?!.*\s).{1,8}$/;
                this.nickNameRexegValid = nickNameRexeg.test(this.nickname);
    
                // 닉네임 중복 체크
                if (this.nickNameRexegValid) {
                    console.log(this.nickname);
    
                    const data = {
                        nickname: self.nickname
                    };
    
                    self.$axios.get("http://localhost:8988/members/nicknameConfirm", { params: data } )
                    .then(function (res) {
                        if (res.status == 200) {
                            if (res.data.flag) {
                                self.nicknameValid = true;
                                self.nickNameMsgBlank = false;
                            } else {
                                self.nicknameValid = false;
                                self.nickNameMsgBlank = false;
                            }
                        } else {
                            alert('에러코드' + res.status);
                        }
                    })
                    .catch(function (error) {
                        console.error(error);
                    });
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
    
                if (document.getElementById('file-input').value !== '') {
                    const file = document.getElementById('file-input').files[0]
    
                    formdata.append('f', file)
                }
    
                self.$axios.post("http://localhost:8988/members/edit/" + self.userNum, formdata)
                .then(function (res){
                    if (res.status == 200) {
                        const dto = res.data.dto;
                        console.log(dto);
                        window.location.href = "/MyInfoSidebar";
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
    
    <style scope>
    .menuTitle {
        font-family: 'AppleSDGothicNeoB';
        font-size: 20px;
        text-align: left;
    }
    
    #profile {
        width: 200px;
        height: 200px;
        border-radius: 50%;
        
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
    
        font-weight: 500; /*폰트 굵기 -> 글씨체 적용하면 좀 바뀔 것 같은데 굵은게 예쁠것 같음 */
        font-size: 14px; /* 지금 버튼 사이즈에는 이게 딱임 */
        font-family: 'AppleSDGothicNeoB';
    
    }
    
    button:hover {
        background-color: white;
        color: #444444;
        font-family: 'AppleSDGothicNeoB';
    }
     
    #checkMsg {
        font-size: 11px;
        font-family: 'AppleSDGothicNeoR';
    }
    
    .form-control {
        font-family: 'AppleSDGothicNeoR';
        font-size: 14px;
    }
    
    input::-webkit-input-placeholder {
        color: #BDBDBD;
    }
    
    .custom-file-upload {
        display: inline-block;
        padding: 10px 20px;
        color: #bdbdbd;
        cursor: pointer;
        font-size: 13px;
        font-family: 'AppleSDGothicNeoR';
    }
    
    .defaultImage {
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
    
        transition-duration: 0.4s;
        cursor: pointer;
        background-color: transparent;
        color: #bdbdbd;
        border: 0px solid transparent;
        border-radius: 0px;
    
        font-size: 13px; 
        font-family: 'AppleSDGothicNeoR';
    }
    
    .defaultImage:hover {
        background-color: transparent;
        color: #bdbdbd;
        font-family: 'AppleSDGothicNeoR';
    }
    
    
    
    </style>