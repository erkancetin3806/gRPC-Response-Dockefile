package com.chatapplication.Chat.Application;

import com.chatapplication.Chat.Application.service.ChatService;
import com.chatapplication.Chat.Application.service.impl.ChatServiceImpl;
import com.erkancetin.grpc.ChatServiceGrpc;
import com.erkancetin.grpc.MessageRequest;
import com.erkancetin.grpc.MessageResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {

		int portNumber = 9999; // Server'ın dinleyeceği port

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			System.out.println("Server başlatıldı. Port: " + portNumber);

			while (true) {
				System.out.println("Bağlantı bekleniyor...");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Bağlantı kabul edildi: " + clientSocket);

				handleClient(clientSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handleClient(Socket clientSocket) {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
			String line;
			while ((line = reader.readLine()) != null) {

				// İstek metnini virgül karakterinden ayırma
				String[] params = line.split(",");

				// İki parametre olup olmadığını kontrol etme
				if (params.length == 2) {
					String sender = params[0]; // İlk parametre gönderici
					String message = params[1]; // İkinci parametre mesaj

					// Mesajı oluşturma
					MessageRequest request = MessageRequest.newBuilder()
							.setSender(sender)
							.setMessage(message)
							.build();

					// Servise isteği gönderme ve yanıtı alma
					ChatService service = new ChatServiceImpl();
					MessageResponse response = service.sendMessage(request);

					// Yanıtı yazdırma
					System.out.println("Response from server: " + response.getMessage());
				} else {
					System.out.println("Invalid request format. Please provide sender and message separated by comma.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}




/*
@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {
		// Örnek gelen istek metni
		String requestText = "okey,hıkan";

		// İstek metnini virgül karakterinden ayırma
		String[] params = requestText.split(",");

		// İki parametre olup olmadığını kontrol etme
		if (params.length == 2) {
			String sender = params[0]; // İlk parametre gönderici
			String message = params[1]; // İkinci parametre mesaj

			// Mesajı oluşturma
			MessageRequest request = MessageRequest.newBuilder()
					.setSender(sender)
					.setMessage(message)
					.build();

			// Servise isteği gönderme ve yanıtı alma
			ChatService service = new ChatServiceImpl();
			MessageResponse response = service.sendMessage(request);

			// Yanıtı yazdırma
			System.out.println("Response from server: " + response.getMessage());
		} else {
			System.out.println("Invalid request format. Please provide sender and message separated by comma.");
		}
	}
}*/





/*@SpringBootApplication
public class ChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}
}*/

