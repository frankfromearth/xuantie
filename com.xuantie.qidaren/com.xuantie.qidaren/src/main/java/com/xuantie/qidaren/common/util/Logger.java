/**
 * @company   : com.xuantie
 * @copyright : copyright (c) 2018
 * 
 * @description: TODO
 * @author   : xuhh
 * @date     : 2018/4/26
 * @version  : v1.0
 */
package com.xuantie.qidaren.common.util;

import org.slf4j.LoggerFactory;

/**
 * 
 * 日志记录
 * @author xuhh
 * @date 2018/4/26
 *
 */
public class Logger {

	/**
	 * Logger对象
	 */
	private static org.slf4j.Logger logger = null;

	/**
	 * 接口调用日志logger
	 */
	private static org.slf4j.Logger accessLogger = null;

	// 私有化构造器
	private Logger() {
	}

	/**
	 * 单例获取Logger对象
	 * @return
	 */
	public static synchronized org.slf4j.Logger getLogger() {
		if (null == logger) {
			try {
				logger = LoggerFactory.getLogger("root");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return logger;
	}

	/**
	 * 单例获取accessLogger对象
	 * @return
	 */
	public static synchronized org.slf4j.Logger getAccessLogger() {
		if (null == accessLogger) {
			try {
				accessLogger = LoggerFactory.getLogger("accesslog");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return accessLogger;
	}

	/**
	 * 记录接口调用日志
	 * @param log
	 */
	public static void access(String msg) {
		try {
			getAccessLogger().info(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取调用方法类名称
	 * @return
	 */
	private static String getClazzName() {
		try {
			StackTraceElement a = (StackTraceElement) Thread.currentThread().getStackTrace()[3];
			return a.getClassName();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * trace日志
	 * @param msg 日志内容
	 */
	public static void trace(String msg) {
		try {
			getLogger().trace("[" + getClazzName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * trace日志
	 * @param objName 调试对象描述
	 * @param msg 日志内容
	 */
	public static void trace(String objName, String msg) {
		try {
			getLogger().trace("[" + objName + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * trace日志
	 * @param obj 调试对象
	 * @param msg 日志内容
	 */
	public static void trace(Object obj, String msg) {
		try {
			getLogger().trace("[" + obj.getClass().getName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * debug日志
	 * @param msg 日志内容
	 */
	public static void debug(String msg) {
		try {
			getLogger().debug("[" + getClazzName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * debug日志
	 * @param objName 调试对象描述
	 * @param msg 日志内容
	 */
	public static void debug(String objName, String msg) {
		try {
			getLogger().debug("[" + objName + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * debug日志
	 * @param obj 调试对象
	 * @param msg 日志内容
	 */
	public static void debug(Object obj, String msg) {
		try {
			getLogger().debug("[" + obj.getClass().getName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * info日志
	 * @param msg 日志内容
	 */
	public static void info(String msg) {
		try {
			getLogger().info("[" + getClazzName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * info日志
	 * @param objName 调试对象
	 * @param msg 日志内容
	 */
	public static void info(String objName, String msg) {
		try {
			getLogger().info("[" + objName + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * info日志
	 * @param obj 调试对象
	 * @param msg 日志内容
	 */
	public static void info(Object obj, String msg) {
		try {
			getLogger().info("[" + obj.getClass().getName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * warn日志
	 * @param msg 日志内容
	 */
	public static void warn(String msg) {
		try {
			getLogger().warn("[" + getClazzName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * warn日志
	 * @param objName 调试对象描述
	 * @param msg 日志内容
	 */
	public static void warn(String objName, String msg) {
		try {
			getLogger().warn("[" + objName + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * warn日志
	 * @param obj 调试对象
	 * @param msg 日志内容
	 */
	public static void warn(Object obj, String msg) {
		try {
			getLogger().warn("[" + obj.getClass().getName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * error日志
	 * @param msg 日志内容
	 */
	public static void error(String msg) {
		try {
			getLogger().error("[" + getClazzName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * error日志
	 * @param msg 日志内容
	 * @param e 异常对象
	 */
	public static void error(String msg, Exception e) {
		try {
			getLogger().error("[" + getClazzName() + "]:" + msg, e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * error日志
	 * @param objName 调试对象描述
	 * @param msg 日志内容
	 */
	public static void error(String objName, String msg) {
		try {
			getLogger().error("[" + objName + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * error日志
	 * @param objName 调试对象描述
	 * @param msg 日志内容
	 * @param e 异常对象
	 */
	public static void error(String objName, String msg, Exception e) {
		try {
			getLogger().error("[" + objName + "]:" + msg, e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * error日志
	 * @param obj 调试对象描述
	 * @param msg 日志内容
	 */
	public static void error(Object obj, String msg) {
		try {
			getLogger().error("[" + obj.getClass().getName() + "]:" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * error日志
	 * @param obj 调试对象描述
	 * @param msg 日志内容
	 */
	public static void error(Object obj, String msg, Exception e) {
		try {
			getLogger().error("[" + obj.getClass().getName() + "]:" + msg, e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
