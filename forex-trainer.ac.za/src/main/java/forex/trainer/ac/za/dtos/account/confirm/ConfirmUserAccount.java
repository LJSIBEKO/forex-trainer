package forex.trainer.ac.za.dtos.account.confirm;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConfirmUserAccount
{
    private UUID accountId;
    private String code;
}
