package capstone.viewIt.member.dto;

import capstone.viewIt.member.domain.Member;

public record MemberDto(String memberId, String password) {
    public static MemberDto of(String memberId, String password) {
        return new MemberDto(memberId, password);
    }

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getMemberId(),
                member.getPassword()
        );
    }
}
