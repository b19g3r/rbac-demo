package com.example.rbacdemo.controller;

import com.example.rbacdemo.dto.LoginRequest;
import com.example.rbacdemo.dto.LoginResponse;
import com.example.rbacdemo.dto.RegisterRequest;
import com.example.rbacdemo.dto.UserResponse;
import com.example.rbacdemo.model.User;
import com.example.rbacdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;

	private final AuthenticationManager authenticationManager;

	/**
	 * 用户注册API
	 * @param request 注册请求信息
	 * @return 注册成功的用户信息
	 */
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
		User user = userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());

		UserResponse response = UserResponse.builder()
			.id(user.getId())
			.username(user.getUsername())
			.email(user.getEmail())
			.roles(user.getRoles())
			.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * 用户登录API
	 * @param request 登录请求信息
	 * @return 登录成功的令牌和用户信息
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		Authentication authentication = authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = userService.findByUsername(request.getUsername());

		LoginResponse response = LoginResponse.builder()
			.userId(user.getId())
			.username(user.getUsername())
			.email(user.getEmail())
			.message("登录成功")
			.build();

		return ResponseEntity.ok(response);
	}

}