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

    public SignUpResponse(String memberId) {
        this.memberId = memberId;
    }
}
