package com.example.rbacdemo.controller;

import com.example.rbacdemo.dto.UserResponse;
import com.example.rbacdemo.model.User;
import com.example.rbacdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 获取所有用户信息
     *
     * @return 用户列表
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAllUsers();

        List<UserResponse> response = users.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(user.getRoles())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * 获取指定用户信息
     *
     * @param id 用户ID
     * @return 用户详细信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.findUserById(id);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();

        return ResponseEntity.ok(response);
    }
    
    /**
     * 为用户分配角色
     *
     * @param userId  用户ID
     * @param roleIds 角色ID集合
     * @return 分配结果
     */
    @PutMapping("/{userId}/roles")
    public ResponseEntity<String> assignRolesToUser(
            @PathVariable Long userId,
            @RequestBody Set<Long> roleIds) {

        userService.assignRolesToUser(userId, roleIds);
        return ResponseEntity.ok("角色分配成功");
    }
} 