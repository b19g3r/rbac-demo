package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.model.Permission;
import com.example.rbacdemo.model.Role;
import com.example.rbacdemo.repository.PermissionRepository;
import com.example.rbacdemo.repository.RoleRepository;
import com.example.rbacdemo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	private final PermissionRepository permissionRepository;

	@Override
	public Role createRole(String name) {
		Role role = new Role();
		role.setName(name);
		role.setPermissions(new HashSet<>());
		return roleRepository.save(role);
	}

	@Override
	public List<Role> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	@Transactional
	public void assignPermissionsToRole(Long roleId, Set<Long> permissionIds) {
		Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("角色不存在: ID=" + roleId));

		Set<Permission> permissions = permissionIds.stream()
			.map(permissionId -> permissionRepository.findById(permissionId)
				.orElseThrow(() -> new RuntimeException("权限不存在: ID=" + permissionId)))
			.collect(Collectors.toSet());

		role.setPermissions(permissions);
		roleRepository.save(role);
	}

}