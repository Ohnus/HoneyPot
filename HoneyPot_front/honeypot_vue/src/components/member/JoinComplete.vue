<template>

<div class="joinComplete">
<br>
<br>
<img src="../../assets/images/join1.jpg" style="width:80px; height:80px">
회원가입이 완료되었습니다.
<img src="../../assets/images/join2.jpg" style="width:80px; height:80px">
<br>허니팟과 함께 마음에 쏙 드는 파티를 찾아보세요.
<br>
<br>

<router-link to="/"><button>허니팟 이용하러 가기</button></router-link>
</div>

</template>

<style scoped>
.joinComplete {
    margin-top: 100px;
    font-family: AppleSDGothicNeoB;
    font-size: 20px;
    text-align: center;
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

</style>