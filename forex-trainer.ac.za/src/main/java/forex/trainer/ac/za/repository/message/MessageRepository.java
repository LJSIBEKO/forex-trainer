package forex.trainer.ac.za.repository.message;

import forex.trainer.ac.za.model.messages.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID>
{
    @Query("select a FROM Message a WHERE a.chatId = ?1")
    List<Message> findByChatId(String chatId);
}
