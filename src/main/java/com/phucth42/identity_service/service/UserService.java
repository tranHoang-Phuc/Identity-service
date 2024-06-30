package com.phucth42.identity_service.service;

import com.phucth42.identity_service.dto.request.UserCreationRequest;
import com.phucth42.identity_service.dto.request.UserUpdateRequest;
import com.phucth42.identity_service.dto.response.ApiResponse;
import com.phucth42.identity_service.dto.response.UserResponse;
import com.phucth42.identity_service.entity.User;
import com.phucth42.identity_service.exception.AppException;
import com.phucth42.identity_service.exception.ErrorCode;
import com.phucth42.identity_service.mapper.IUserMapper;
import com.phucth42.identity_service.repository.IRoleRepository;
import com.phucth42.identity_service.repository.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

    IUserRepository userRepository;
    IUserMapper userMapper;
    PasswordEncoder passwordEncoder;
    IRoleRepository roleRepository;

    public ApiResponse<UserResponse> createUser(UserCreationRequest request) {
        User checkedUser = userRepository.findByUsername(request.getUsername());
        if (checkedUser != null)
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toDomainModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        UserResponse userResponse = userMapper.toUserResponse(userRepository.findByUsername(request.getUsername()));
        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userResponse)
                .build();
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserResponse>> getUsers() {
        log.info("Get all users");
        ApiResponse<List<UserResponse>> response = ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .result(userMapper.toUsersResponse(userRepository.findAll()))
                .build();
        return response;
    }

    public ApiResponse<UserResponse> updateUser(String username, UserUpdateRequest request) {
        User user = userRepository.findByUsername(username);
        User checkedUser = userRepository.findByUsername(username);
        if (checkedUser == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userMapper.updateUser(user, request);
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        userRepository.save(user);

        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Update successfully")
                .result(userMapper.toUserResponse(user))
                .build();
        return response;
    }

    public ApiResponse<String> deleteUser(String username) {
        User user = userRepository.findByUsername(username);
        userRepository.delete(user);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("Delete successfully")
                .build();
    }
    @PostAuthorize("returnObject.result.username == authentication.principal.username")
    public ApiResponse<UserResponse> getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userMapper.toUserResponse(user))
                .build();
    }
}
