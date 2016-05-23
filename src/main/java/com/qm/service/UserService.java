package com.qm.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qm.common.exception.CustomException;
import com.qm.common.utils.Common;
import com.qm.common.utils.MD5;
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
}
