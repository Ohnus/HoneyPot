<template>
    <div class="test2">
        파티 생성/찾기(결제 수단 등록x => 빌링키 발급 및 저장 + 100원 결제/환불)<br>
        카드번호:
        <input v-model="cardNum1" type="text" size="5">-<input v-model="cardNum2" type="text" size="5">-<input v-model="cardNum3" type="text" size="5">-<input v-model="cardNum4" type="text" size="5"><br>
        카드 유효기간:
        <input v-model="cardExpiry1" type="text" size="5">-<input v-model="cardExpiry2" type="text" size="3"><br>
        생년월일:
        <input v-model="birth" type="text" size="6"><br>
        카드 비밀번호 앞 두자리:
        <input v-model="pwdDigit2" type="text" size="2"><br>
        <button v-on:click="registerCard">카드등록</button>
    </div>
</template>


<script>
export default {
    name: 'PartyTest1',
    data() {
        return {

        }
    },
    created: function() {

    },
    methods: {

    }
}
</script>


<style scoped>

</style>