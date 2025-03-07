package com.beboilerplate.global.util;

import com.beboilerplate.domain.chat.dto.ChatDto;
import com.beboilerplate.domain.chat.service.ChatRedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final ChatRedisService chatRedisService;

    public ChatWebSocketHandler(ChatRedisService chatRedisService) {
        this.chatRedisService = chatRedisService;
    }

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결된 세션 저장
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatDto chatMessage = objectMapper.readValue(message.getPayload(), ChatDto.class);

        // 메시지 저장
        chatRedisService.saveMessage(chatMessage.getRoomId(), chatMessage);

        // 참여자에게 브로드캐스트
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션 제거
        sessions.remove(session.getId());
    }
}

