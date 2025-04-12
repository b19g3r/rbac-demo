package com.example.rbacdemo.service;

import com.example.rbacdemo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

	/**
	 * 创建新角色
	 * @param name 角色名称
	 * @return 创建成功的角色信息
	 */
	Role createRole(String name);

	/**
	 * 获取所有角色
	 * @return 角色列表
	 */
	List<Role> findAllRoles();

	/**
	 * 为角色分配权限
	 * @param roleId 角色ID
	 * @param permissionIds 权限ID集合
	 */
	void assignPermissionsToRole(Long roleId, Set<Long> permissionIds);

}