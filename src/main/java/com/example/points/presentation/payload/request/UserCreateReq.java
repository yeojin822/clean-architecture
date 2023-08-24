package com.example.points.presentation.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserCreateReq {
    @NotEmpty
    String name;
}
