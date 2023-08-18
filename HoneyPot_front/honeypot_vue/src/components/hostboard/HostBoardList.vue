<template lang="">
    <div>
       
        <!-- <div >
                <div class="col">
                    글 번호
                </div>
                <div class="col">
                    사이트
                </div>
                <div class="col">
                    작성자
                </div>
                <div class="col">
                    구독시작일 
                </div>
                <div class="col">
                    구독기간
                </div>
        </div> -->
                


            <div v-if="list != null && list.length > 0">
                <div v-for="order in list" :key="order.boardNum">
                    <div v-if="order.ing === 0" @click="detail(order.boardNum)" >
                        <div class="col">
                          글번호 :   {{ order.boardNum }}
                        </div>
                        <div class="col">
                           오티티 :  {{ order.type.type }}
                        </div>
                        <div class="col">
                            아이디 : {{ order.userNum.userNum }} 
                        </div>
                        <div class="col-3">
                           구독 시작날짜 : {{ order.subStart }}
                        </div>
                        <div class="col">
                            구독 개월 수 {{ order.month}}
                        </div>

                    </div>
                    </div>
                </div>


    </div>
</template>
<script>
export default {
    name: 'HostBoardList',
    data() {
        return {
            list : [],
            boardNum : 0,
            userNum : sessionStorage.getItem('loginId'),
            type : null,
            subStart : null,
            month : null
         }
    },
    created: function () { 
        const self = this;
        self.$axios.get('http://localhost:8988/hostboard') 
            .then(function (res) {
                if (res.status == 200) {
                    self.list = res.data.list
                    console.log(self.list)
                }
            })
                  

    },
    methods :{
        detail(boardNum){
            this.$router.push({ name: 'HostBoardDetail', query: { boardNum: boardNum } })

        }
    }
}
</script>
