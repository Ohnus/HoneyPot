<template>
    <div class="hello">


        <div>loginId : {{ id }}</div>
        <div>userNum : {{ userNum }}</div>
        <div>token : {{ token }}</div>
        <div>snsType : {{ snstype }}</div>


        <div class="chatroom" v-for="chatroom in list" :key="chatroom.chatroomNum">
            <div class="chatheader" @click="enterchatroom(chatroom.boardNum)">{{ chatroom.subject }}</div>
            <div class = "chatroom-chatcheck">boardNum :{{ chatroomchatcheck(chatroom.boardNum) }}</div>
        </div>
        <div>
            채팅방 참여 멤버 목록
            <div class="members" v-for="member in members" :key="member.index">
                <div class ="member">{{ member.name }} / {{ member.userNum }}</div>         
            </div>
        </div>
        <div class="chatting">
            <div class="chats" v-for="chat in chats" :key="chat.chatNum">
                <div class="chat">{{ chat.content }} / 보낸이 : {{ chat.isFromSender.name }} / 보낸 시간: {{ chat.time }} / </div>
                <!-- <div class = "chatcheck">chatNum :{{ chatcheck(chat.chatNum) }}</div> -->
            </div>

            <div class="chat" v-for="webchat in webchats" :key="webchat.chatNum">
                <div class="chat">{{ webchat.content }} / 보낸이 : {{ webchat.isFromSender.name }} / 보낸 시간: {{ webchat.time }}
                    /
                </div>
            </div>
            <div class="greetings" v-for="greeting in greetings" :key="greeting.index" >
                <div class="greetings">{{ greeting.id }} </div>
            </div>

            <div>
                <textarea class="send_chat" v-model="content" @keyup.enter="send()"></textarea>
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
            id: sessionStorage.getItem("loginId"),
            userNum: sessionStorage.getItem("userNum"),
            token:sessionStorage.getItem("token"),
            snstype:sessionStorage.getItem("snsType"),
            boardNum: '',
            members:[],//각 채팅방 별 참여 멤버목록

            count: '', // 각 채팅 별 안읽은 인원
            chatcheckResults: [], //각 채팅 별 안읽은 인원 리스트
            chatroomchatcheckResults:[], //각 채팅방 별 안읽은 채팅 개수

            content: '', // 채팅 작성
            list: [], // db 채팅 내용
            chats: [], //웹소켓 채팅 내용
            webchats: [], // 구독 목록
            stompClient: null, // 소켓 연결
            dto: null, // 채팅 작성 객체
            subscription: null, // 구독

            greetings:[] // 채팅방 입장 시 
        }
    },

    // 처음 시작 시 채팅 목록 보여주기
    created: function () {

        const self = this;
        console.log("실행")
        console.log(location.origin)

        // 채팅 목록
        self.$axios
            .get("http://localhost:8988/chatheader/" + self.userNum)
            .then(function (res) {
                if (res.status == 200) {
                    self.list = res.data.list;
                } else {
                    alert("에러코드 : " + res.status);
                }
            });




        // 소켓 연결    
        this.connect();
    },

    //채팅하기 나가면 소켓, 구독 해제
    unmounted: function () {
        const self = this;

        // 구독해제
        this.subscribeCancle();

        // 소켓 연결 해제
        self.stompClient.disconnect();
        self.stompClient = null;
        self.subscription = null;
        this.connected = false;

        console.log('구독 및 소켓 연결 해제');
        console.log('this.connected : ' + this.connected);
        console.log('self.webchats : ' + self.webchats);
        console.log('self.stompClient : ' + self.stompClient);
        console.log('self.subscription : ' + self.subscription);

    },

    methods: {

        // 채팅방 들어갈 때 채팅 내용 가져오기, 소켓 연결
        enterchatroom(boardNum) {
            const self = this;

            
            self.greetings =[];

            // 채팅방 들어가면 안읽은 채팅 개수 0으로 업데이트
            if(self.chatroomchatcheckResults[boardNum] != 0){
            self.chatroomchatcheckResults[boardNum] = 0;
            }
            
            

            // 기존 구독 해제
            this.subscribeCancle();

            self.boardNum = boardNum;

          

            //endpoint 구독
            self.subscription = self.stompClient.subscribe("/sub/channel/" + self.boardNum, res => {
                console.log('1) 구독으로 받은 메시지 입니다.', res.body);
                



                const result = JSON.parse(res.body);
                console.log(result.type);
                console.log(self.greetings);
                if(result.type === 'enter'){
                    self.greetings.push(result);
                    console.log('응');
                    
                    
                }else{
                   
                    // 받은 데이터를 json으로 파싱하고 리스트에 담기
                    //self.webchats.push(JSON.parse(res.body))
                    self.webchats.push(result);
                    console.log('아니')
                }
               

                

            });

              //채팅 입장 시 알리기
              const greeting={
                type:'enter',
                id :self.id+"님 입장",
                boardNum:self.boardNum
            }
            self.stompClient.send("/pub/greetings", JSON.stringify(greeting), {});

            console.log("2) boardNum : " + boardNum);
            console.log("3) userNum : " + self.userNum)


          
          

            // 채팅 내용 가져오기
            self.$axios
                .get("http://localhost:8988/chat/" + self.userNum + "/" + boardNum)
                .then(function (res) {
                    if (res.status == 200) {
                        self.chats = res.data.chat;
                    } else {
                        alert("에러코드 : " + res.status);
                    }
                });

                
            // 채팅방 참여 멤버목록 가져오기
            self.$axios
                .get("http://localhost:8988/chatheader/getmembers/" + self.boardNum)
                .then(function (res) {
                    if (res.status == 200) {
                        self.members = res.data.members;
                    } else {
                        alert("에러코드 : " + res.status);
                    }
                });


        },

        // 각 채팅방 별 안읽은 채팅 개수 확인
        chatroomchatcheck(boardNum) {
            const self = this;

            //채팅방 별 안 읽은 채팅 개수
            self.$axios
                .get("http://localhost:8988/chatcheck/countchatroom/" + boardNum + "/" + self.userNum)
                .then(function (res) {
                    if (res.status == 200) {
                        self.count = res.data.count;
                        self.chatroomchatcheckResults[boardNum] = res.data.count;
                    } else {
                        alert("에러코드 : " + res.status);
                    }
                });
                return self.chatroomchatcheckResults[boardNum] || 0;
        },

        // 각 채팅 별 안읽은 인원 확인
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


        // 채팅 작성
        send() {

            const self = this;
            const form = new FormData();

            form.append("content", self.content);
            form.append("isFromSender", self.userNum);
            form.append("boardNum", self.boardNum);

            console.log("content", self.content);
            console.log("isFromSender", self.userNum);
            console.log("boardNum", self.boardNum);

            self.$axios
                .post("http://localhost:8988/chat", form)
                .then(function (res) {

                    if (res.status == 200) {
                        if (res.data.flag) {
                            alert("채팅 작성 완료");
                            self.content = ''
                            self.dto = res.data.dto;

                            console.log("1) self.dto : " + self.dto);
                            console.log("2) self.dto : " + self.dto.chatNum);
                            console.log("3) " + self.dto);


                            if (self.stompClient && self.stompClient.connected) {

                                console.log("4) dto 다시 보내줘야함")
                                console.log('5) this.stompClient : ' + self.stompClient)
                                console.log('6) ' + self.dto);

                                self.stompClient.send("/pub/receive", JSON.stringify(self.dto), {});

                            }

                        } else {
                            alert("채팅보내기 실패")
                        }

                    } else {
                        alert("에러코드 : " + res.status);
                    }
                });

        },

        // 소켓 연결
        connect() {
            const self = this;

            let socket = new SockJS('http://localhost:8988/room');
            self.stompClient = Stomp.over(socket);
            console.log("1) 소켓 연결을 시도합니다.");


            self.stompClient.connect(
                {},
                frame => {
                    // 소켓 연결 성공
                    this.connected = true;
                    console.log('2) 소켓 연결 성공', frame);


                    //서버의 메시지 전송 endpoint를 구독합니다.
                    // self.stompClient.subscribe("/sub/channel/" + self.boardNum, res => {
                    //     console.log('구독으로 받은 메시지 입니다.', res.body);

                    //     // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
                    //     self.webchats.push(JSON.parse(res.body))

                    // });
                },
                error => {
                    // 소켓 연결 실패
                    console.log('2) 소켓 연결 실패', error);
                    this.connected = false;
                }

            );
        },

        //구독 해제
        subscribeCancle() {
            const self = this;

            // 구독 내역이 있으면 기존 구독 해제
            if (self.subscription) {
                self.subscription.unsubscribe();
                self.webchats = [];
                console.log('구독이 취소되었습니다.');

            }
        }





    },


};
</script>
