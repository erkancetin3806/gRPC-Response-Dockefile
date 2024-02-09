package com.chatapplication.Chat.Application.service.impl;

import com.chatapplication.Chat.Application.service.ChatService;
import com.erkancetin.grpc.MessageRequest;
import com.erkancetin.grpc.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public MessageResponse sendMessage(MessageRequest request) {

        String sender = request.getSender();
        String message = request.getMessage();

        byte[] messageBytes = message.getBytes();

        System.out.println("Mesajın Byte Hali: " + Arrays.toString(messageBytes));

        MessageResponse response = MessageResponse.newBuilder()
                .setSender(sender)
                .setMessage("Received - Kullanıcının Gördüğü : " + message)
                .build();
        return response;
    }


 /*
    @Override
    public MessageResponse sendMessage(MessageRequest request) {
        // Gelen mesajı işle ve yanıtı oluştur
        String sender = request.getSender();
        String message = request.getMessage();
        MessageResponse response = MessageResponse.newBuilder()
                .setSender(sender)
                .setMessage("Received: " + message)
                .build();
        return response;
    }
*/

}

