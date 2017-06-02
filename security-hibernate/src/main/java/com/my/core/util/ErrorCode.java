package com.my.core.util;
/*
 * 错误编码
 * */
public class ErrorCode {
	
	//
	public static final int SUCCESS = 0;
	public static final int SYS_PROCESSING = 1;
	
	public static final int UNKNOW_ERROR = 99;
	//简单统一错误号
	public static final int ERROR = -1;
	
	public static final int NO_PERMISSION = 101;//没有权限
	public static final int SESSION_INVALID = 102;//session失效
	public static final int SERVER_ERROR = 103;//session失效
	
	//参数问题
	public static final int PARAMS_ERROR = 201;
	
	//用户数据错误
	public static final int USER_INFO_ERROR = 203;
	
	//数据问题
	public static final int DATA_ERROR = 301;
	public static final int FILE_NOT_FOUND = 302;

	//广告管理
	public static final int AD_IN_USE = 351;
	public static final int NO_ATTACH = 352;
	
	//http
	public static final int AUTH_INVILID = 401;
	public static final int ACCESS_DENIED = 403;
	
	//系统一些错误编号1200~1600
	public static final int HTTP_METHOD_NOT_EXISTS = 1200;
	
}
