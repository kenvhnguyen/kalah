package com.nek.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class PlayerDto {

    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
