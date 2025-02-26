package com.example.rbacdemo.controller;

import com.example.rbacdemo.model.User;
import com.example.rbacdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 获取当前认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 获取用户详细信息
        User user = userService.findByUsername(username);

        // 获取权限信息
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        // 将权限按类型分组（ROLE_和普通权限）
        Map<String, Collection<String>> groupedAuthorities = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.groupingBy(auth -> 
                    auth.startsWith("ROLE_") ? "roles" : "permissions",
                    HashMap::new,
                    Collectors.toCollection(java.util.TreeSet::new)
                ));

        // 添加到模型
        model.addAttribute("user", user);
        model.addAttribute("roles", groupedAuthorities.get("roles"));
        model.addAttribute("permissions", groupedAuthorities.get("permissions"));
        model.addAttribute("isAuthenticated", authentication.isAuthenticated());
        model.addAttribute("principal", authentication.getPrincipal());

        return "dashboard";
    }
} 