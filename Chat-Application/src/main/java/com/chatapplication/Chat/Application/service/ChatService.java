package com.chatapplication.Chat.Application.service;

import com.erkancetin.grpc.MessageRequest;
import com.erkancetin.grpc.MessageResponse;

public interface ChatService {
    MessageResponse sendMessage(MessageRequest request);
}

