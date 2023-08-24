package com.example.points.presentation.controller;

import com.example.points.presentation.payload.request.UserCreateReq;
import com.example.points.presentation.payload.response.UserRes;
import com.example.points.usecases.dto.UserDto;
import com.example.points.usecases.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserUseCase userUseCase;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid UserCreateReq userCreateReq) {
        UserDto user = userUseCase.create(userCreateReq);
        return ResponseEntity.ok().body(new UserRes(user));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRes> find(@PathVariable Long userId) {
        UserDto user = userUseCase.find(userId);
        return ResponseEntity.ok().body(new UserRes(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        userUseCase.delete(userId);
        return ResponseEntity.ok().build();
    }

}
