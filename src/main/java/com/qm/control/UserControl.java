package com.qm.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qm.common.domain.ResponseResult;
import com.qm.domain.entity.BdUser;
import com.qm.service.UserService;
import com.qm.web.utils.JsonViewFactory;

@Controller
@RequestMapping(value = "/user")
public class UserControl {

	@Autowired
	private UserService userService;
	
	/**
	 * 
	* @Description: TODO
	* @param @param request
	* @param @return
	* @return ModelAndView
	* @throws 
	* @author qiming 
	* @date 2016年5月18日 下午11:41:20
	 */
	@RequestMapping(value = "/addUser")
	public ModelAndView addUser(HttpServletRequest request) {
		BdUser user = new BdUser();
		user.setUsername("18072940495");
		user.setName("青木");
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "添加成功！", userService.insertSelective(user)));
	}
}
