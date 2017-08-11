package com.aode.dao;

import com.aode.dto.Permission;

public interface PermissionMapper {

	public Integer createPermission(Permission permission);

	public void deletePermission(Integer permissionId);
	
	public void updatePermission(Permission permission);
}
