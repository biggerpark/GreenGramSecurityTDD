package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "로그인 요청")
public class UserSignInReq {

    @NotNull(message = "아이디를 입력하셔야 합니다.")
    @Schema(title = "아이디", example = "mic", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;


    @NotNull(message = "비밀번호를 입력하셔야 합니다.")
    @Schema(title = "비밀번호", example = "1212", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
}
