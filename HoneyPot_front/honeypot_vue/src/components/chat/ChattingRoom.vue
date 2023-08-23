<template>
    
    <div class="hello">
     
  
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
  
  
  export default {
      
  
      name: 'ChattingRoom',
      data() {
          return {
          }
      },
       
      created:function(){
          
      },
  
      
  
      methods: {
          
          
      }
    
};
  </script>
  