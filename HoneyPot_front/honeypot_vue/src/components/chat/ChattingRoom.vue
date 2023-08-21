<template>
    
  <div class="hello">
    <div>loginId : {{id}}</div>
    <div>userNum : {{userNum}}</div>

    <div class="chatroom" v-for="chatroom in list" :key="chatroom.chatroomNum">
        <div class="chatheader" @click="chatting(chatroom.boardNum)">{{ chatroom.subject }}</div>
    </div>

    <div class="chatting">
        <div class="chats" v-for="chat in chat" :key="chat.chatNum">
            <div class="chat">{{ chat.content }}</div> 
        </div>
        <div>
            <textarea class = "send_chat" v-model="content" @keyup.enter="send()"></textarea>
            <button @click="send()">보내기</button>
        </div>
    </div>

    </div>
</template>

<script>
export default {
    name: 'ChattingRoom',
    data() {
        return {
           id : sessionStorage.getItem("loginId"),
           userNum : sessionStorage.getItem("userNum"),
           boardNum : '',
           content :'',
           list:[],
            chat:[],
        }
    },
     
    created: function(){
        const self = this;
        console.log("실행")
        console.log(location.origin)
        self.$axios
        .get("http://localhost:8988/chatheader/" + self.userNum)
        .then(function (res){
            
            if(res.status == 200){
                
                self.list = res.data.list;
            }else{
                alert("에러코드 : " + res.status);
            }
        });
    },
    methods: {
        chatting(boardNum){
            const self = this;
            self.boardNum = boardNum;
        self.$axios
        .get("http://localhost:8988/chat/" + self.userNum + "/" + boardNum)
        .then(function (res){
            
            if(res.status == 200){
                
                self.chat = res.data.chat;
            }else{
                alert("에러코드 : " + res.status);
            }
        });
        },
        send(){
            alert("보내기")
            const self = this;
            const form = new FormData();

            form.append("content",self.content);
            form.append("isFromSender",self.userNum);
            form.append("boardNum",self.boardNum);

            console.log("content", self.content);
            console.log("isFromSender", self.userNum);
            console.log("boardNum", self.boardNum);

        self.$axios
        .post("http://localhost:8988/chat",  form)
        .then(function (res){
            
            if(res.status == 200){
                if(res.data.flag){
                    alert("채팅 작성 완료");
                    self.content=''
                }else{
                    alert("쪽지보내기 실패")
                }
                
            }else{
                alert("에러코드 : " + res.status);
            }
        });
        }

    },
    

};
</script>
