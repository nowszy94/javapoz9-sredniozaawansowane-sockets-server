package com.sda.chat.infrastructure.file;

import com.sda.chat.domain.model.ChatMessage;
import com.sda.chat.domain.model.ChatUser;
import com.sda.chat.domain.port.MessagesRepository;
import com.sda.chat.domain.port.UsersRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InTextFileMessagesRepository implements MessagesRepository {

    private File file;
    private PrintWriter out;
    private List<ChatMessage> messages;

    public InTextFileMessagesRepository(String filePath, UsersRepository usersRepository) throws FileNotFoundException {
        this.file = new File(filePath);
        this.out = new PrintWriter(file);
        this.messages = new ArrayList<>();

        init(usersRepository);
    }

    private void init(UsersRepository usersRepository) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split(";");
            String senderName = split[0];
            String receiverName = split[1];
            Instant date = Instant.parse(split[2]);
            String message = split[3]; //TODO pobierac pozostale elementy do message

            ChatMessage chatMessage = new ChatMessage(
                    usersRepository.findByName(receiverName),
                    usersRepository.findByName(senderName),
                    message,
                    date);
            this.messages.add(chatMessage);
        }
    }

    @Override
    public void saveMessage(ChatMessage message) {
        this.messages.add(message);
        out.println(messageToFileFormat(message));
        out.flush();
    }

    private String messageToFileFormat(ChatMessage chatMessage) {
        return chatMessage.getSender().getName() + ";" +
                chatMessage.getReceiver().getName() + ";" +
                chatMessage.getDate().toString() + ";" +
                chatMessage.getMessage();
    }

    @Override
    public List<ChatMessage> findMessagesFor(ChatUser chatUser) {
        return this.messages.stream()
                .filter(e -> e.getSender().equals(chatUser) || e.getReceiver().equals(chatUser))
                .collect(Collectors.toList());
    }
}
