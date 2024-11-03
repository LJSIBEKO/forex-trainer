package forex.trainer.ac.za.model.chat;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.base.AbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Chat extends AbstractPersistenceEntity
{
    @Column(nullable = false)
    private String name;

    private boolean groupChat;

    @ManyToMany // Adjust according to your relationship
    @JoinTable(
            name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserAccount> participants;
}
