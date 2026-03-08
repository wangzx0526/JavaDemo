package com.zane.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.zane.ISysMenuService;
import com.zane.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final ISysMenuService sysMenuService;
    private final SysRoleMapper sysRoleMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Long userId = parseLoginId(loginId);
        return sysMenuService.getPermsByUserId(userId);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = parseLoginId(loginId);
        return sysRoleMapper.getRoleCodesByUserId(userId);
    }

    private Long parseLoginId(Object loginId) {
        if (loginId == null) {
            return null;
        }
        try {
            return Long.valueOf(loginId.toString());
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
