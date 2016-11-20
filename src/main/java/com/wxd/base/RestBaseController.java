package com.wxd.base;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.jianong.entity.Page;
import com.jianong.util.Logger;
import com.jianong.util.PageData;
import com.jianong.util.UuidUtil;

/**
 * 用于前台使用
 * 
 * @author 
 *
 */
public class RestBaseController implements Serializable {
	protected Logger logger = Logger.getLogger(this.getClass());
	private static String sign = null;

	@Resource(name = "rest")
	private RestTemplate restTemplate;

	private static final long serialVersionUID = 6357869213649815390L;

	/**
	 * 生成sign算法 get/post+api_uri+date时间戳+length(发送body的数据长度)+password(app秘钥)
	 * 使用md5摘要一次md5(sign) 拼接用户名 sign = username : sign
	 * 
	 * @return
	 */
	public static String makeSign() {
		return null;
	}

	/**
	 * 获取sign
	 * 
	 * @return
	 */
	public static String getSign() {
		return null;
	}
	
	/**
	 * 从sign中获取时间阈值
	 * @param sign
	 * @return
	 */
	public static String getDateBySign(String sign){
		return null;
	}

	/**
	 * new PageData对象
	 * 
	 * @return
	 */
	public PageData getPageData() {
		return new PageData(this.getRequest());
	}

	/**
	 * 得到ModelAndView
	 * 
	 * @return
	 */
	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	/**
	 * 得到request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	/**
	 * 得到32位的uuid
	 * 
	 * @return
	 */
	public String get32UUID() {
		return UuidUtil.get32UUID();
	}

	/**
	 * 得到分页列表的信息
	 * 
	 * @return
	 */
	public Page getPage() {
		return new Page();
	}

	public static void logBefore(Logger logger, String interfaceName) {
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}

	public static void logAfter(Logger logger) {
		logger.info("end");
		logger.info("");

	}
}
