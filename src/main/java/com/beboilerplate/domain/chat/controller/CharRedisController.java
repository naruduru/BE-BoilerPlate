package com.beboilerplate.domain.chat.controller;

import com.beboilerplate.domain.chat.dto.ChatDto;
import com.beboilerplate.domain.chat.service.ChatRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/chat")
public class CharRedisController {

    private final ChatRedisService chatRedisService;

    @Autowired
    public CharRedisController(ChatRedisService chatRedisService) {
        this.chatRedisService = chatRedisService;
    }

    /**
     * 메시지 저장
     */
    @PostMapping("/{roomId}/message")
    public ResponseEntity<Void> saveMessage(@PathVariable String roomId, @RequestBody ChatDto message) {
        chatRedisService.saveMessage(roomId, message);
        return ResponseEntity.ok().build();
    }

    /**
     * 메시지 조회
     */
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<ChatDto>> getMessages(@PathVariable String roomId,
                                                         @RequestParam(defaultValue = "20") int limit) {
        List<ChatDto> messages = chatRedisService.getMessages(roomId, limit);
        return ResponseEntity.ok(messages);
    }

    /**
     * 읽음 여부 업데이트
     */
    @PostMapping("/{roomId}/read")
    public ResponseEntity<Void> markMessageAsRead(@PathVariable String roomId,
                                                  @RequestParam String userId,
                                                  @RequestParam String messageId) {
        chatRedisService.markAsRead(roomId, userId, messageId);
        return ResponseEntity.ok().build();
    }

    /**
     * 읽음 여부 확인
     */
    @GetMapping("/{roomId}/read")
    public ResponseEntity<Boolean> isMessageRead(@PathVariable String roomId,
                                                 @RequestParam String userId,
                                                 @RequestParam String messageId) {
        boolean isRead = chatRedisService.isMessageRead(roomId, userId, messageId);
        return ResponseEntity.ok(isRead);
    }

    /**
     * 채팅방 참여자 추가
     */
    @PostMapping("/{roomId}/participants")
    public ResponseEntity<Void> addParticipant(@PathVariable String roomId, @RequestParam String userId) {
        chatRedisService.addParticipant(roomId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * 채팅방 참여자 조회
     */
    @GetMapping("/{roomId}/participants")
    public ResponseEntity<Set<Object>> getParticipants(@PathVariable String roomId) {
        Set<Object> participants = chatRedisService.getParticipants(roomId);
        return ResponseEntity.ok(participants);
    }
}
