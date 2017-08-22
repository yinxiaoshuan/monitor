package com.framework.monitor.mybatis.event;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SQL统计事件发布器.
 * 
 * @author YRJ
 *
 */
public class MonitorEventTrigger {

	private final static Logger logger = LoggerFactory.getLogger( MonitorEventTrigger.class );

	private final Set< MonitorListener > listeners = new CopyOnWriteArraySet< MonitorListener >( );

	public void addListener( final MonitorListener listener )
	{
		final boolean isOk = listeners.add( listener );
		if ( logger.isDebugEnabled( ) )
		{
			logger.debug( "EventTrigger add [" + ( listener.getClass( ).getName( ) ) + "], " + ( isOk ? "成功" : "失败" ) );
		}
	}

	public void setMonitorListener( final MonitorListener listener )
	{
		addListener( listener );
	}

	// private Executor taskExecutor;
	//
	// public void setTaskExecutor( final Executor taskExecutor )
	// {
	// this.taskExecutor = taskExecutor;
	// }

	public void trigger( final MonitorSource eventSource, final Object result, final Throwable cause )
	{
		final MonitorListener listener = getSupportMonitorListener( eventSource );
		if ( listener == null )
		{
			return;
		}

		listener.handleEvent( new SQLMonitorEvent( eventSource ), result, cause );
	}

	protected MonitorListener getSupportMonitorListener( final MonitorSource eventSource )
	{
		MonitorListener listener = null;
		for ( final Iterator< MonitorListener > iter = listeners.iterator( ); iter.hasNext( ); )
		{
			final MonitorListener entity = iter.next( );
			if ( entity.supportsSourceType( eventSource.getClass( ) ) )
			{
				listener = entity;
				break;
			}
		}
		return listener;
	}
	
}
