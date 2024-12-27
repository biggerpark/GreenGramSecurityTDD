package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserSignUpReq {
    @JsonIgnore
    private long userId;
    @JsonIgnore
    private String pic;

    @Size(min=3,max=30,message="아이디는 3~30자 사이만 가능합니다.")
    @Schema(description = "유저 아이디", example = "mic", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;

    @Size(min=4, max=50, message = "비밀번호는 4~50자 사이만 가능합니다.")
    @Schema(description = "유저 비밀번호", example = "1212", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
    @Schema(description = "유저 닉네임", example = "홍길동")
    private String nickName;
}
