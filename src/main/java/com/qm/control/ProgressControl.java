package com.qm.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.qm.common.domain.ResponseResult;
import com.qm.common.exception.CustomException;
import com.qm.task.Progress;
import com.qm.web.utils.JsonViewFactory;

/**
 * @Description: 进度条测试Control
 * @author qiming
 * @date : 2016年9月26日 上午11:48:08
 */
@Controller
public class ProgressControl {

	/**
	 * @Description: 启动进度线程
	* @author: qiming
	* @date: 2016年9月26日 上午11:59:59
	 */
	@RequestMapping(value = "/progress/startProgress_async", method = RequestMethod.GET)
	public ModelAndView startProgress(HttpServletRequest request) {
		Progress p = (Progress) request.getSession().getAttribute("progress");
		if(p != null) {
			System.out.println("Thread Running!");
	        throw new CustomException("线程被占用!");
		} else {
			Progress progress = new Progress();
	        request.getSession().setAttribute("progress", progress);
	        progress.start();
		}
		return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "线程启动成功！"));
	}
	
	/**
	 * @Description: 获取进度
	* @author: qiming
	* @date: 2016年9月26日 下午1:52:27
	 */
	@RequestMapping(value = "/progress/getProgress_async", method = RequestMethod.GET)
	public ModelAndView getProgress(HttpServletRequest request) {
		Progress p = (Progress) request.getSession().getAttribute("progress");
		 String percent = null;
		if (p != null) {
	        percent = p.getPercent();
	        if(p.isOver()){
		        request.getSession().removeAttribute("progress");
		    }
	    }
	    
	    return JsonViewFactory.buildJsonView(new ResponseResult<>(true, "请求成功！", percent));
	}
}
