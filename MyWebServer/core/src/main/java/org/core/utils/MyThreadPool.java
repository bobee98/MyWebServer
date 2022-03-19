package org.core.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类似于代理？装饰器（这两个一般也是合在一起用）；给ThreadPoll一些新功能
 * @author bobee
 *
 */
public class MyThreadPool {
	private final ExecutorService exe;
	
	public MyThreadPool(int nThreads) {
		exe = Executors.newFixedThreadPool(nThreads);
	}
	/**
	 * 此时的submit方法还不是代理的Threadpoll的submit，而是execute()；
	 * 等有返回结果的需要再使用真的submit()
	 */
	public void submit(Runnable task) {
		exe.execute(task);
	}
	
}
