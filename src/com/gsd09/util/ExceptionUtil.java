package com.gsd09.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.gsd09.TApplication;

/** 存放打印异常类 */
public class ExceptionUtil {
	public static void handleException(Exception e) {
		// 判断是否已发布
		if (TApplication.isRelease) {
			// 已发布
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			String errorInfo = stringWriter.toString();
			// 自动发送邮件 (留坑)
		} else {
			// 开发中,仅打印出来
			e.printStackTrace();
		}

	}
}
