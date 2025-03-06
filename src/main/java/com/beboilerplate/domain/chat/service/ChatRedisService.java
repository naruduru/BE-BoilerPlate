package com.beboilerplate.domain.chat.service;

import com.beboilerplate.domain.chat.ChatMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChatRedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ChatRedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 메시지 저장
    public void saveMessage(String roomId, ChatMessage message) {
        redisTemplate.opsForList().leftPush("chat:" + roomId + ":messages", message);
    }

    // 메시지 조회
    public List<ChatMessage> getMessages(String roomId, int limit) {
        List<Object> messages = redisTemplate.opsForList().range("chat:" + roomId + ":messages", 0, limit - 1);
        return messages.stream().map(msg -> (ChatMessage) msg).toList();
    }

    // 읽음 여부 저장
    public void markAsRead(String roomId, String userId, String messageId) {
        redisTemplate.opsForSet().add("chat:" + roomId + ":read:" + userId, messageId);
    }

    // 읽음 여부 확인
    public boolean isMessageRead(String roomId, String userId, String messageId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("chat:" + roomId + ":read:" + userId, messageId));
    }

    // 참여자 추가
    public void addParticipant(String roomId, String userId) {
        redisTemplate.opsForSet().add("chat:" + roomId + ":participants", userId);
    }

    // 참여자 목록 조회
    public Set<Object> getParticipants(String roomId) {
        return redisTemplate.opsForSet().members("chat:" + roomId + ":participants");
    }
}