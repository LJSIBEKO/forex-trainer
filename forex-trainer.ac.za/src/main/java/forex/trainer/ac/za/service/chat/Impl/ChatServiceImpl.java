package forex.trainer.ac.za.service.chat.Impl;

import forex.trainer.ac.za.exception.RequestException;
import forex.trainer.ac.za.model.account.Role;
import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.chat.Chat;
import forex.trainer.ac.za.repository.account.UserAccountRepository;
import forex.trainer.ac.za.repository.chat.ChatRepository;
import forex.trainer.ac.za.service.chat.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService
{
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public Chat createIndividualChat(UUID user1_id, UUID user2_id) {

        UserAccount user1 = userAccountRepository.findById(user1_id).orElseThrow(() -> new RequestException("User Account 1 not found"));
        UserAccount user2 = userAccountRepository.findById(user2_id).orElseThrow(() -> new RequestException("User Account 2 not found"));
        Chat chat = new Chat();
        chat.setGroupChat(false);
        chat.setParticipants(Arrays.asList(user1, user2));
        return chatRepository.save(chat);
    }

    public Chat createGroupChat(String name, UUID coach, List<UserAccount> participants) {

        UserAccount creator = userAccountRepository.findById(coach).orElseThrow(() ->  new RequestException("Coach not found"));

        if (!creator.getRoles().equals(Role.COACH)) {
            throw new RequestException("Only coaches can create group chats.");
        }


        Chat chat = new Chat();
        chat.setName(name);
        chat.setGroupChat(true);
        chat.setParticipants(participants);
        return chatRepository.save(chat);
    }

    @Override
    public Chat createChat(String chatName, boolean isGroupChat, List<String> participants) {
        List<UserAccount> userAccounts = new ArrayList<>();
        participants.forEach(string -> {
            UserAccount userAccount = userAccountRepository.findUserAccountByUsername(string);
            if (userAccount != null) {
                userAccounts.add(userAccount);
            }
        });

        if(userAccounts.isEmpty())
            throw new RequestException("Users not found.");

        if(userAccounts.size() < 2)
            throw new RequestException("Users not found.");

        Chat chat = new Chat();
        chat.setName(chatName.toLowerCase());
        chat.setGroupChat(isGroupChat);
        chat.setParticipants(userAccounts);
        return chatRepository.save(chat);
    }

    @Override
    public Chat getChatById(UUID id) {
        return chatRepository.findById(id).orElseThrow(() -> new RequestException("Chat not found"));
    }

    @Override
    public List<Chat> getUserChats(UUID userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new RequestException("User Account not found"));
        return chatRepository.getAllUsersChats(userAccount);
    }
}
