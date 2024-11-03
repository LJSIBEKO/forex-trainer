package forex.trainer.ac.za.controller;

import forex.trainer.ac.za.dtos.data.CreateChats;
import forex.trainer.ac.za.model.chat.Chat;
import forex.trainer.ac.za.service.chat.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/chats")
public class ChatController
{

    @Autowired
    private ChatService chatService;

    @PostMapping("/create")
    public ResponseEntity<Chat> createChat(@RequestBody  CreateChats createChats) {
        log.info("Create chat: {}", createChats.toString());
        return new ResponseEntity<>(chatService.createChat(createChats.getChatName(), createChats.isGroupChat(),createChats.getParticipants()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable UUID id) {
        return new ResponseEntity<>(chatService.getChatById(id), HttpStatus.OK);
    }

    @GetMapping("user/chats/{userId}")
    public ResponseEntity<List<Chat>> getChatsByUserId(@PathVariable UUID userId) {
        return new ResponseEntity<>(chatService.getUserChats(userId),HttpStatus.OK);
    }


}
