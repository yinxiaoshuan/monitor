package com.framework.monitor.mybatis.policy;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public abstract class AbstractResultSetPolicy implements ResultSetPolicy{

	@Override
	public ResultSet doResultSet( final Object result, final Throwable cause )
	{
		if ( cause != null )
		{
			return doException( cause );
		}

		final int fetchSize = fetchSize( result );
		final int queryCount = queryCount( result );

		final ResultSet resultSet = new ResultSet( fetchSize, queryCount );
		return resultSet;
	}

	protected ResultSet doException( final Throwable e )
	{
		final ResultSet resultSet = new ResultSet( 50000, e.getMessage( ) );
		if ( e instanceof SQLException )
		{
			final SQLException sqlEx = ( SQLException ) e;
			resultSet.setErrorCode( sqlEx.getErrorCode( ) ).setErrMsg( sqlEx.getMessage( ) );
			return resultSet;
		}

		if ( e instanceof InvocationTargetException )
		{
			final InvocationTargetException ite = ( InvocationTargetException ) e;
			final Throwable target = ite.getTargetException( );
			if ( target instanceof SQLException )
			{
				final SQLException sqlEx = ( SQLException ) target;
				resultSet.setErrorCode( sqlEx.getErrorCode( ) ).setErrMsg( sqlEx.getMessage( ) );
				return resultSet;
			}

			resultSet.setErrMsg( target.getMessage( ) );
		}

		// 非SQLException, 统一错误码为50000.
		return resultSet;
	}

	/**
	 *  计算受影响行.
	 * 
	 * @param result
	 * @return
	 */
	abstract int fetchSize( Object result );

	/**
	 * 计算查询行.
	 * 
	 * @param result
	 * @return
	 */
	abstract int queryCount( Object result );
	
}
