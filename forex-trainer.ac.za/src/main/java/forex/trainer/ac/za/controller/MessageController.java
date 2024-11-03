package forex.trainer.ac.za.controller;

import forex.trainer.ac.za.dtos.data.SendMessage;
import forex.trainer.ac.za.model.messages.Message;
import forex.trainer.ac.za.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class MessageController
{
    @Autowired
    private MessageService messageService;


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessage sendMessage) {
        return new ResponseEntity<>(messageService.sendMessage(sendMessage.getChatId(), sendMessage.getSenderId(), sendMessage.getContent()), HttpStatus.OK);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable UUID chatId) {
        return new ResponseEntity<>(messageService.getMessagesByChatId(chatId), HttpStatus.OK);
    }

}
