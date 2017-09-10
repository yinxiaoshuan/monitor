package com.framework.monitor.mybatis.policy;

import org.apache.ibatis.mapping.SqlCommandType;

/**
 * 策略工厂.
 * 
 * @author YRJ
 *
 */
public final class ResultSetPolicyFactory {

	public final static ResultSetPolicy createResultSetPolicy( String command )
	{
		if ( SqlCommandType.SELECT.name( ).equals( command ) )
		{
			return new QueryCommandPolicy( );
		}

		return new UpdateCommandPolicy( );
	}

	private ResultSetPolicyFactory( )
	{
		throw new AssertionError( "Uninstantiable class." );
	}
	
}
