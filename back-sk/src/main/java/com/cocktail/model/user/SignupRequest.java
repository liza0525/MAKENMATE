package com.cocktail.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Valid
@Getter
@Setter
@ToString
public class SignupRequest {
    @ApiModelProperty(required = true)
    @NotNull
    String email;
    @ApiModelProperty(required = true)
    @NotNull
    String nickname;
    @ApiModelProperty(required = true)
    @NotNull
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$")
    String password;

    // @ApiModelProperty(required = true)
    // @NotNull
    // String name;
    // @ApiModelProperty(required = true)
    // @NotNull
    // String phone;
    // @ApiModelProperty(required = true)
    // @NotNull
    // String ssn;
    // String intro;


}
