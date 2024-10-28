package capstone.viewIt.member.dto;

import capstone.viewIt.member.domain.Member;

public record MemberDto(String memberId, String password, String nickname) {
    public static MemberDto of(String memberId, String password, String nickname) {
        return new MemberDto(memberId, password, nickname);
    }

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getMemberId(),
                member.getPassword(),
                member.getNickname()
        );
    }
}
