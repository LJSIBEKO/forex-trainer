package forex.trainer.ac.za.service.chat;

import forex.trainer.ac.za.model.chat.Chat;

import java.util.List;
import java.util.UUID;

public interface ChatService
{
    Chat createChat(String chatName, boolean isGroupChat, List<String> participants);

    Chat getChatById(UUID id);

    List<Chat> getUserChats(UUID userId);
}
