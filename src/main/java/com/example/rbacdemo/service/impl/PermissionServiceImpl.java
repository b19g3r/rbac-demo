package com.example.rbacdemo.service.impl;

import com.example.rbacdemo.model.Permission;
import com.example.rbacdemo.repository.PermissionRepository;
import com.example.rbacdemo.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission createPermission(String name) {
        Permission permission = new Permission();
        permission.setName(name);
        return permissionRepository.save(permission);
    }

    @Override
    public List<Permission> findAllPermissions() {
        return permissionRepository.findAll();
    }
} 