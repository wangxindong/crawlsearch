package com.jianong.util.ftp;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 创建ftp连接工厂
 * 
 * @ClassName: FtpClientFactory
 * @Description:
 * @author: wxd
 * @date: 2016年12月15日 下午12:38:31
 */
public class FtpClientFactory implements PooledObjectFactory<FTPClient> {
	private static final Logger logger = LoggerFactory.getLogger(FtpClientFactory.class);
	private static FTPClient ftpClient;
	private volatile static FtpClientFactory factory;
	private static String url;
	private static String port;
	private static String username;
	private static String passwd;
	private static int timeout;

	static {
		ResourceBundle reource = ResourceBundle.getBundle("ftp");
		url = reource.getString("ftp.host");
		port = reource.getString("ftp.port");
		username = reource.getString("ftp.username");
		passwd = reource.getString("ftp.passwd");
		timeout = Integer.parseInt(reource.getString("ftp.timeout"));
	}

	private FtpClientFactory() {

	}

	public synchronized static FtpClientFactory getInstance() {
		if (null == factory) {
			synchronized (FtpClientFactory.class) {
				factory = new FtpClientFactory();
			}
		}
		return factory;
	}

	public FTPClient getFTPClient() {
		try {
			ftpClient = new FTPClient();
			ftpClient.setControlEncoding("utf-8");
			ftpClient.setDefaultPort(Integer.parseInt(port));
			ftpClient.connect(url);
			ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
			ftpClient.login(username, passwd);
			ftpClient.setDataTimeout(timeout);
			ftpClient.changeWorkingDirectory("/");
			logger.error("创建ftp连接" + ftpClient.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ftp连接失败" + e);
		}
		return ftpClient;
	}

	@Override
	public PooledObject<FTPClient> makeObject() throws Exception {
		FTPClient client = getFTPClient();
		return new DefaultPooledObject<FTPClient>(client);
	}

	/**
	 * 销毁连接
	 * 
	 * @Title: destroyObject
	 * @Description:
	 * @param p
	 * @throws Exception
	 * @see org.apache.commons.pool2.PooledObjectFactory#destroyObject(org.apache.commons.pool2.PooledObject)
	 */
	@Override
	public void destroyObject(PooledObject<FTPClient> p) {
		FTPClient client = p.getObject();
		if (client != null) {
			try {
				client.logout();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("销毁连接出现问题", e);
			} finally {
				try {
					client.disconnect();
					logger.info("释放连接");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean validateObject(PooledObject<FTPClient> client) {
		try {
			logger.info("发送ftp心跳", client.getObject().getStatus());
			return client.getObject().sendNoOp();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("验证连接出现错误: ", e);
		}
	}

	/**
	 * 初始化从池中取去的ftp连接
	 * 
	 * @Title: activateObject
	 * @Description:
	 * @param p
	 * @throws Exception
	 * @return: void
	 */
	@Override
	public void activateObject(PooledObject<FTPClient> p) throws Exception {
		FTPClient ftp = p.getObject();
		ftp.logout();
		//
		ftp.setControlEncoding("utf-8");
		ftp.setDefaultPort(Integer.parseInt(port));
		ftp.connect(url);
		ftp.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
		ftp.login(username, passwd);
	}

	@Override
	public void passivateObject(PooledObject<FTPClient> p) throws Exception {

	}

}
