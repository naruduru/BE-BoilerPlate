package com.beboilerplate.domain.chat.controller;

import com.beboilerplate.domain.chat.dto.ChatDto;
import com.beboilerplate.domain.chat.service.ChatRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatRedisService chatRedisService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat")
    public ChatDto sendMessage(ChatDto chatDto) {
        chatDto.setSendAt(LocalDateTime.now());

        // Redis에 메시지 저장
        chatRedisService.saveMessage(chatDto.getRoomId(), chatDto);

        return chatDto;
    }
}
