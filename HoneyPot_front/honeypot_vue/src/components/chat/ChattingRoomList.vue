<template>
  
  <div class="hello" >
   

    <div>loginId : {{id}}</div>
    <div>userNum : {{userNum}}</div>

    <div class="chatroom" v-for="chatroom in list" :key="chatroom.chatroomNum">
        <div class="chatheader" @click="chatting(chatroom.boardNum)" >{{ chatroom.subject }}</div>
    </div>

    <div class="chatting">
        <div class="chats" v-for="chat in chats" :key="chat.chatNum"  >
            <div class="chat">{{ chat.content }} / 보낸이 : {{ chat.isFromSender.name}} / 보낸 시간: {{ chat.time }} / </div> 
            <!-- <div class = "chatcheck">chatNum :{{ chatcheck(chat.chatNum) }}</div> -->
        </div>
        <div>
            <textarea class = "send_chat" v-model="content" @keyup.enter="send()"></textarea>
            <button @click="send()">보내기</button>
        </div>


    </div>

    </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
    

    name: 'ChattingRoomList',
    data() {
        return {
           id : sessionStorage.getItem("loginId"),
           userNum : sessionStorage.getItem("userNum"),
           boardNum : '',
         count:'',
         content:'',
           list:[],
            chats:[],
            chatcheckResults: {},
            connected :false
        }
    },
     
    created:function(){

    


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

            // 채팅방을 클릭하면 소켓 연결.
                // 소켓 연결.
                this.connect();


            const self = this;
            self.boardNum = boardNum;
            console.log("boardNum : " + boardNum);
            console.log("userNum : "+ self.userNum)
        self.$axios
        .get("http://localhost:8988/chat/" + self.userNum + "/" + boardNum)
        .then(function (res){
            
            if(res.status == 200){
                
                self.chats = res.data.chat;
                //self.connectWS();
            
            }else{
                alert("에러코드 : " + res.status);
            }
        });
        },
       
        // chatcheck(chatNum){
        //     const self = this;

        // self.$axios
        // .get("http://localhost:8988/chatcheck/countchat/" + chatNum)
        // .then(function (res){
            
        //     if(res.status == 200){
                
        //         //self.count = res.data.count;
        //         self.chatcheckResults[chatNum] = res.data.count;
        //     }else{
        //         alert("에러코드 : " + res.status);
        //     }
        // });
        // return self.chatcheckResults[chatNum] || 0; 
        // },

        send(){
            
            
            alert("보내기")

            if(this.content === ''){
                alert('내용을 작성해주세요')
                return
            }

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

                    if (this.stompClient && this.stompClient.connected) {
                    
                        console.log("dto 다시 보내줘야함")
                }
                    




                }else{
                    alert("쪽지보내기 실패")
                }
                
            }else{
                alert("에러코드 : " + res.status);
            }
        });
        },

        connect(){

            // const serverURL = "http://localhost:8988"
            let socket = new SockJS('http://localhost:8988/room');
            this.stompClient = Stomp.over(socket);
            console.log("소켓 연결을 시도합니다. 서버 주소: ");

            this.stompClient.connect(
                {},
                frame => {
                // 소켓 연결 성공
                this.connected = true;
                console.log('소켓 연결 성공', frame);
                // 서버의 메시지 전송 endpoint를 구독합니다.
                // 이런형태를 pub sub 구조라고 합니다.
                // this.stompClient.subscribe("/send", res => {
                //     console.log('구독으로 받은 메시지 입니다.', res.body);

                //     // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
                //     this.recvList.push(JSON.parse(res.body))
                // });
                },
                error => {
                // 소켓 연결 실패
                console.log('소켓 연결 실패', error);
                this.connected = false;
                }

            );
        }

        


       },
    

};
</script>
