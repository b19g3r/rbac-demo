package com.example.rbacdemo.controller;

import com.example.rbacdemo.dto.PermissionRequest;
import com.example.rbacdemo.dto.PermissionResponse;
import com.example.rbacdemo.model.Permission;
import com.example.rbacdemo.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

	private final PermissionService permissionService;

	/**
	 * 获取所有权限
	 * @return 权限列表
	 */
	@GetMapping
	public ResponseEntity<List<PermissionResponse>> getAllPermissions() {
		List<Permission> permissions = permissionService.findAllPermissions();

		List<PermissionResponse> response = permissions.stream()
			.map(permission -> PermissionResponse.builder().id(permission.getId()).name(permission.getName()).build())
			.collect(Collectors.toList());

		return ResponseEntity.ok(response);
	}

	/**
	 * 创建新权限
	 * @param request 权限请求信息
	 * @return 创建成功的权限信息
	 */
	@PostMapping
	public ResponseEntity<PermissionResponse> createPermission(@RequestBody PermissionRequest request) {
		Permission permission = permissionService.createPermission(request.getName());

		PermissionResponse response = PermissionResponse.builder()
			.id(permission.getId())
			.name(permission.getName())
			.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}