package com.framework.monitor.mybatis.event;

import java.util.EventObject;

/**
 * SQL统计事件.
 * 
 * @author a123
 *
 */
public class SQLMonitorEvent extends EventObject{

	private final MonitorSource source;

	@Override
	public MonitorSource getSource( )
	{
		return source;
	}

	public SQLMonitorEvent( final MonitorSource source )
	{
		super( source );
		this.source = source;
	}

	private static final long serialVersionUID = 1L;
	
}
