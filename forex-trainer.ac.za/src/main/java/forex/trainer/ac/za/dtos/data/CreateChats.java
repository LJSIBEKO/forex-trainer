package forex.trainer.ac.za.dtos.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class CreateChats
{
    private String chatName;
    private boolean isGroupChat;
    private List<String> participants;
}
