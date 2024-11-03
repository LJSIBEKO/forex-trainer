package forex.trainer.ac.za;

import forex.trainer.ac.za.model.account.AccountStatus;
import forex.trainer.ac.za.model.account.Permission;
import forex.trainer.ac.za.model.account.Role;
import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.repository.account.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class Application {

    @Autowired
    private Environment env;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            String username = env.getProperty("superadmin.username", "admin");
            String password = env.getProperty("superadmin.password", "superSecretPassword");
            String email = env.getProperty("superadmin.email", "admin@example.com");
            String mobile = env.getProperty("superadmin.mobile", "0822847214");


            if (!userAccountRepository.existsByUsername(username)) {
                UserAccount superAdmin = new UserAccount();
                superAdmin.setUsername(username);
                superAdmin.setPassword(passwordEncoder.encode(password));
                superAdmin.setAccountStatus(AccountStatus.ACTIVE);
                superAdmin.setEmail(email);
                superAdmin.setMobile(mobile);
                superAdmin.setRoles(new ArrayList<>(List.of(Role.SUPER_ADMINISTRATOR)));
                superAdmin.setPermissions(new ArrayList<>(Arrays.asList(
                        Permission.SUPER_ADMINISTRATOR_DELETE,
                        Permission.SUPER_ADMINISTRATOR_READ,
                        Permission.SUPER_ADMINISTRATOR_WRITE,
                        Permission.SUPER_ADMINISTRATOR_UPDATE
                )));

                userAccountRepository.save(superAdmin);
                System.out.println("Super admin created with username: " + username);
            } else {
                System.out.println("Super admin already exists, skipping creation.");
            }


        };
    }

}
