package com.dataserve.migration.spga.objects;

import java.util.List;

public class InbasketData {
    String departmentQueueName;
    String inBasketName;
    String roleName;
    String departmentId;
    List<String> roleUsersIds;

    public InbasketData(String departmentQueueName, String inBasketName, String roleName, String departmentId, List<String> roleUsersIds) {
        this.departmentQueueName = departmentQueueName;
        this.inBasketName = inBasketName;
        this.roleName = roleName;
        this.departmentId = departmentId;
        this.roleUsersIds = roleUsersIds;
    }

    public String getDepartmentQueueName() {
        return departmentQueueName;
    }

    public void setDepartmentQueueName(String departmentQueueName) {
        this.departmentQueueName = departmentQueueName;
    }

    public String getInBasketName() {
        return inBasketName;
    }

    public void setInBasketName(String inBasketName) {
        this.inBasketName = inBasketName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public List<String> getRoleUsersIds() {
        return roleUsersIds;
    }

    public void setRoleUsersIds(List<String> roleUsersIds) {
        this.roleUsersIds = roleUsersIds;
    }
}
