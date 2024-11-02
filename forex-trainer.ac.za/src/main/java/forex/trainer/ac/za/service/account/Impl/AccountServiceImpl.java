package forex.trainer.ac.za.service.account.Impl;

import forex.trainer.ac.za.config.jwt.JwtUtil;
import forex.trainer.ac.za.dtos.account.login.LoginRequest;
import forex.trainer.ac.za.dtos.account.login.LoginResponse;
import forex.trainer.ac.za.exception.RequestException;
import forex.trainer.ac.za.model.account.AccountStatus;
import forex.trainer.ac.za.model.account.Permission;
import forex.trainer.ac.za.model.account.Role;
import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.repository.UserAccountRepository;
import forex.trainer.ac.za.service.account.AccountService;
import forex.trainer.ac.za.utils.EmailValidator;
import forex.trainer.ac.za.utils.PhoneNumberValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountServiceImpl  implements AccountService
{

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public UserAccount createAccount(UserAccount userAccount) {

        log.info("User Account {}", userAccount.toString());
        UserAccount newUserAccount = new UserAccount();

        if (!EmailValidator.isValidEmail(userAccount.getEmail())) {
            throw new RequestException("Invalid email format");
        }

        String formattedPhoneNumber = PhoneNumberValidator.validateAndFormatPhoneNumber(userAccount.getMobile());

        newUserAccount.setMobile(formattedPhoneNumber);

        if (userAccountRepository.existsByUsername(userAccount.getUsername()))
            throw new RequestException( "Username already exists");

        if (userAccountRepository.existsByEmail(userAccount.getEmail()))
            throw new RequestException("Email already exists");

        newUserAccount.setUsername(userAccount.getUsername());
        newUserAccount.setEmail(userAccount.getEmail());
        newUserAccount.setMobile(formattedPhoneNumber);

        newUserAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        newUserAccount.setAccountStatus(AccountStatus.ACTIVE);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.CUSTOMER);
        List<Permission>  permissions = new ArrayList<>();
        permissions.add(Permission.CUSTOMER_WRITE);
        permissions.add(Permission.CUSTOMER_READ);
        permissions.add(Permission.CUSTOMER_DELETE);
        permissions.add(Permission.CUSTOMER_UPDATE);
        newUserAccount.setRoles(roles);
        newUserAccount.setPermissions(permissions);

        return userAccountRepository.save(newUserAccount);
    }

    @Override
    public UserAccount getAccountByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElseThrow(
                () -> new RequestException("User not found"));
    }

    @Override
    public UserAccount updateAccount(UserAccount userAccount) {
        return null;
    }

    @Override
    public UserAccount changeAccountStatus(String username, AccountStatus newStatus) {
        return null;
    }

    @Override
    public LoginResponse loginUserAccount(LoginRequest loginRequest) {

        UserAccount userAccount = userAccountRepository.findByUsername(loginRequest.getEmail()).orElseThrow(
                () -> new RequestException("User not found")
        );

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

            // Return the token in the response

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-'T'HH:mm");
            loginResponse.setExpires(LocalDateTime.now().plusHours(24).format(formatter));
            return loginResponse;
        } catch (Exception e){
            e.printStackTrace();
            throw new RequestException("Invalid credentials");
        }

    }

    @Override
    public void deleteAccount(String username) {

    }
}
