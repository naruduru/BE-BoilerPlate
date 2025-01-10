package com.beboilerplate.domain.chat;

import com.beboilerplate.domain.chat.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat")
    public ChatDto sendMessage(ChatDto chatDto) {
        chatDto.setSendAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return chatDto;
    }
}
