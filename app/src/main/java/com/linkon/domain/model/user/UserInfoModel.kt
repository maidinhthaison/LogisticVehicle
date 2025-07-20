package com.linkon.domain.model.user

import java.io.Serializable

data class UserInfoModel(
    val code: Int? = null,
    val msg: String? = null,
    val data : UserInfoDataModel? = null
) : Serializable {

}


data class UserInfoDataModel(
    val tenantId: String? = null,
    val userId: String? = null,
    val deptId: Int? = null,
    val deptCategory: String? = null,
    val deptName: String? = null,
    val token: String? = null,
    val userType: String? = null,
    val loginTime: String? = null,
    val expireTime: String? = null,
    val ipaddr: String? = null,
    val loginLocation: String? = null,
    val browser: String? = null,
    val os: String? = null,

    val menuPermission: List<MenuPermissionResponseItemModel>? = null,
    val rolePermission: List<RolePermissionResponseItemModel>? = null,

    val username: String? = null,
    val nickname: String? = null,
    val roles: List<RolesResponseItemModel>? = null,
    val posts: List<PostsResponseItemModel>? = null,

    val roleId: String? = null,
    val clientKey: String? = null,
    val deviceType: String? = null,
    val loginId: String? = null,

    ) : Serializable

data class MenuPermissionResponseItemModel(
    val id: Int? = null,
    val name: String? = null
)

data class RolePermissionResponseItemModel(
    val id: Int? = null,
    val name: String? = null
)

data class RolesResponseItemModel(
    val id: Int? = null,
    val name: String? = null
)

data class PostsResponseItemModel(
    val id: Int? = null,
    val name: String? = null,
)