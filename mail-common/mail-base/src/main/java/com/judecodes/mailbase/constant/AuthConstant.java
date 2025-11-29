package com.judecodes.mailbase.constant;

public interface AuthConstant {

    /**
     * 后台管理client_id
     */
    String STP_IDENTITY_TYPE = "identity-type";

    String ADMIN_ROLE_INFO = "admin-info";

    String ADMIN_PERMISSION_LIST = "admin-permission";
    /**
     * 前台商城client_id
     */
    String PORTAL_CLIENT_ID = "portal-app";

    /**
     * Redis缓存权限规则（路径->资源）
     */
    String PATH_RESOURCE_MAP = "auth:pathResourceMap";


}
