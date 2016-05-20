package com.qm.common.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/** 
 * @ClassName: UserCache 
 * @Description: 单利模式缓存用户登录信息
 * @author caizhen
 * @date 2015年10月26日 上午11:05:18  
 */
public class UserCache {
	
	 protected static final HashMap map = new HashMap();//缓存登录用户信息
	 
	 private static final Object lock = new Object(); 
	 
     private UserCache(){}
     public static Object getData(Object key){  
             Object o = map.get(key);  
             if(o==null){  
                   synchronized(lock){    
                   o = map.get(key);  
                   }  
             }  
             return o;  
     }
     /**
      * @Title: setUser 
      * @Description: 将用户信息放入缓存
      * @author caizhen   
      * @param @param key  BdUser对象的ID
      * @param @param value    BdUser对象
      */
    public static void setUser(Object key,Object value){
		Object o = map.get(key);
		//map中无此用户，则存入
		if(o==null){
			map.put(key, value);  
		}
    } 
    /**
     * 
    * @Title: updateUser 
    * @Description: 重置缓存中用户信息
    * @param @param key
    * @param @param value
    * @return void
    * @throws
     */
    public static void updateUser(Object key,Object value){
		map.put(key, value);  
    } 
    /**
     * @Title: loginOut 
     * @Description: 清除map中指定对象
     * @author caizhen   
     * @param @param key   BdUser对象的ID  
     */
    public static void loginOut(Object key){
  		if(key!=null){
  			map.remove(key);
  		}
    }
    /**
     * @Title: loginOut 
     * @Description: 清除map中指定对象
     * 				 清空session中的用户信息
     * @author caizhen   
     * @param @param key    BdUser对象的ID 
     * @param @param session    设定文件 
     */
    public static void loginOut(Object key,HttpSession session){
    	session.removeAttribute(Common.SESSION_USER);
  		if(key!=null){
  			map.remove(key);
  		}
   }
}
