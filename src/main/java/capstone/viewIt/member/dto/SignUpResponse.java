package capstone.viewIt.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpResponse {
    private String memberId;
    private String nickname;

    public SignUpResponse(String memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
