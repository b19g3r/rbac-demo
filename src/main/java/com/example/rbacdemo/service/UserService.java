package com.example.rbacdemo.service;

import com.example.rbacdemo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

	/**
	 * 注册新用户
	 * @param username 用户名
	 * @param email 邮箱
	 * @param password 密码
	 * @return 注册成功的用户信息
	 */
	User registerUser(String username, String email, String password);

	/**
	 * 根据用户名查找用户
	 * @param username 用户名
	 * @return 用户信息
	 */
	User findByUsername(String username);

	/**
	 * 获取所有用户
	 * @return 用户列表
	 */
	List<User> findAllUsers();

	/**
	 * 根据ID查找用户
	 * @param id 用户ID
	 * @return 用户信息
	 */
	User findUserById(Long id);

	/**
	 * 为用户分配角色
	 * @param userId 用户ID
	 * @param roleIds 角色ID集合
	 */
	void assignRolesToUser(Long userId, Set<Long> roleIds);

}