package org.core;

import java.io.*;
import java.net.*;
import org.core.http.*;//我自己的路径

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;

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
		try {
			//判断端口是否可用
			//ServerSocket可以端口监听; backlog设置等待连接列表的限制（accept才会从中取出来）不要随便for循环
			ServerSocket ss = new ServerSocket(port, backlog);
//			ss.setReuseAddress(true); 重启服务器之后马上就可以用
			while(true) {
				Socket s = ss.accept();
				Request request = new Request(s);
				System.out.println("浏览器发送Request: " + request.getRequest());
				System.out.println("浏览器发送URI: " + request.getUri());
				
				//返回一个输出流
				OutputStream out = s.getOutputStream();
				Response response = new Response();
				String uri = request.getUri();
				if(uri == "/") {
					String html = "hello, welcom 200";
					response.getWriter().println(html); //writer中的内容都会自动刷新到目标中（文件或Writer）
				}
				else {
					String fileName = StrUtil.removeSuffix(uri, "/");
					File file = FileUtil.file("webapps/", fileName);
					if(file.exists()) {
						String fileContent = FileUtil.readUtf8String(file);
						response.getWriter().println(fileContent);
					}else {
						response.getWriter().println("File not found, sorry!");
					}
				}
				out.write(response.get200Head());
				s.close();
			}
		}catch(IOException e) {
			System.out.print("端口占用异常 socket bind failed");
		}finally{
		}
	}
}