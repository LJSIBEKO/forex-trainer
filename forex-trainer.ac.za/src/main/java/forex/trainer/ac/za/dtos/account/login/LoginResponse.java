package forex.trainer.ac.za.dtos.account.login;

import forex.trainer.ac.za.model.account.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse
{
    private String token;
    private String expires;
    private UserAccount account;
}
