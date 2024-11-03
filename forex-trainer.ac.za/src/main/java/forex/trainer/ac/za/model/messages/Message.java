package forex.trainer.ac.za.model.messages;


import forex.trainer.ac.za.model.base.AbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Message extends AbstractPersistenceEntity
{
    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "sender_id")
    private String senderId;

    private String senderName;

    private String content;

    @Column(name = "timestamp")
    private long timestamp;
}
