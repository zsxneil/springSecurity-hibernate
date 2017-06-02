package com.my.core.util;

import java.util.HashMap;
import java.util.Map;

public class ErrorMsg {
	private static Map<Integer,String> msgs = new HashMap<Integer, String>();
	static{
		msgs.put(ErrorCode.SUCCESS, "操作成功");
		msgs.put(ErrorCode.NO_PERMISSION, "权限不足");
		msgs.put(ErrorCode.SESSION_INVALID, "会话超时");
		msgs.put(ErrorCode.SERVER_ERROR, "服务器错误");
		msgs.put(ErrorCode.PARAMS_ERROR, "参数错误");
		msgs.put(ErrorCode.DATA_ERROR, "数据错误");
		msgs.put(ErrorCode.HTTP_METHOD_NOT_EXISTS, "HTTP请求方式不存在");
		msgs.put(ErrorCode.FILE_NOT_FOUND, "文件不存在");
		msgs.put(ErrorCode.SYS_PROCESSING, "系统繁忙");
		msgs.put(ErrorCode.UNKNOW_ERROR, "未知错误");
		msgs.put(ErrorCode.AUTH_INVILID, "无效的凭证");
		msgs.put(ErrorCode.ACCESS_DENIED, "访问被拒绝");
		
		
		
		msgs.put(ErrorCode.AD_IN_USE, "广告已启用，无法删除！");
		msgs.put(ErrorCode.NO_ATTACH, "无关联附件");
		
		
	}
	public static String errMsg(int errCode){
		if(msgs.containsKey(errCode)){
			return msgs.get(errCode);
		}
		return String.valueOf(errCode);
	}
}
