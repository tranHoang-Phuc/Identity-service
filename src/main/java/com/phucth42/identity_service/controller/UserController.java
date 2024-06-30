package com.phucth42.identity_service.controller;

import com.phucth42.identity_service.dto.request.UserCreationRequest;
import com.phucth42.identity_service.dto.request.UserUpdateRequest;
import com.phucth42.identity_service.dto.response.ApiResponse;
import com.phucth42.identity_service.dto.response.UserResponse;
import com.phucth42.identity_service.entity.User;
import com.phucth42.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return userService.createUser(request);
    }
    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{username}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(username, request);
    }

    @DeleteMapping("/{username}")
    public ApiResponse<String> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
    @GetMapping("/{username}")
    public ApiResponse<UserResponse> getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

}
