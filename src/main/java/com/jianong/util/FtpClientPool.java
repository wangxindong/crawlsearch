package com.jianong.util.ftp;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

@SuppressWarnings("all")
public class FtpClientPool implements ObjectPool<FTPClient> {
	private static final int DEFAULT_POOL_SIZE = 10;
	// 使用队列存储ftp连接
	private volatile BlockingQueue<PooledObject<FTPClient>> pool;
	private static FtpClientFactory factory;
	// 正在使用的
	private static int i = 0;

	private void initPool(int poolSize) throws Exception {
		for (int j = 0; j < poolSize; j++) {
			addObject();
		}
	}

	public FtpClientPool() throws Exception {
		this(DEFAULT_POOL_SIZE, factory);
	}

	public FtpClientPool(int poolSize, FtpClientFactory factory) throws Exception {
		this.factory = factory;
		pool = new ArrayBlockingQueue(poolSize);
		initPool(poolSize);
	}

	/**
	 * 客户端从池中取出一个对象
	 * 
	 * @Title: borrowObject
	 * @Description:
	 * @return
	 * @throws Exception
	 * @throws NoSuchElementException
	 * @throws IllegalStateException
	 * @see org.apache.commons.pool2.ObjectPool#borrowObject()
	 */
	@Override
	public synchronized FTPClient borrowObject() throws Exception, NoSuchElementException, IllegalStateException {
		// FTPClient client = pool.take().getObject();
		PooledObject<FTPClient> def = pool.take();
		if (def == null) {
			addObject();
		} else if (!factory.validateObject(def)) {
			invalidateObject(def.getObject());
			def = factory.makeObject();
		}
		i++;
		return def.getObject();
	}

	/**
	 * 将连接归还一个对象到池中
	 * 
	 * @Title: returnObject
	 * @Description:
	 * @param obj
	 * @throws Exception
	 * @see org.apache.commons.pool2.ObjectPool#returnObject(java.lang.Object)
	 */
	@Override
	public void returnObject(FTPClient obj) throws Exception {
		PooledObject<FTPClient> client = new DefaultPooledObject<FTPClient>(obj);
		factory.destroyObject(client);
		addObject();
	}

	/**
	 * 清除无效的连接
	 * 
	 * @Title: invalidateObject
	 * @Description:
	 * @param obj
	 * @throws Exception
	 * @see org.apache.commons.pool2.ObjectPool#invalidateObject(java.lang.Object)
	 */
	@Override
	public void invalidateObject(FTPClient obj) throws Exception {
		PooledObject<FTPClient> client = new DefaultPooledObject<FTPClient>(obj);
		if (!factory.validateObject(client)) {
			pool.remove(obj);
		}
	}

	/**
	 * 添加对象到池
	 * 
	 * @Title: addObject
	 * @Description:
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws UnsupportedOperationException
	 * @see org.apache.commons.pool2.ObjectPool#addObject()
	 */
	@Override
	public void addObject() throws Exception, IllegalStateException, UnsupportedOperationException {
		pool.offer(factory.makeObject(), 1000, TimeUnit.SECONDS);
	}

	@Override
	public int getNumIdle() {
		return pool.size() - i;
	}

	@Override
	public int getNumActive() {
		return i;
	}

	@Override
	public void clear() throws Exception, UnsupportedOperationException {

	}

	/**
	 * 清空池
	 * 
	 * @Title: close
	 * @Description:
	 * @see org.apache.commons.pool2.ObjectPool#close()
	 */
	@Override
	public void close() {
		for (PooledObject<FTPClient> client : pool) {
			factory.destroyObject(client);
		}
	}

}
