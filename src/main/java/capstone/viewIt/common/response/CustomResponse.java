package capstone.viewIt.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CustomResponse<T> { //상태코드, 메세지, 데이터 담아서 락엔락 ~ 이 세가지로 효리한테 무조건 넘겨준다 !!!!!!!!!!!!!!!!!!!

    private final int status;
    private final String message;

    @Setter
    private T data;
}