package com.example.rbacdemo.controller;

import com.example.rbacdemo.dto.RoleRequest;
import com.example.rbacdemo.dto.RoleResponse;
import com.example.rbacdemo.model.Role;
import com.example.rbacdemo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 获取所有角色
     *
     * @return 角色列表
     */
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<Role> roles = roleService.findAllRoles();

        List<RoleResponse> response = roles.stream()
                .map(role -> RoleResponse.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .permissions(role.getPermissions())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * 创建新角色
     *
     * @param request 角色请求信息
     * @return 创建成功的角色信息
     */
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest request) {
        Role role = roleService.createRole(request.getName());

        RoleResponse response = RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(role.getPermissions())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 为角色分配权限
     *
     * @param roleId        角色ID
     * @param permissionIds 权限ID集合
     * @return 分配结果
     */
    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<String> assignPermissionsToRole(
            @PathVariable Long roleId,
            @RequestBody Set<Long> permissionIds) {

        roleService.assignPermissionsToRole(roleId, permissionIds);
        return ResponseEntity.ok("权限分配成功");
    }
}