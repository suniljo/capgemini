package com.capgemini.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tools")
public class TimeRestController {
   private final ChatClient chatClient;

   public TimeRestController(ChatClient chatClient) {
	        this.chatClient = chatClient;
   }

   @GetMapping("/local-time")
   public ResponseEntity<String> localTime(@RequestHeader("username") String username,
           @RequestParam("message") String message) {
       String answer = chatClient.prompt()
               					 .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, username))
               					 .user(message)
               					 .call()
               					 .content();
       
       return ResponseEntity.ok(answer);
   }   
}
