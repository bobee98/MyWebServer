package org.core;

import java.io.*;
import java.net.*;
import org.core.http.*;//我自己的路径
import org.core.utils.Logs;
import org.core.utils.MyThreadPool;
import org.core.utils.Tasks;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.LogFactory;
import cn.hutool.system.SystemUtil;
import org.core.contalina.Context;

/**
 * @date 2022-3-18
 * @author bobee
 */
public class BootStrap{
	/**
	 * 服务器启动类入口: 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		//在18080端口上去监听
		int port = 18080;
		int backlog = 10;
		Context.scanContextsOnWebAppsFolder();//读取所有的APP，加载到ContextMap
		try {
			//ServerSocket可以端口监听; backlog设置等待连接列表的限制（accept才会从中取出来）不要随便for循环
			ServerSocket ss = new ServerSocket(port, backlog);
			Logs.logJVM();
			//使用线程池来管理执行任务
			int nThreads = 10;
			MyThreadPool exe = new MyThreadPool(nThreads);
			while(true) {
				Socket s = ss.accept();
				Runnable task = Tasks.getTask(s);
				exe.submit(task);
			}
		}catch(IOException e) {
			LogFactory.get().error(e);
//			System.out.print("端口占用异常 socket bind failed");
		}finally{
		}
	}
}