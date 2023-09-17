
 <template>

    <div></div>
        
</template>



<script>
export default {
    name: 'NaverLogin',
    data() {
        return {
            duplicated1: false,         // 핸드폰 + 이름 중복, true일 경우 회원가입 불가
            duplicated2: false,         // 핸드폰 중복, true일 경우 회원가입 불가
            certified: false,           // 중복 값 없는 상태, true일 경우 회원가입 가능
            nickNameMsgBlank: true,
            nickNameRexegValid: false,  // 닉네임 정규식 체크
            nickNameValid: false,       // 닉네임 중복 체크
            
            code: this.$route.query.code,
            state: this.$route.query.state,
            name: self.name,            // 본인인증한 이름 정보
            phone: self.phone,          // 본인인증한 핸드폰 번호 정보
            nickname: '',               // 네이버 회원과 허니팟 회원 간 닉네임 중복될 수 있어 새로 입력하는 것으로 설정
            snsType: '',
            form: {                     // 네이버에서 가져온 정보
                email: 'self.form.email',
                navertoken: '' 
            }
        }
    },
    created() {
        this.code = this.$route.query.code;
        this.state = this.$route.query.state;
        
        console.log("code: " + this.code + " / " + "state: " + this.state);

        this.getToken(this.code, this.state);
    },

    methods: {
        getToken() {
            const self = this;

            self.$axios.post('http://localhost:8988/naver/login/' + self.code + '/' + self.state)
            .then(function (res) {
                if (res.status == 200) {
                    self.form.email = res.data.userinfo.email;
                    self.form.navertoken = res.data.access_token;

                    console.log("res.form.email: " + self.form.email);

                    self.$axios.get('http://localhost:8988/members/naverCheck', { params: { email: self.form.email } })
                    .then(function (res) {
                        if (res.status == 200) {
                            if (!res.data.flag) {    // 중복된 네이버 아이디 없음
                                console.log(res.data.flag);
                            } else if (res.data.flag2) {
                                console.log(res.data.flag2);
                                alert ("이미 동일한 네이버 메일로 가입된 이력이 존재합니다.\n고객센터로 문의해주세요.")
                                window.location.href = "/";
                            } else if (res.data.flag) {
                                sessionStorage.setItem('token', res.data.token);   
                                sessionStorage.setItem('loginId', res.data.dto.email);
                                sessionStorage.setItem('userNum', res.data.dto.userNum);
                                sessionStorage.setItem('snsType', res.data.dto.snsType);
                                window.location.href = "/";
                            }
                        }
                    });
                }
            });
        }
    }
}




</script>


<style scoped>

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
    font-size: 15px;
}

input::-webkit-input-placeholder {
    color: #BDBDBD;
}

.certificationNotice {
    position: absolute;
    top: 20%;
    left: 40%;
    font-family: 'AppleSDGothicNeoB';
}

.duplicateNotice {
    position: absolute;
    top: 40%;
    left: 40%;
    font-family: 'AppleSDGothicNeoB';
}


</style>
