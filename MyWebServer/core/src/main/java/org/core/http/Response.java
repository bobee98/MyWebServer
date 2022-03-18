package org.core.http;

public class Response {
	private static String headHttpVersion = "HTTP/1.1";
	private static String headerUrl = "localhost:18080";
	private static String headerContentType = "Content-Type: text/html";
	private int statu = 0;
	private String content = "";
	
	public void setcontent(String content) {
		this.content = content;
	}
	public String getcontent(String content) {
		return this.content;
	}
	
	public String get200() {
		return headHttpVersion + " " + 200 + " OK\r\n" + headerContentType
				+ "\r\n\r\n" + content;
	}
}
