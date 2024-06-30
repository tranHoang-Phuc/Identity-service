package com.phucth42.identity_service.service;

import com.phucth42.identity_service.dto.request.PermissionRequest;
import com.phucth42.identity_service.dto.response.PermissionResponse;
import com.phucth42.identity_service.entity.Permission;
import com.phucth42.identity_service.mapper.IPermissionMapper;
import com.phucth42.identity_service.repository.IPermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    IPermissionRepository permissionRepository;
    IPermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request) {
        Permission permission = permissionMapper.toDomainModel(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toDtoModel(permission);
    }

    public List<PermissionResponse> getPermissions() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toDtoModel).toList();
    }

    public void deletePermission(String name) {
        permissionRepository.deleteById(name);
    }
}
