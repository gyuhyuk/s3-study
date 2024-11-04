package capstone.viewIt.member.controller;

import capstone.viewIt.common.exception.CustomException;
import capstone.viewIt.common.response.CustomResponse;
import capstone.viewIt.member.dto.LoginRequest;
import capstone.viewIt.member.dto.SignUpRequest;
import capstone.viewIt.member.dto.SignUpResponse;
import capstone.viewIt.member.dto.TokenResponse;
import capstone.viewIt.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원가입
    @PostMapping("/api/auth/sign-up")
    public CustomResponse<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return new CustomResponse<>(
                200,
                "회원가입에 성공했습니다.",
                memberService.signUp(signUpRequest)
        );
    }

    // 로그인
    @PostMapping("/api/auth/sign-in")
    public CustomResponse<TokenResponse> signIn(@Valid @RequestBody LoginRequest loginRequest) throws CustomException {
        return new CustomResponse<>(
                HttpStatus.OK.value(), "로그인에 성공했습니다.", memberService.signIn(loginRequest)
        );
    }

    // 아이디 중복 확인
    @GetMapping("/api/email/{memberId}/exists")
    public CustomResponse<Boolean> checkMemberIdDuplicate(@PathVariable String memberId) {
        return new CustomResponse<> (
                HttpStatus.OK.value(), "null",
                memberService.checkMemberIdDuplicate(memberId)
        );
    }
}
