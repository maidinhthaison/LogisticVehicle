package com.linkon.data.api.response.user

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.linkon.domain.model.user.MenuPermissionResponseItemModel
import com.linkon.domain.model.user.PostsResponseItemModel
import com.linkon.domain.model.user.RolePermissionResponseItemModel
import com.linkon.domain.model.user.RolesResponseItemModel
import com.linkon.domain.model.user.UserInfoDataModel
import com.linkon.domain.model.user.UserInfoModel
import java.io.Serializable

@Keep
data class UserInfoResponseDto (
    @SerializedName("code") val code: Int?,
    @SerializedName("msg") val msg: String?,
    @SerializedName("data") val data: UserInfoDataResponseDto?
): Serializable {
    fun toModel(): UserInfoModel {
        return UserInfoModel(
            code = code,
            msg = msg,
            data = data?.toModel()
        )
    }
}

@Keep
data class UserInfoDataResponseDto (
    @SerializedName("tenantId") val tenantId: String?,
    @SerializedName("userId") val userId: String?,
    @SerializedName("deptId") val deptId: Int?,
    @SerializedName("deptCategory") val deptCategory: String?,
    @SerializedName("deptName") val deptName: String?,
    @SerializedName("token") val token: String?,
    @SerializedName("userType") val userType: String?,
    @SerializedName("loginTime") val loginTime: String?,

    @SerializedName("expireTime") val expireTime: String?,
    @SerializedName("ipaddr") val ipaddr: String?,
    @SerializedName("loginLocation") val loginLocation: String?,
    @SerializedName("browser") val browser: String?,
    @SerializedName("os") val os: String?,
    @SerializedName("menuPermission") val menuPermission: List<MenuPermissionResponseItemDto>?,
    @SerializedName("rolePermission") val rolePermission: List<RolePermissionResponseItemDto>?,
    @SerializedName("username") val username: String?,
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("roles") val roles: List<RolesResponseItemDto>?,
    @SerializedName("posts") val posts: List<PostsResponseItemDto>?,
    @SerializedName("roleId") val roleId: String?,
    @SerializedName("clientKey") val clientKey: String?,
    @SerializedName("deviceType") val deviceType: String?,
    @SerializedName("loginId") val loginId: String?

): Serializable {
    fun toModel(): UserInfoDataModel {
        return UserInfoDataModel(
            tenantId = tenantId,
            userId = userId,
            deptId = deptId,
            deptCategory = deptCategory,
            deptName = deptName,
            token = token,
            userType = userType,
            loginTime = loginTime,
            expireTime = expireTime,
            ipaddr = ipaddr,
            loginLocation = loginLocation,
            browser = browser,
            os = os,
            menuPermission = menuPermission?.map { it.toModel() },
            rolePermission = rolePermission?.map { it.toModel() },
            username = username,
            nickname = nickname,
            roles = roles?.map { it.toModel() },
            posts = posts?.map { it.toModel() },
            roleId = roleId,
            clientKey = clientKey,
            deviceType = deviceType,
            loginId = loginId
        )
    }
}

@Keep
data class MenuPermissionResponseItemDto (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
): Serializable{
    fun toModel() : MenuPermissionResponseItemModel  {
        return MenuPermissionResponseItemModel(
            id = id,
            name = name
        )
    }
}

@Keep
data class RolePermissionResponseItemDto (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
): Serializable{
    fun toModel() : RolePermissionResponseItemModel  {
        return RolePermissionResponseItemModel(
            id = id,
            name = name
        )
    }
}

@Keep
data class RolesResponseItemDto (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
): Serializable{
    fun toModel() : RolesResponseItemModel  {
        return RolesResponseItemModel(
            id = id,
            name = name
        )
    }
}

@Keep
data class PostsResponseItemDto (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
): Serializable{
    fun toModel() : PostsResponseItemModel {
        return PostsResponseItemModel(
            id = id,
            name = name
        )
    }
}
