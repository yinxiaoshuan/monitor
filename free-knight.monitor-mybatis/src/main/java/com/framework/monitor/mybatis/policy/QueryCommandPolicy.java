package com.framework.monitor.mybatis.policy;

import java.util.List;

public class QueryCommandPolicy extends AbstractResultSetPolicy{

	@Override
	int fetchSize( final Object result )
	{
		return 0;
	}

	@Override
	int queryCount( final Object result )
	{
		final int queryCount = ( ( List< ? > ) result ).size( );
		return queryCount;
	}
	
}
