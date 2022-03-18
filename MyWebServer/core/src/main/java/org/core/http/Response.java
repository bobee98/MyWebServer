package org.core.http;

import java.io.PrintWriter;
import java.io.StringWriter;

import cn.hutool.core.util.StrUtil;

public class Response {
	public final static String response_head_200 =
            "HTTP/1.1 200 OK\r\n" +
            "Content-Type: {}\r\n\r\n";
	private String contentType = "text/html";
	
	private StringWriter responseString; //用来存最终的内容
	private PrintWriter printWriter; //会向初始化时绑定的writer中写入数据，为什么这两个要一起用？

	public Response() {
		responseString  = new StringWriter();
		this.printWriter = new PrintWriter(responseString);
	}
	
	public PrintWriter getWriter() {
		return printWriter;
	}
	
	public byte[] get200Head() {
		String headText = StrUtil.format(response_head_200, contentType);
		String headAndBody = headText + responseString.toString();
		byte[] responseBytes = headAndBody.getBytes();
		return responseBytes;
	}
}
