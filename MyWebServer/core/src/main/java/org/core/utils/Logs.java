package org.core.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import cn.hutool.log.LogFactory;
import cn.hutool.system.SystemUtil;

public class Logs {
	
	public static void logJVM() {
        Map<String,String> infos = new LinkedHashMap<>();
        infos.put("Server version", "liubo MyWebServer/1.0.1");
        infos.put("Server built", "2022-03-20 10:20:22");
        infos.put("Server number", "1.0.1");
        infos.put("OS Name\t", SystemUtil.get("os.name"));
        infos.put("OS Version", SystemUtil.get("os.version"));
        infos.put("Architecture", SystemUtil.get("os.arch"));
        infos.put("Java Home", SystemUtil.get("java.home"));
        infos.put("JVM Version", SystemUtil.get("java.runtime.version"));
        infos.put("JVM Vendor", SystemUtil.get("java.vm.specification.vendor"));
 
        Set<String> keys = infos.keySet();
        for (String key : keys) {
            LogFactory.get().info(key+":\t\t" + infos.get(key));
        }
    }

}
