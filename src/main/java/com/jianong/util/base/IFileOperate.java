package com.jianong.util.base;

import org.springframework.web.multipart.MultipartFile;

/**
 * ftp处理的接口
 * 
 * @ClassName: IFtp
 * @Description:
 * @author: wxd
 * @date: 2016年12月15日 上午10:28:25
 */
public interface IFileOperate {

	/**
	 * 上传图片
	 * 
	 * @Title: upload
	 * @Description:
	 * @param img
	 * @return
	 * @throws Exception
	 * @return: String 存放的位置
	 */
	public String upload(MultipartFile img, String dir) throws Exception;
	
	public String upload(Object obj, String dir) throws Exception;
	
	public void delete(String name) throws Exception;
	
	public byte[] fileDown(String path) throws Exception;

}
