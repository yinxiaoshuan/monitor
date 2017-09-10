package com.framework.monitor.mybatis.event;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.monitor.mybatis.SQLInfo;
import com.framework.monitor.mybatis.policy.ResultSet;
import com.framework.monitor.mybatis.policy.ResultSetPolicyFactory;

public class SQLMonitorListener implements MonitorListener{

	private final static Logger logger = LoggerFactory.getLogger( SQLMonitorListener.class );

	@Override
	public void handleEvent( final SQLMonitorEvent event, final Object result, final Throwable cause )
	{
		final MonitorSource source = event.getSource( );
		final MappedStatement statement = source.getMappedStatement( );

		final SQLInfo sqlInfo = new SQLInfo( source.getDatabase( ), statement.getId( ) );
		sqlInfo.setDuration( source.getTimestamp( ) );
		{
			final BoundSql boundSQL = statement.getSqlSource( ).getBoundSql( source.getSQLParameters( ) );
			sqlInfo.setSql( boundSQL.getSql( ) );
		}
		{
			final ResultSet resultSet = ResultSetPolicyFactory.createResultSetPolicy( statement.getSqlCommandType( ).name( ) )
					.doResultSet( result, cause );
			sqlInfo.setFetchSize( resultSet.getFetchSize( ) ).setQueryCount( sqlInfo.getQueryCount( ) );
			sqlInfo.setErrCode( resultSet.getErrorCode( ) ).setErrMsg( resultSet.getErrMsg( ) );
		}

		logger.info( sqlInfo.toString( ) );
	}

	// @Override
	// public boolean supportsEventType( final Class< ? > eventType )
	// {
	// return false;
	// }

	@Override
	public boolean supportsSourceType( final Class< ? > sourceType )
	{
		return MonitorSource.class == sourceType;
	}
	
}
