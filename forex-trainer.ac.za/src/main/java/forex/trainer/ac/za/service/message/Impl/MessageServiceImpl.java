package forex.trainer.ac.za.service.message.Impl;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.chat.Chat;
import forex.trainer.ac.za.model.messages.Message;
import forex.trainer.ac.za.repository.account.UserAccountRepository;
import forex.trainer.ac.za.repository.chat.ChatRepository;
import forex.trainer.ac.za.repository.message.MessageRepository;
import forex.trainer.ac.za.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService
{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Message sendMessage(UUID chatId, UUID senderId, String content) {
        UserAccount userAccount = userAccountRepository.findById(senderId).orElseThrow(() -> new RuntimeException("User not found"));
        Message message = new Message();
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        message.setContent(content);
        message.setSenderId(String.valueOf(userAccount.getId()));
        message.setSenderName(userAccount.getUsername());
        message.setChatId(String.valueOf(chat.getId()));
        message.setTimestamp(System.currentTimeMillis());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessagesByChatId(UUID chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        return messageRepository.findByChatId(String.valueOf(chat.getId()));
    }
}
