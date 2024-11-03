package forex.trainer.ac.za.repository.chat;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID>
{

    @Query("SELECT c FROM Chat c JOIN c.participants p WHERE p = :userAccount")
    List<Chat> getAllUsersChats(@Param("userAccount") UserAccount userAccount);
}
