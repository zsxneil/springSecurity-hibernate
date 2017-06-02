package com.my.core.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.my.core.Constants;

import net.sf.json.JSONObject;
import net.sf.json.JSONNull;
import java.util.Iterator;
import java.util.List;

public class CommonUtils {
	public static String format(){
		return format("yyyy-MM-dd HH:mm:ss",new Date());
	}
	public static String format(String format){
		return format(format,new Date());
	}
	public static String format(String format,Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	public static String format(int timestamp){
		long time = (long)timestamp * 1000;
		return format("yyyy-MM-dd HH:mm:ss",new Date(time));
	}
	public static Date string2date(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.parse(date);
	}
	public static Date string2date(String format, String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	public static int timestamp(long time){
		return (int)(time/1000);
	}
	public static int timestamp(){
		return timestamp(System.currentTimeMillis());
	}
	public static String random(int minLen,int maxLen){
		String _configRandStrs = PptLoader.getPptValue("config", "random.strs");
		String realRandStrs = _configRandStrs == null ? "qwertyuiopasdfghjklzxcvbnmZXCVBNMASDFGHJKLQWERTYUIOP1234567890":_configRandStrs;
		StringBuffer buff = new StringBuffer();
		Random r = new Random();
		int len = minLen + r.nextInt(maxLen - minLen);
		int randStrsLen = realRandStrs.length();
		for(int i = 0;i < len;i++){
			buff.append(realRandStrs.charAt(r.nextInt(randStrsLen)));
		}
		return buff.toString();
	}
	public static int zeroTimestamp(){
		return zeroTimestamp(timestamp());
	}
	public static int zeroTimestamp(int timestamp){
		timestamp += Constants.BEIJING_TIME_DELTA;
		return timestamp - (timestamp%Constants.DAY_SECONDS) - Constants.BEIJING_TIME_DELTA;
	}
	public static String sha(String ...data){
		String result = "";
		Arrays.sort(data);
		try {
			result = DigestUtils.sha1Hex((StringUtils.join(data).getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 空判断
	 * @author 头像IS熊
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null || "".equals(str))
			return true;
		return false;
		
	}
	
	public static JSONObject jsonResult(int errCode,String msg,Object data){
		JSONObject obj = new JSONObject();
		if(errCode == ErrorCode.SUCCESS){
			obj.accumulate("status","success");
		}else{
			obj.accumulate("status", "failure");
		}
		obj.accumulate("errCode", errCode);
		obj.accumulate("errMsg", msg);
		if(data != null){
			obj.accumulate("data", data);
		}
		return obj;
	}
	
	public static Object jsonResult2(int errCode,String msg,Object data){
		Map obj = new HashMap<String, Object>();
		if(errCode == ErrorCode.SUCCESS){
			obj.put("status","success");
		}else{
			obj.put("status", "failure");
		}
		obj.put("errCode", errCode);
		obj.put("errMsg", msg);
		if(data != null){
			obj.put("data", data);
		}
		return com.alibaba.fastjson.JSON.toJSON(obj);
	}
	
	public static void jsonResult(int errCode,HttpServletResponse response){
		JSONObject obj = jsonResult(errCode);
		try {
			PrintWriter out = response.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static JSONObject jsonResult(int errCode){
		return jsonResult(errCode,ErrorMsg.errMsg(errCode),null);
	}
	
	public static JSONObject jsonResult(int errCode,Object data){
		return jsonResult(errCode,ErrorMsg.errMsg(errCode),data);
	}
	
	public static boolean jsonCheckNull(JSONObject obj,List<String> list){
		boolean result = false;
		do{
			if(obj == null || list == null || list.size() == 0){
				break;
			}
			Iterator<String> it= obj.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				if(list.contains(key)){
					Object test = obj.get(key);
					if(test instanceof JSONNull){
						result = true;
						break;
					}
				}
			}
		}while(false);
		return result;
	}
	
	public static boolean jsonCheckNull(JSONObject obj,String[] list){
		if(list != null && list.length > 0){
			return jsonCheckNull(obj, Arrays.asList(list));
		}
		return false;
	}
	
	public static Object jsonResult2(int errCode,Object data){
		return jsonResult2(errCode,ErrorMsg.errMsg(errCode),data);
	}
	
	
	public static String join(List<String> list,String prefix,String split,String suffix){
		StringBuffer buff = new StringBuffer();
		buff.append(prefix);
		buff.append(join(list,split));
		buff.append(suffix);
		return buff.toString();
	}
	
	public static String join(List<String> list,String split){
		return _join(list,split).toString();
	}
	
	public static StringBuffer _join(List<String> list,String split){
		StringBuffer result = new StringBuffer();
		if(list != null && list.size() > 0){
			for(String tmp : list){
				if(tmp == null || tmp.length() == 0){
					continue;
				}
				result.append(tmp);
				result.append(split);
			}
		}
		if(result.length() > 1){
			result.setLength(result.length() - split.length());
		}
		return result;
	}
	
}
