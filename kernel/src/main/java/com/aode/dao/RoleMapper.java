package com.aode.dao;

import com.aode.dto.Role;
import com.aode.dto.RolePermission;

public interface RoleMapper {

	public Integer createRole(Role role);

	public void updateRole(Role role);
	
	public void deleteRoleByRoleId(Integer roleId);

	// 添加角色-权限之间关系
	public void correlationPermissions(RolePermission rolePermission);

	// 移除角色-权限之间关系
	public void uncorrelationPermissions(RolePermission rolePermission);
}
