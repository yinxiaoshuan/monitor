package com.framework.monitor.mybatis.event;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * 监控事件源.
 * 
 * @author YRJ
 *
 */
public class MonitorSource {

	private final Object[ ] args;

	private final long timestamp = System.currentTimeMillis( );

	private final long threshold;

	private final int maxQuery;

	private final String database;

	public MonitorSource( final Object[ ] args, final long threshold, final int maxQuery, final String database )
	{
		this.args = args;
		this.threshold = threshold;
		this.maxQuery = maxQuery;
		this.database = database;
	}

	public MappedStatement getMappedStatement( )
	{
		final MappedStatement statement = ( MappedStatement ) args[ 0 ];
		return statement;
	}

	public Object getSQLParameters( )
	{
		return args[ 1 ];
	}

	public long getTimestamp( )
	{
		return ( System.currentTimeMillis( ) - timestamp );
	}

	public long getThreshold( )
	{
		return threshold;
	}

	public int getMaxQuery( )
	{
		return maxQuery;
	}

	public String getDatabase( )
	{
		return database;
	}
	
}
