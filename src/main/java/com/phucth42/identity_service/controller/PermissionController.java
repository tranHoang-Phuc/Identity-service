package com.phucth42.identity_service.controller;

import com.phucth42.identity_service.dto.request.PermissionRequest;
import com.phucth42.identity_service.dto.response.ApiResponse;
import com.phucth42.identity_service.dto.response.PermissionResponse;
import com.phucth42.identity_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .code(1000)
                .result(permissionService.createPermission(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(1000)
                .result(permissionService.getPermissions())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<String> deletePermission(@PathVariable String name) {
        permissionService.deletePermission(name);
        return ApiResponse.<String>builder()
                .code(1000)
                .result("Permission deleted")
                .build();
    }
}
