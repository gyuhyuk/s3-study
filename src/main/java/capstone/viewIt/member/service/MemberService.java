package capstone.viewIt.member.service;

import capstone.viewIt.common.exception.CustomException;
import capstone.viewIt.common.exception.ErrorCode;
import capstone.viewIt.common.jwt.TokenUtils;
import capstone.viewIt.member.domain.Member;
import capstone.viewIt.member.dto.LoginRequest;
import capstone.viewIt.member.dto.SignUpRequest;
import capstone.viewIt.member.dto.SignUpResponse;
import capstone.viewIt.member.dto.TokenResponse;
import capstone.viewIt.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenUtils tokenUtils;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) throws CustomException {
        if (memberRepository.findByNickname(signUpRequest.getNickname()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_NICKNAME_RESOURCE);
        }
        if (memberRepository.findByMemberId(signUpRequest.getMemberId()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL_RESOURCE);
        }
        Member member = memberRepository.save(
                Member.builder()
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .memberId(signUpRequest.getMemberId())
                        .nickname(signUpRequest.getNickname())
                        .build());

        return new SignUpResponse(member.getMemberId(), member.getNickname());
    }

    @Transactional
    public TokenResponse signIn(LoginRequest loginRequest) throws CustomException {
        Member member = memberRepository
                .findByMemberId(loginRequest.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!hasSamePassword(member, loginRequest)) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        String accessToken = tokenUtils.generateJwtToken(member);
        return new TokenResponse(accessToken);
    }

    private boolean hasSamePassword(Member member, LoginRequest loginRequest) {
        return passwordEncoder.matches(loginRequest.getPassword(), member.getPassword());
    }

    public boolean checkMemberIdDuplicate(String memberId) {
        return memberRepository.existsByMemberId(memberId);
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public Member findMemberByJwt(final String jwt) {
        Claims claims = tokenUtils.getClaimsFormToken(jwt);
        String memberId = claims.get("memberId", String.class);

        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
