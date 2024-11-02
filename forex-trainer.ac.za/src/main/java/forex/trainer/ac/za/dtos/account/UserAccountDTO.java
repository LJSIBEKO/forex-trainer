package forex.trainer.ac.za.dtos.account;

import forex.trainer.ac.za.dtos.base.DTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAccountDTO  extends DTO
{
    private String username;
    private String email;
    private List<String> roles;
    private List<String> permissions;
    private String status;

}
