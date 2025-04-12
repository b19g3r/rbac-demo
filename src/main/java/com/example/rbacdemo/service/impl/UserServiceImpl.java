package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.model.Role;
import com.example.rbacdemo.model.User;
import com.example.rbacdemo.repository.RoleRepository;
import com.example.rbacdemo.repository.UserRepository;
import com.example.rbacdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(String username, String email, String password) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(password));
		user.setRoles(new HashSet<>());
		return userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("用户不存在: " + username));
	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("用户不存在: ID=" + id));
	}

	@Override
	@Transactional
	public void assignRolesToUser(Long userId, Set<Long> roleIds) {
		User user = findUserById(userId);
		Set<Role> roles = roleIds.stream()
			.map(roleId -> roleRepository.findById(roleId)
				.orElseThrow(() -> new RuntimeException("角色不存在: ID=" + roleId)))
			.collect(Collectors.toSet());

		user.setRoles(roles);
		userRepository.save(user);
	}

}