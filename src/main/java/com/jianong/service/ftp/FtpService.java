package com.jianong.service.ftp;

import java.io.InputStream;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jianong.util.UuidUtil;
import com.jianong.util.base.IFileOperate;
import com.jianong.util.ftp.FtpClientFactory;
import com.jianong.util.ftp.FtpClientPool;

@SuppressWarnings("all")
@Service(value = "ftp")
public class FtpService implements IFileOperate {

	private Logger logger = LoggerFactory.getLogger(FtpService.class);
	private FTPClient client;
	private static FtpClientPool pool;
	private static int poolSize;
	private static boolean enabled;

	static {
		ResourceBundle reource = ResourceBundle.getBundle("ftp");
		enabled = Boolean.parseBoolean(reource.getString("ftp.enabled"));
		poolSize = Integer.parseInt(reource.getString("ftp.pool.size"));
	}

	/**
	 * 返回文件后缀名称
	 * 
	 * @Title: modifyFileName
	 * @Description:
	 * @param fileName
	 * @return
	 * @return: String[]
	 */
	private String modifyFileName(String fileName) {
		int position = fileName.indexOf('.');
		if (position > 0 && position < fileName.length()) {
			fileName = fileName.substring(position, fileName.length());
		}
		return fileName;
	}

	public FtpService() throws Exception {
		if (enabled) {
			pool = new FtpClientPool(poolSize, FtpClientFactory.getInstance());
		}
	}

	/**
	 * 上传文件
	 * 
	 * @Title: upload
	 * @Description:
	 * @param img
	 * @param dir
	 *            目录--公司uuid
	 * @return 上传之后的文件完整
	 * @throws Exception
	 * @see com.jianong.util.ftp.IFtpOperate#upload(org.springframework.web.multipart.MultipartFile,
	 *      java.lang.String)
	 */
	@Override
	public String upload(MultipartFile img, String dir) throws Exception {
		client = pool.borrowObject();
		boolean flag = client.makeDirectory(dir);
		client.changeWorkingDirectory(dir);
		String fileName = img.getOriginalFilename();
		String tmp = modifyFileName(fileName);
		String wordPath = client.printWorkingDirectory();
		String realName = UuidUtil.get32UUID() + tmp;
		byte[] b = img.getBytes();
		InputStream input = img.getInputStream();
		client.storeFile(realName, input);
		pool.returnObject(client);
		// 返回文件路径
		logger.info("上传文件成功", dir);
		return dir + "/" + realName;
	}

	@Override
	public String upload(Object obj, String dir) throws Exception {
		if (obj instanceof MultipartFile) {
			return upload((MultipartFile) obj, dir);
		}
		// 对别的类型的文件进行处理
		// TODO
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @Title: delete
	 * @Description:
	 * @param name
	 * @throws Exception
	 * @see com.jianong.util.ftp.IFtpOperate#delete(java.lang.String)
	 */
	@Override
	public void delete(String name) throws Exception {
		client = pool.borrowObject();
		client.deleteFile(name);
	}

	/**
	 * 下载
	 * 
	 * @Title: fileDown
	 * @Description:
	 * @param path
	 * @return
	 * @throws Exception
	 * @return: Object
	 */
	@Override
	public byte[] fileDown(String path) throws Exception {
		InputStream input = null;
		client = pool.borrowObject();
		String[] tmp = path.split("/");
		String wordDir = tmp[0];
		client.changeWorkingDirectory(wordDir);
		input = client.retrieveFileStream(tmp[1]);
		logger.info("下载文件成功", path);
		byte[] bs = IOUtils.toByteArray(input);
		input.close();
		pool.returnObject(client);
		return bs;
	}

}
