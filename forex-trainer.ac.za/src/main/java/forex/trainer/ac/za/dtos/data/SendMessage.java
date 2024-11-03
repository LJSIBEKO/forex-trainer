package forex.trainer.ac.za.dtos.data;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SendMessage
{
    private UUID chatId;
    private UUID senderId;
    private String content;
}
