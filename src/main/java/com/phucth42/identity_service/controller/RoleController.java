package com.phucth42.identity_service.controller;

import com.phucth42.identity_service.dto.request.RoleRequest;
import com.phucth42.identity_service.dto.response.ApiResponse;
import com.phucth42.identity_service.dto.response.RoleResponse;
import com.phucth42.identity_service.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createPermission(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .result(roleService.createRole(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getPermissions() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(1000)
                .result(roleService.getRoles())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<String> deletePermission(@PathVariable String name) {
        roleService.deleteRole(name);
        return ApiResponse.<String>builder()
                .code(1000)
                .result("Role deleted")
                .build();
    }
}
