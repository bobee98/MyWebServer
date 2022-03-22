package org.core.contalina;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.LogFactory;
import java.util.Map;

import org.core.utils.Constant;

import java.io.File;
import java.util.HashMap;

/**
 * 给每一个上下文分配一个独特的路径：用路径区分不同的应用;一开始利用webapps/中文件夹个数来判断有多少app
 * @author bobee
 *
 */
public class Context {
	public static final Map<String, Context> contextMap = new HashMap<>();
	private final String path;
	private final String docBase;
	
	public Context(String path, String docBase) {
		TimeInterval timeInterval = DateUtil.timer();
		this.path = path;
		this.docBase = docBase;
		LogFactory.get().info("Deploying web application directory {}", this.docBase);
		LogFactory.get().info("Deployment of web application directory {} has finished in {} ms", this.docBase,timeInterval.intervalMs());
	}
	/**
	 * 没有webapp前缀，只有一个文件名
	 * @return
	 */
	public String getPath() {
		return this.path;
	}
	/**
	 * 包含了webapps前缀
	 * @return
	 */
	public String getBase() {
		return this.docBase;
	}
	
	public static void scanContextsOnWebAppsFolder() {
		File[] files = Constant.webappsFolder.listFiles();
		for(File file : files) {
			//文件夹代表一个app
			if(!file.isDirectory()) continue;
			String path = file.getName();
			if(path.equals("ROOT")) {
				path = "/";
			}else {
				path = "/" + path;
			}
			String docBase = "webapps/" + file.getName();
			Context context = new Context(path, docBase);
			contextMap.put(context.getPath(), context);
		}
	}
}
