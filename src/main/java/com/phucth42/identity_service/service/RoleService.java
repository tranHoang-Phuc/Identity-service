package com.phucth42.identity_service.service;

import com.phucth42.identity_service.dto.request.RoleRequest;
import com.phucth42.identity_service.dto.response.RoleResponse;
import com.phucth42.identity_service.mapper.IRoleMapper;
import com.phucth42.identity_service.repository.IPermissionRepository;
import com.phucth42.identity_service.repository.IRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    IRoleRepository roleRepository;
    IPermissionRepository permissionRepository;
    IRoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void deleteRole(String name) {
        roleRepository.deleteById(name);
    }
}
