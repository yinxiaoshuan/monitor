package com.framework.monitor.mybatis.event;

import java.util.EventListener;

/**
 * SQL统计事件监听器.
 * 
 * @author YRJ
 *
 */
public interface MonitorListener extends EventListener{

	/**
	 * 执行事件.
	 * 
	 * @param event
	 */
	void handleEvent( SQLMonitorEvent event, Object result, Throwable cause );

	/**
	 * 判断事件类型是否支持
	 * 
	 * @param eventType
	 * @return
	 */
	// boolean supportsEventType( Class< ? > eventType );

	/**
	 * 判断事件源类型是否支持.
	 * 
	 * @param sourceType
	 * @return
	 */
	boolean supportsSourceType( final Class< ? > sourceType );
	
}
