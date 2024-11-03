package forex.trainer.ac.za.repository.account_confirmation;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.account.confirmation.AccountConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AccountConfirmationRepository extends JpaRepository<AccountConfirmation, UUID>
{
    List<AccountConfirmation> findAccountConfirmationByAccountId(UUID accountId);

    @Query("SELECT ac FROM AccountConfirmation ac WHERE ac.account = ?1 ORDER BY ac.creationDate DESC")
    AccountConfirmation findLatestAccountConfirmation(UserAccount userAccount);
}
