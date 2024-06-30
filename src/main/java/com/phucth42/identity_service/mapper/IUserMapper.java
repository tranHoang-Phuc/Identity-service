package com.phucth42.identity_service.mapper;

import com.phucth42.identity_service.dto.request.UserCreationRequest;
import com.phucth42.identity_service.dto.request.UserUpdateRequest;
import com.phucth42.identity_service.dto.response.UserResponse;
import com.phucth42.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    User toDomainModel(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    List<UserResponse> toUsersResponse(List<User> users);
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
