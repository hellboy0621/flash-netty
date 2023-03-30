package demo.im.session;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Session {

    private String userId;
    private String userName;
}
