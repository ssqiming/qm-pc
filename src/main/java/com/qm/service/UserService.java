package com.qm.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.common.exception.CustomException;
import com.qm.common.utils.Common;
import com.qm.common.utils.MD5;
import com.qm.common.utils.UserCache;
import com.qm.dao.BdUserDao;
import com.qm.domain.entity.BdUser;

/**
 * 
 * @Description: 用户服务类
 * @author qiming
 * @date : 2016年5月20日 下午2:30:45
 */
@Service
public class UserService {

	@Autowired
	private BdUserDao userDao;
	
	/**
	 * 
	* @Description: 添加用户
	* @author: qiming
	* @date: 2016年5月20日 下午2:34:28
	 */
	public int insertSelective(BdUser user) {
		return userDao.insertSelective(user);
	}
	
	/**
	 * @Description: 添加用户
	* @author: qiming
	* @date: 2016年5月20日 下午5:13:15
	 */
	public BdUser saveUser(HttpServletRequest request, BdUser user) {
		BdUser bduser = new BdUser();
		if(user == null) {
			throw new CustomException("用户信息不能为空！");
		}
		if(StringUtils.isNotBlank(user.getUsername())) {
			bduser = userDao.getUser(new BdUser(null, user.getUsername()));
		}
		if(bduser == null || StringUtils.isBlank(bduser.getUsername())) {
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			userDao.insertSelective(user);
			addUserTOSession(request, user);
		} else {
			throw new CustomException("该用户已存在！");
		}
		return user;
	}
	
	/**
	 * 
	* @Description: 登陆验证
	* @author: qiming
	* @date: 2016年5月20日 下午2:34:42
	 */
	public void doCheckLogin(HttpServletRequest request, BdUser user) {
		String volidateSession = (String) request.getSession().getAttribute("randCheckCode");
		if(StringUtils.isNotEmpty(user.getUsername()) && StringUtils.isNotEmpty(user.getPwd())) {
			BdUser bduser = userDao.getUser(new BdUser(null, user.getUsername()));
			if(bduser != null) {
				String password = bduser.getPwd();
				if(volidateSession != null && volidateSession.equals(user.getCheckCode())) {
					if(MD5.md5(user.getPwd()).equals(password)) {
						addUserTOSession(request, bduser);					
					} else {
						throw new CustomException("密码错误！");
					}
				} else {
					throw new CustomException("请输入正确的验证码！");
				}
			} else {
				throw new CustomException("用户不存在或无权限！");
			}
		} else {
			throw new CustomException("提交信息不完整！");
		}
	}
	
	/**
	* @Description: 将用户信息存入session
	* @author: qiming
	* @date: 2016年5月20日 下午2:34:56
	 */
	public void addUserTOSession(HttpServletRequest request, BdUser user) {
		request.getSession().setAttribute(Common.SESSION_USER, user);
	}
	
	/**
	 * @Description: 修改当前登录账号信息
	* @author: qiming
	* @date: 2016年5月23日 下午5:02:42
	 */
	public String updateUser(HttpServletRequest request, BdUser user) {
		BdUser sUser=(BdUser)request.getAttribute("user_cache");
		user.setId(sUser.getId());
		user.setUpdateTime(new Date());
		userDao.updateByPrimaryKeySelective(user);
		//修改session及map中保存的用户信息
		user = userDao.selectByPrimaryKey(sUser.getId());
		HttpSession session = request.getSession();
		session.setAttribute(Common.SESSION_USER, user);
		UserCache.updateUser(user.getId(), user);
		return user.getId();
	}
	
	/**
	 * @Description: 修改账号信息
	* @author: qiming
	* @date: 2016年5月23日 下午5:25:22
	 */
	public int updateByPrimaryKeySelective(BdUser user) {
		return userDao.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * @Description: 退出登录
	* @author: qiming
	* @date: 2016年5月23日 下午5:04:05
	 */
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
        session.removeAttribute(Common.SESSION_USER);
        session.removeAttribute("userAccount");
	} 
	
	/**
	 * @Description: 退出登录异步接口
	* @author: qiming
	* @date: 2016年5月23日 下午5:05:39
	 */
	public void logout_async(HttpServletRequest request) {
		BdUser user=(BdUser)request.getAttribute("user_cache");
		UserCache.loginOut(user==null?null:user.getId(), request.getSession());
	}
	
	/**
	 * @Description: 修改密码
	* @author: qiming
	* @date: 2016年5月23日 下午5:15:25
	 */
	public String saveModifyPwd(BdUser user,HttpServletRequest request) {
		HttpSession session = request.getSession(true);
        BdUser userSession=(BdUser)session.getAttribute(Common.SESSION_USER);
        if(!MD5.md5(user.getOldPwd()).equals(userSession.getPwd()))
        	throw new CustomException("原密码输入有错误!");
		if (user.getPwd().length() < 6) {
			throw new CustomException("密码长度必须大于六位！");
		}
        user.setPwd(MD5.md5(user.getPwd()));
        user.setId(userSession.getId());
        String str=updateUser(request, user);
        session.removeAttribute(Common.SESSION_USER);
        session.removeAttribute("userAccount");
        return str;
	}
	
	/**
	 * @Description: 修改密码异步接口
	* @author: qiming
	* @date: 2016年5月23日 下午5:15:58
	 */
	public String saveModifyPwd_async(BdUser user,HttpServletRequest request) {
		BdUser cacheUser=(BdUser)request.getAttribute("user_cache");
		if(!MD5.md5(user.getOldPwd()).equals(cacheUser.getPwd()))
        	throw new CustomException("原密码输入有问题!");
        user.setPwd(MD5.md5(user.getPwd()));
        user.setId(cacheUser.getId());
        String str=updateUser(request, user);
        UserCache.loginOut(cacheUser.getId(), request.getSession());
        return str;
	}
}
