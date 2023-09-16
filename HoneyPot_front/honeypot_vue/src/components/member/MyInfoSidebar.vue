<template>

<div class="myPage">

<div class="cashState">
<p class="cashTitle">허니팟 캐시 현황</p>
<div style="margin-top:20px;">
<img src="../../assets/images/coin.png" style="width:35px; height:30px">
<span id="cash">{{ cash }}원</span>
</div>
<p style="margin-top:25px;"><router-link to=""><button style="width:135px;">내역보기</button></router-link></p>
</div>

<div class="sideMenu">
<div style="margin-left: -45px;">
<button @click="activeComp = 'my-info'" style="font-size:17px; font-family: AppleSDGothicNeoB;">내 정보 수정</button><br>
</div>

<p class="ul">파티 관리</p>
<div style="margin-left: -48px;">
<button @click="activeComp='my-partylist'">파티 참여 현황</button>
<button @click="activeComp='my-automatcinglist'">파티 매칭 현황</button>
</div>

<p class="ul">정산 수단 관리</p>
<div style="margin-left: -48px;">
<button @click="activeComp='my-bankinfo'">인출 계좌 관리</button>
<button @click="activeComp=''">결제 수단 관리</button>
</div>
</div>

<div class="component">
    <component :is="activeComp"></component>
</div>

</div>

</template>

<script>
export default {
    data() {
        return {
          activeComp: 'my-info',
          cash: ''
        }
    },

}

</script>

<style scope>
.myPage {
    padding: 100px;
    position: relative;
}

.cashState {
    border: 1px solid #e8e4e4;
    border-radius: 20px;
    padding: 30px;
    width: 250px;
    height: 200px;
    position: relative;
    left: 20px;
    top: -20px;

}

.sideMenu {
    border: 1px solid #e8e4e4;
    border-radius: 20px;
    padding: 30px;
    width: 250px;
    height: 260px;
    position: absolute;
    top: 300px;
    left: 120px;

}
.component {
    border: 1px solid #e8e4e4;
    border-radius: 20px;
    padding: 30px;
    width: 1000px;
    height: 480px;
    position: absolute;
    top: 80px;
    left: 390px;

}

.cashTitle {
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;
}

.menuTitle {
    font-family: 'AppleSDGothicNeoB';
    font-size: 20px;
    text-align: left;
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

.ul {
    font-family: 'AppleSDGothicNeoB';
    font-size: 17px;
    text-align: left;
    margin-left: 30px;
    margin-top: 10px;
    margin-bottom: 0px;
}

.sideMenu button {
    padding: 1px 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;

    transition-duration: 0.4s;
    cursor: pointer;
    background-color: transparent;
    color: #444444;
    border: 0px solid transparent;
    border-radius: 0px;

    font-weight: 500; /*폰트 굵기 -> 글씨체 적용하면 좀 바뀔 것 같은데 굵은게 예쁠것 같음 */
    font-size: 14px; /* 지금 버튼 사이즈에는 이게 딱임 */
    font-family: 'AppleSDGothicNeoR';
}




</style>