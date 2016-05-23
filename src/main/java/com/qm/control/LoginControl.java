package com.qm.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qm.common.domain.ResponseResult;
import com.qm.domain.entity.BdUser;
import com.qm.service.UserService;
import com.qm.web.utils.JsonViewFactory;

/**
 * @Description: 登陆Controller类
 * @author qiming
 * @date : 2016年5月20日 下午2:31:04
 */
@Controller
@RequestMapping(value = "/login")
public class LoginControl {

	@Autowired
	private UserService userService;
	
	/**
	 * @Description: 登录验证
	* @author: qiming
	* @date: 2016年5月20日 下午2:35:35
	 */
	@RequestMapping(value = "/doCheckLogin_async", method = RequestMethod.POST)
	public ModelAndView doCheckLogin(HttpServletRequest request,@RequestBody BdUser user) throws Exception {
		Assert.notNull(user, "用户信息不能为空！");
		userService.doCheckLogin(request, user);
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "验证成功！"));
	}
	
	/**
	 * @Description: 添加用户
	* @author: qiming
	* @date: 2016年5月20日 下午5:19:17
	 */
	@RequestMapping(value = "/saveUser_async")
	public ModelAndView saveUser(HttpServletRequest request, @RequestBody BdUser user) throws Exception {
		Assert.notNull(user, "用户信息不能为空！");
		userService.saveUser(request, user);
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "添加成功！"));
	}
	
}
