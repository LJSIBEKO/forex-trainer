package forex.trainer.ac.za.service.account;

import forex.trainer.ac.za.dtos.account.login.LoginRequest;
import forex.trainer.ac.za.dtos.account.login.LoginResponse;
import forex.trainer.ac.za.model.account.AccountStatus;
import forex.trainer.ac.za.model.account.UserAccount;

public interface AccountService {
    /**
     * Create a new user account.
     *
     * @param userAccount the user account details to be created
     * @return the created UserAccount object
     */
    UserAccount createAccount(UserAccount userAccount);

    /**
     * Retrieve a user account by username.
     *
     * @param username the username of the account to be retrieved
     * @return an Optional containing the UserAccount if found, or empty if not
     */
    UserAccount getAccountByUsername(String username);

    /**
     * Update an existing user account.
     *
     * @param userAccount the updated user account details
     * @return the updated UserAccount object
     */
    UserAccount updateAccount(UserAccount userAccount);

    /**
     * Change the status of a user account.
     *
     * @param username the username of the account to be updated
     * @param newStatus the new status to set for the account
     * @return the updated UserAccount object
     */
    UserAccount changeAccountStatus(String username, AccountStatus newStatus);

    LoginResponse loginUserAccount(LoginRequest loginResponse);

    /**
     * Delete a user account by username.
     *
     * @param username the username of the account to be deleted
     */
    void deleteAccount(String username);
}
