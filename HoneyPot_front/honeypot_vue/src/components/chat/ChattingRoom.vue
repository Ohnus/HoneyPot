<template>
    
    <div class="hello">
     <p>채팅룸 {{ boardNum }}</p>
  
      
  
      </div>
  </template>
  
  <script>
  
  
  export default {
      
        props:{
            message:Number
        },
      name: 'ChattingRoom',
      data() {
          return {
            boardNum : this.message
          }
      },
       
      created:function(){
          
      },
  
      
  
      methods: {
          
          
      }
    
};
  </script>
  