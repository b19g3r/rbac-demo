package com.example.rbacdemo.service;

import com.example.rbacdemo.model.Permission;

import java.util.List;

public interface PermissionService {

    /**
     * 创建新权限
     *
     * @param name 权限名称
     * @return 创建成功的权限信息
     */
    Permission createPermission(String name);

    /**
     * 获取所有权限
     *
     * @return 权限列表
     */
    List<Permission> findAllPermissions();
} 