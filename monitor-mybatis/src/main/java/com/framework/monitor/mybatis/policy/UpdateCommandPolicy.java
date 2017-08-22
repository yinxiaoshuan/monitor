package com.framework.monitor.mybatis.policy;

public class UpdateCommandPolicy extends AbstractResultSetPolicy{

	@Override
	int fetchSize( final Object result )
	{
		final int fetchSize = ( int ) result;
		return fetchSize;
	}

	@Override
	int queryCount( final Object result )
	{
		return 0;
	}
	
}
