package org.core.http;

import java.io.*;
import java.net.Socket;

import cn.hutool.core.util.StrUtil;


/**
 * @date 2022-3-18
 * @author bobee
 */
public class Request {
	/**
	 * 将HTTP整个报文段从socket中读出来；然后按照HTTP格式进行解析（空格分割）
	 */
	private String requestString;
	private String uri;
	private Socket socket;
	
	/*
	 * 	将对应的那个socket放进来；之后都在这里进行uri和request提取
	 */
	public Request(Socket socket) throws IOException {
		this.socket = socket;
		//解析数据
		this.parseHttpRequest();
		if(StrUtil.isEmpty(requestString)) return;
		//读取处URI,方便后续的处理方法确认
		this.parseUri();
	}
	
	private void parseHttpRequest() throws IOException {
		InputStream in = socket.getInputStream();
		int buffer_size = 1024;
		byte[] buffer = new byte[buffer_size];
		//将socket缓冲区中读出来的东西放进另一个缓冲区
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		//将HTTP中的内容全部读出来
		while(true) {
			//read()方法只能用定长数组来存
			int length = in.read(buffer); //从socket的输出缓冲区中把数据读出来
			if(length == 0) break;
			bOut.write(buffer, 0, length);
			if(length != buffer_size) break;
		}
		requestString = new String(bOut.toByteArray(), "utf-8");
	}
	
	private void parseUri() {
		//HTTP首部第一行：请求方法+“ ”+URL+“ ”+版本
		String temp;

        temp = StrUtil.subBetween(requestString, " ", " "); //两个空格里面的就是URI
        if (!StrUtil.contains(temp, '?')) {
            uri = temp;
            return;
        } //“？”将URL和参数分开
        temp = StrUtil.subBefore(temp, '?', false);
        uri = temp;
	}
	
	public String getUri() {
		return this.uri;
	}
	
	public String getRequest() {
		return this.requestString;
	}
}
