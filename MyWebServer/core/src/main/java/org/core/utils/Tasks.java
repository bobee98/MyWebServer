package org.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.core.contalina.Context;
import org.core.http.Request;
import org.core.http.Response;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.LogFactory;

/**
 * Task工厂类；本文件中还有其他的真实Runnable的Task类用于给工厂类返回
 * @author bobee
 *
 */
public class Tasks {
	public static Runnable getTask(Socket s) {
		return new PageTask(s);
	}
}

class PageTask implements Runnable{
	private final Socket s;
	
	public PageTask(Socket s) {
		this.s = s;
	}
	@Override
	public void run() {
		Request request;
		try {
			request = new Request(s);
		
//		System.out.println("浏览器发送Request: " + request.getRequest());
		System.out.println("线程：" + Thread.currentThread().getName() + "===> 正在工作");
		System.out.println("浏览器发送URI: " + request.getUri());
		
		//返回一个输出流
		OutputStream out = s.getOutputStream();
		Response response = new Response();
		String uri = request.getUri();
		Context context = request.getContext();
		if(uri.equals("/")) { //不存在的路径/应用，就回到主页
			String html = "hello, welcom 200";
			response.getWriter().println(html); //writer中的内容都会自动刷新到目标中（文件或Writer）
		}
		else {
			String fileName = uri.replace(context.getPath(), ""); //只删除一开始匹配到的app名
			if(context.getPath() == "/") fileName = uri;
			File file = FileUtil.file(context.getBase(),fileName);
			if(file.exists()) {
				String fileContent = FileUtil.readUtf8String(file);
				response.getWriter().println(fileContent);
			}else {
				response.getWriter().println("File not found, sorry!");
			}
		}
		
		out.write(response.get200Head());
		s.close();
		} catch (IOException e) {
			e.printStackTrace();
			LogFactory.get().error(e);
		}
	}
}
