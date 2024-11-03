package forex.trainer.ac.za.service.account.Impl;

import forex.trainer.ac.za.config.jwt.JwtUtil;
import forex.trainer.ac.za.dtos.account.confirm.ConfirmUserAccount;
import forex.trainer.ac.za.dtos.account.login.LoginRequest;
import forex.trainer.ac.za.dtos.account.login.LoginResponse;
import forex.trainer.ac.za.exception.RequestException;
import forex.trainer.ac.za.model.account.AccountStatus;
import forex.trainer.ac.za.model.account.Permission;
import forex.trainer.ac.za.model.account.Role;
import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.account.confirmation.AccountConfirmation;
import forex.trainer.ac.za.repository.account.UserAccountRepository;
import forex.trainer.ac.za.repository.account_confirmation.AccountConfirmationRepository;
import forex.trainer.ac.za.service.account.AccountService;
import forex.trainer.ac.za.utils.EmailUtil;
import forex.trainer.ac.za.utils.EmailValidator;
import forex.trainer.ac.za.utils.PhoneNumberValidator;
import forex.trainer.ac.za.utils.RandomCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private AccountConfirmationRepository accountConfirmationRepository;
    @Autowired
    private EmailUtil emailUtil;


    @Override
    public UserAccount createAccount(UserAccount userAccount) {

        log.info("User Account {}", userAccount.toString());
        UserAccount newUserAccount = new UserAccount();

        if (!EmailValidator.isValidEmail(userAccount.getEmail())) {
            throw new RequestException("Invalid email format");
        }

        String formattedPhoneNumber = PhoneNumberValidator.validateAndFormatPhoneNumber(userAccount.getMobile());

        UserAccount userAccountWithPhone = userAccountRepository.findByMobile(formattedPhoneNumber);
        if (userAccountWithPhone != null)
            throw new RequestException("Phone number already in use");

        newUserAccount.setMobile(formattedPhoneNumber);
        newUserAccount.setUsername(userAccount.getEmail().toLowerCase());

        if (userAccountRepository.existsByUsername(userAccount.getEmail().toLowerCase()))
            throw new RequestException( "Username already exists");

        if (userAccountRepository.existsByEmail(userAccount.getEmail().toLowerCase()))
            throw new RequestException("Email already exists");

        newUserAccount.setUsername(userAccount.getEmail().toLowerCase());
        newUserAccount.setEmail(userAccount.getEmail().toLowerCase());
        newUserAccount.setMobile(formattedPhoneNumber);

        newUserAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        newUserAccount.setAccountStatus(AccountStatus.AWAITING_CONFIRMATION);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.CUSTOMER);
        List<Permission>  permissions = new ArrayList<>();
        permissions.add(Permission.CUSTOMER_WRITE);
        permissions.add(Permission.CUSTOMER_READ);
        permissions.add(Permission.CUSTOMER_DELETE);
        permissions.add(Permission.CUSTOMER_UPDATE);
        newUserAccount.setRoles(roles);
        newUserAccount.setPermissions(permissions);

        UserAccount savedUserAccount = userAccountRepository.save(newUserAccount);

       sendAccountConfirmation(savedUserAccount);


        return savedUserAccount;
    }

    private void sendAccountConfirmation(UserAccount account)
    {
        AccountConfirmation accountConfirmation = new AccountConfirmation();
        accountConfirmation.setAccount(account);
        accountConfirmation.setConfirmed(false);
        accountConfirmation.setConfirmationExpiryDate(LocalDateTime.now().plusMinutes(10));
        accountConfirmation.setConfirmationCode(RandomCodeUtil.generateUniqueRandomNumbersAsString(1));
        accountConfirmationRepository.save(accountConfirmation);

        emailUtil.sendConfirmationWEmail(accountConfirmation);
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

    @Override
    public UserAccount confirmUserAccount(ConfirmUserAccount confirmUserAccount) {
        UserAccount userAccount = userAccountRepository.getReferenceById(confirmUserAccount.getAccountId());

        if(userAccount==null)
            throw new RequestException("User not found");

        if(userAccount.getAccountStatus().equals(AccountStatus.AWAITING_CONFIRMATION)){

            AccountConfirmation accountConfirmation = accountConfirmationRepository.findLatestAccountConfirmation(userAccount);

            if(accountConfirmation==null)
                throw new RequestException("Account confirmation is invalid");

            if(accountConfirmation.getConfirmationExpiryDate().isBefore(LocalDateTime.now()))
                throw new RequestException("Account confirmation is expired");

            if(accountConfirmation.getConfirmationCode()==null)
                throw new RequestException("Account confirmation code is invalid");

            if(accountConfirmation.getAttempts()>3){
                sendAccountConfirmation(userAccount);
                throw new RequestException("OTP is expired new one has been sent to you please view your emails");
            }

            if(!accountConfirmation.getConfirmationCode().equals(confirmUserAccount.getCode())){
                accountConfirmation.setAttempts(accountConfirmation.getAttempts()+1);
                accountConfirmationRepository.save(accountConfirmation);
                throw new RequestException("OTP is incorrect");
            }


            userAccount.setAccountStatus(AccountStatus.ACTIVE);
            return userAccountRepository.save(userAccount);
        }else{
            throw new RequestException("User not AWAITING_CONFIRMATION");
        }


    }

    @Override
    public UserAccount findById(UUID id) {
        log.info(" id {}",id);
        return userAccountRepository.findById(id).orElseThrow(() -> new RequestException("User not found"));
    }

    @Override
    public List<UserAccount> getAllAccounts(){
        return userAccountRepository.findAll();
    }
}
