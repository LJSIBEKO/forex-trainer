package forex.trainer.ac.za.repository.account;

import forex.trainer.ac.za.model.account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID>
{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<UserAccount> findByUsername(String username);
    UserAccount findUserAccountByUsername(String username);
    UserAccount findByEmail(String email);
    UserAccount findByMobile(String mobile);
}
