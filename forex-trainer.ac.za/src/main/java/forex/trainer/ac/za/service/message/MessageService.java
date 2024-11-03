package forex.trainer.ac.za.service.message;

import forex.trainer.ac.za.model.messages.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService
{

    Message sendMessage(UUID chatId, UUID senderId, String content);

    List<Message> getMessagesByChatId(UUID chatId);
}
