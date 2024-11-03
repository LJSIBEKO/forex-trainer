package forex.trainer.ac.za.model.account.confirmation;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.base.AbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class AccountConfirmation extends AbstractPersistenceEntity
{
    @ManyToOne
    @JoinColumn(name = "user_account_id", nullable = false)
    private UserAccount account;

    @Column(nullable = false) // Ensure confirmationCode cannot be null
    private String confirmationCode;

    @Column(name = "confirmation_expiry_date", nullable = false) // Optional: specify the column name
    private LocalDateTime confirmationExpiryDate;

    private int attempts;

    @Column(nullable = false) // Ensure confirmed cannot be null
    private boolean confirmed;
}
