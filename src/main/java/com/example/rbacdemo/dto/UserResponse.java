package com.example.rbacdemo.dto;

import com.example.rbacdemo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

	private Long id;

	private String username;

	private String email;

	private Set<Role> roles;

}