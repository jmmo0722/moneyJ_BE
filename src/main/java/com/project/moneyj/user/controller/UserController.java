package com.project.moneyj.user.controller;

import com.project.moneyj.user.dto.UserResponse;
import com.project.moneyj.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

//    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public ResponseEntity<UserResponse> getUser() {
        UserResponse userResponse = userService.getUser();

        return ResponseEntity.ok(userResponse);
    }
}
