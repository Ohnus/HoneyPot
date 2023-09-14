<template lang="">
    <div>

        내 가입 파티 목록 
        <div v-if="list != null && list.length > 0">
                <div v-for="order in list" :key="order.groupNum">
                    <div @click="detail(order.boardNum.boardNum)" >
                        <div class="col">
                          오티티 :   {{ order.boardNum.type.type }}
                        </div>
                        <div class="col">
                            파티장 이름 : {{ order.boardNum.userNum.userNum }} 
                        </div>
                        <div class="col-3">
                           구독 시작날짜 : {{ order.boardNum.subStart }}
                        </div>
                        <div class="col">
                            구독 개월 수 {{ order.boardNum.month}}
                        </div>
                    </div>
                </div>
            </div>
    </div>
</template>
<script>
export default {
    name : 'MyPartyList',
    data() {
        return {
            list : [],
            userNum: sessionStorage.getItem("userNum"),
 


        }

    },
    created : function(){
        
        const self = this;        
        self.userNum= sessionStorage.getItem("userNum"),
        self.$axios.get('http://localhost:8988/partygroup/' + self.userNum)
            .then(function (res) {
                if (res.status == 200) {
                    console.log(res.data.list)
                    self.list = res.data.list
                }
            })

    },
    methods: {
        detail(boardNum){
            this.$router.push({ name: 'HostBoardDetail', query: { boardNum: boardNum } })

        }
    },
    
}
</script>
<style lang="">
    
</style>