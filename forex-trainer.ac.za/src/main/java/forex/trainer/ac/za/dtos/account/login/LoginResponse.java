package forex.trainer.ac.za.dtos.account.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse
{
    private String token;
    private String expires;
}
