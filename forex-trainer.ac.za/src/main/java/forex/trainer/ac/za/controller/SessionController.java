package forex.trainer.ac.za.controller;

import forex.trainer.ac.za.dtos.account.confirm.ConfirmUserAccount;
import forex.trainer.ac.za.dtos.account.login.LoginRequest;
import forex.trainer.ac.za.dtos.account.login.LoginResponse;
import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("sessions")
public class SessionController
{
    @Autowired
    private AccountService accountService;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(accountService.loginUserAccount(loginRequest), HttpStatus.OK);
    }

    @PostMapping("create/client/account")
    public ResponseEntity<UserAccount> createClientAccount(@RequestBody UserAccount userAccount){
        return new ResponseEntity<>(accountService.createAccount(userAccount),HttpStatus.OK);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<UserAccount> getClientAccount(@PathVariable UUID uuid){
        return new ResponseEntity<>(accountService.findById(uuid),HttpStatus.OK);
    }

    @PostMapping("confirm/otp")
    public ResponseEntity<UserAccount> confirmOtp(@RequestBody ConfirmUserAccount confirmUserAccount){
        return new ResponseEntity<>(accountService.confirmUserAccount(confirmUserAccount),HttpStatus.OK);
    }

    @GetMapping("view")
    public ResponseEntity<List<UserAccount>> getViewAccount(){
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }
}
