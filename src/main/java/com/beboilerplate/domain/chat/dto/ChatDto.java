package com.beboilerplate.domain.chat.dto;

import lombok.Data;

@Data
public class ChatDto {
    private String sender;
    private String content;
    private String sendAt;
}
