package forex.trainer.ac.za.dtos.account.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest
{
    private String email;
    private String password;
}
