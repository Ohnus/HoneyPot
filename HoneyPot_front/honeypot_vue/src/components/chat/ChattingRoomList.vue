<template>
    <div class="hello">


        <div>loginId : {{ id }}</div>
        <div>userNum : {{ userNum }}</div>
        <div>token : {{ token }}</div>
        <div>snsType : {{ snstype }}</div>
    </div>

    <div class="body">
        <div class="body-left">
            <div class="myprofile">
                <!-- <div class="box-profile"><img class="profile" :src="chat.isFromSender.profile" @error="replaceImg"></div> -->
                <div class="myprofile-id">{{ id }}</div>
            </div>

            <div class="chatroom">
                <div class="chatroom-list" v-for="chatroom, index in list" :key="chatroom.chatroomNum">
                    <div>{{ chatroom.boardNum }}</div>
                    <div>ott타입 : {{ otttype[index+1] }}</div>
                    <div class="chatheader" @click="enterchatroom(chatroom.boardNum)">{{ chatroom.subject }}</div>
                    <div class="chatroom-chatcheck">안읽은 채팅 수 :{{ chatroomchatcheck(chatroom.boardNum) }}</div>
                </div>
            </div>
        </div>

        <div class="body-center">
            <div class="chatting" ref="chatContainer">
                <div class="chats" v-for="chat in chats" :key="chat.chatNum">
                    <div v-if="userNum !== chat.isFromSender.userNum">
                        <div class="box-profile"><img class="profile" :src="chat.isFromSender.profile" @error="replaceImg">
                        </div>
                        <div class="chat-nickname">{{ chat.isFromSender.nickname }}</div>
                        <div class="chat-info">
                            <div class="chat-content">{{ chat.content }}</div>
                            <span class="chat-time">{{ formatTime(chat.time) }} </span>
                        </div>
                        <!-- <div class = "chatcheck">chatNum :{{ chatcheck(chat.chatNum) }}</div> -->
                    </div>
                    <div v-else>
                        <div class="box-profile-reverse"><img class="profile" :src="chat.isFromSender.profile"
                                @error="replaceImg">
                        </div>
                        <div class="chat-nickname-reverse">{{ chat.isFromSender.nickname }}</div>
                        <div class="chat-info-reverse">
                            <span class="chat-time-reverse">{{ formatTime(chat.time) }} </span>
                            <div class="chat-content-reverse">{{ chat.content }}</div>

                        </div>



                    </div>
                </div>

                <div class="chat" v-for="webchat in webchats" :key="webchat.chatNum">
                    <div v-if="userNum !== webchat.isFromSender.userNum">
                        <div class="box-profile"><img class="profile" :src="webchat.isFromSender.profile"
                                @error="replaceImg">
                        </div>
                        <div class="chat-nickname">{{ webchat.isFromSender.nickname }}</div>
                        <div class="chat-info">
                            <div class="chat-content">{{ webchat.content }}</div>
                            <div class="chat-time">{{ formatTime_webchat(webchat.time) }} </div>
                        </div>
                    </div>
                    <div v-else>
                        <div class="box-profile-reverse"><img class="profile" :src="webchat.isFromSender.profile"
                                @error="replaceImg">
                        </div>
                        <div class="chat-nickname-reverse">{{ webchat.isFromSender.nickname }}</div>
                        <div class="chat-info-reverse">
                            <span class="chat-time-reverse">{{ formatTime_webchat(webchat.time) }} </span>
                            <div class="chat-content-reverse">{{ webchat.content }}</div>
                        </div>
                    </div>


                </div>



                <div class="greetings" v-for="greeting in greetings" :key="greeting.index">
                    <div class="greetings">{{ greeting.id }} </div>
                </div>

            </div>
            <div class="send-chat">
                <textarea class="write-chat" v-model="content" cols="75" rows="3" @keyup.enter="send()"></textarea>
                <div><button class="write-chat-btn" @click="send()" :disabled=isDisableded>보내기</button></div>
            </div>
        </div>
        <div class="body-right">
            채팅방 참여 멤버 목록
            <div class="members" v-for="member in members" :key="member.index">
                <div class="member">{{ member.name }} / {{ member.userNum }}</div>
            </div>
        </div>
    </div>
</template>


<script>

import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import img from "@/assets/images/BasicUserImg.png"

export default {


    name: 'ChattingRoomList',
    data() {
        return {
            id: sessionStorage.getItem("loginId"),
            userNum: sessionStorage.getItem("userNum"),
            token: sessionStorage.getItem("token"),
            snstype: sessionStorage.getItem("snsType"),
            boardNum: '',
            members: [],//각 채팅방 별 참여 멤버목록

            count: '', // 각 채팅 별 안읽은 인원
            chatcheckResults: [], //각 채팅 별 안읽은 인원 리스트
            chatroomchatcheckResults: [], //각 채팅방 별 안읽은 채팅 개수
            otttype:[], // 각 채팅방 otttype 정보

            content: '', // 채팅 작성
            list: [], // db 채팅 내용
            chats: [], //웹소켓 채팅 내용
            webchats: [], // 구독 목록
            stompClient: null, // 소켓 연결
            dto: null, // 채팅 작성 객체
            subscription: null, // 구독

            greetings: [], // 채팅방 입장 시 

            isDisableded: true // 채팅보내기 버튼 활성화 여부
        }
    },

    watch: {
        content(newContent) {
            this.isDisableded = newContent.length === 0;

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
                    console.log("self.list" + self.list[0].boardNum);
                    for (var i in self.list) {
                        self.$axios
                            .get("http://localhost:8988/chatheader/otttype/" + self.list[i].boardNum)
                            .then(function (res) {
                                if (res.status == 200) {
                                    self.otttype[i] = res.data.otttype;
                                    console.log(i)
                                    console.log("self.otttype : "+ self.otttype[i])
                                    i++;
                                } else {
                                    alert("에러코드 : " + res.status);
                                }
                            });
                    }
                    
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


            self.greetings = [];

            // 채팅방 들어가면 안읽은 채팅 개수 0으로 업데이트
            if (self.chatroomchatcheckResults[boardNum] != 0) {
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
                if (result.type === 'enter') {
                    self.greetings.push(result);
                    console.log('응');


                } else {

                    // 받은 데이터를 json으로 파싱하고 리스트에 담기
                    //self.webchats.push(JSON.parse(res.body))
                    self.webchats.push(result);
                    console.log('아니')
                }




            });

            //채팅 입장 시 알리기
            const greeting = {
                type: 'enter',
                id: self.id + "님 입장",
                boardNum: self.boardNum
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
                        self.scrollToBottom();

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

            if (self.boardNum == 0) {
                alert("채팅방을 선택해 주세요")
                self.content = ''
                return
            }
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
        },
        replaceImg(e) {
            e.target.src = img;
        },

        // formatTime(time) {
        //     const date = new Date(time[0], time[1] - 1, time[2], time[3], time[4], time[5], time[6]);
        //     const year = date.getFullYear().toString().substr(-2); // Get the last 2 digits of the year
        //     const month = String(date.getMonth() + 1).padStart(2, '0'); // Month starts from 0
        //     const day = String(date.getDate()).padStart(2, '0');
        //     const hours = String(date.getHours()).padStart(2, '0');
        //     const minutes = String(date.getMinutes()).padStart(2, '0');
        //     return `${year}/${month}/${day} ${hours}:${minutes}`;
        // },
        formatTime(time) {

            const year = time[0].toString().substr(-2); // Get the last 2 digits of the year
            const month = String(time[1]).padStart(2, '0'); // Month starts from 0
            const day = String(time[2]).padStart(2, '0');
            const hours = String(time[3]).padStart(2, '0');
            const minutes = String(time[4]).padStart(2, '0');
            return `${year}/${month}/${day} ${hours}:${minutes}`;
        },
        formatTime_webchat(time) {
            const date = new Date(time);
            const year = date.getFullYear().toString().substr(-2);
            const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 두 자리로 만듭니다.
            const day = String(date.getDate()).padStart(2, '0');
            const hours = String(date.getHours()).padStart(2, '0');
            const minutes = String(date.getMinutes()).padStart(2, '0');

            return `${year}/${month}/${day} ${hours}:${minutes}`;
        },


        scrollToBottom() {
            this.$nextTick(() => {
                const chatContainer = this.$refs.chatContainer;
                chatContainer.scrollTop = chatContainer.scrollHeight;
            });
        },



    },


};
</script>

<style scoped>
.body {

    height: 620px;
    display: flex;
    border: 1px;
    float: center;
    padding-left: 50px;
    padding-right: 50px;
    padding-bottom: 25px;
    border-radius: 10px;
    justify-content: center;
}

/* Styles for the chatroom on the left */
.body-left {
    flex: 1;
    /* Occupy remaining space */
    background-color: #f0f0f0;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
    flex-direction: column;
}

.myprofile {
    border: 1px solid black;
}

.chatroom {
    border: 1px solid black;
    overflow: auto;
}

.chatroom-list {
    flex: 1;
    /* Occupy 1/3 of the chatroom's space */
    overflow-y: auto;
    /* Enable vertical scrolling if needed */
}



/* body-center */
.body-center {
    flex: 2;
    /* Occupy more space */
    background-color: #fff;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* Center items vertically */
    align-items: center;
}

/* 채팅 */
.chat {
    display: block;
    margin-bottom: 10px;

}

.chats {
    display: block;
    margin-bottom: 10px;

}

.chatting {
    width: 100%;
    overflow: auto;
    margin-bottom: 10px;
}

.box-profile {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 70%;
    overflow: hidden;
    float: left;
    align-items: center;

}

.box-profile-reverse {
    margin-right: 25px;
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 70%;
    overflow: hidden;
    float: right;
    align-items: center;

}

.profile {
    width: 100%;
    height: 100%;
    background-color: white;
    object-fit: cover;

}

.chat-nickname {
    display: flex;
    justify-content: left;
    padding-left: 10px;
    font-size: 13px;
    font-family: 'AppleSDGothicNeoB';
}

.chat-nickname-reverse {
    display: flex;
    justify-content: right;
    padding-right: 10px;
    font-size: 13px;
    font-family: 'AppleSDGothicNeoB';
}

.chat-info {
    display: flex;

}

.chat-info-reverse {
    display: flex;
    justify-content: right;


}

/* .chat-content {
    display: flex;
    justify-content: left;
    padding-left: 10px;
    font-size: 18px;
    font-family: 'AppleSDGothicNeoR';
} */

.chat-content {
    /* justify-content: left; */
    margin-left: 10px;
    font-size: 18px;
    font-family: 'AppleSDGothicNeoR';

    position: relative;
    display: inline-block;
    max-width: calc(100% - 140px);
    padding: 2px 6px 2px 6px;
    font-size: 15px;
    border-radius: 8px;
    background-color: #ddd;
    align-items: center;

}

.chat-content::before {
    position: absolute;
    display: block;
    content: "◀";
    top: 4px;
    left: -6px;
    font-size: 10px;
    color: #ddd;
}

.chat-content-reverse {
    display: inline;
    margin-right: 10px;
    font-size: 18px;
    font-family: 'AppleSDGothicNeoR';
    justify-content: right;
    /* float:right; */
    position: relative;
    display: inline;
    max-width: calc(400px);
    padding: 2px 6px 2px 6px;
    font-size: 15px;
    border-radius: 8px;
    background-color: #Fdd000;

}

.chat-content-reverse::before {
    position: absolute;
    display: block;
    content: "▶";
    top: 4px;
    right: -6px;
    font-size: 10px;
    color: #Fdd000;
}

.chat-time {
    padding-left: 10px;
    float: left;
    font-size: 10px;
    /* padding-top: 11px; */
    font-family: 'AppleSDGothicNeoR';
}

.chat-time-reverse {
    display: inline;
    padding-right: 10px;
    float: right;
    font-size: 10px;
    /* padding-top: 11px; */
    font-family: 'AppleSDGothicNeoR';
}

.send-chat {
    align-self: flex-end;
    /* Align at the bottom of the column */
    margin-top: auto;


    /* Push it to the bottom */
    border: 1.5px solid rgb(167, 167, 167);
    width: 100%;
    padding: 5px;
    border-radius: 10px;

}

.write-chat {
    width: 100%;
    outline: none;
    border: none;
    resize: none;
    font-family: 'AppleSDGothicNeoR';
}

/* 메세지 보내기 버튼 */
.write-chat-btn[disabled] {
    background-color: rgb(204, 204, 204);
    color: white;
    border-color: rgb(204, 204, 204);
}








/* body right */
.body-right {
    flex: 1;
    /* Occupy remaining space */
    background-color: #f0f0f0;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    padding: 20px;
    flex-direction: column;
}

/* Add more styles as needed */
</style>
