package org.core.utils;

import java.io.File;

import cn.hutool.system.SystemUtil;

public class Constant {
	public final static File webappsFolder = new File(SystemUtil.get("user.dir"),"webapps");
    public final static File rootFolder = new File(webappsFolder,"ROOT");
}
