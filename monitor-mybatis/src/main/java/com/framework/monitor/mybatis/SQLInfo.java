package com.framework.monitor.mybatis;

import java.util.Arrays;

import com.framework.monitor.mybatis.helper.Identifier;

public class SQLInfo
{

	private final String database;

	private String sqlId;

	private String sql;

	private Object[ ] args;

	private long duration;

	private boolean slow;

	private int fetchSize;

	private int queryCount;

	private int errCode;

	private String errMsg;

	public SQLInfo( final String database, final String id )
	{
		this.database = database;
		setSqlId( id );
	}

	public int getIdentifier( )
	{
		return Identifier.getIdentifier( sqlId, sql );
	}

	public String getDatabase( )
	{
		return database;
	}

	public String getSqlId( )
	{
		return sqlId;
	}

	public void setSqlId( final String sqlId )
	{
		this.sqlId = sqlId;
	}

	public String getSql( )
	{
		return sql;
	}

	public void setSql( final String sql )
	{
		this.sql = sql;
	}

	public Object[ ] getArgs( )
	{
		return args;
	}

	public void setArgs( final Object[ ] args )
	{
		this.args = args;
	}

	public long getDuration( )
	{
		return duration;
	}

	public void setDuration( final long duration )
	{
		this.duration = duration;
	}

	public boolean isSlow( )
	{
		return slow;
	}

	public SQLInfo setSlow( final boolean slow )
	{
		this.slow = slow;
		return this;
	}

	public int getFetchSize( )
	{
		return fetchSize;
	}

	public SQLInfo setFetchSize( final int fetchSize )
	{
		this.fetchSize = fetchSize;
		return this;
	}

	public int getQueryCount( )
	{
		return queryCount;
	}

	public SQLInfo setQueryCount( final int queryCount )
	{
		this.queryCount = queryCount;
		return this;
	}

	public int getErrCode( )
	{
		return errCode;
	}

	public SQLInfo setErrCode( final int errCode )
	{
		this.errCode = errCode;
		return this;
	}

	public String getErrMsg( )
	{
		return errMsg;
	}

	public SQLInfo setErrMsg( final String errMsg )
	{
		this.errMsg = errMsg;
		return this;
	}

	@Override
	public String toString( )
	{
		final StringBuilder tostr = new StringBuilder( );
		tostr.append( getDatabase( ) ).append( '-' );
		tostr.append( getIdentifier( ) ).append( '-' );

		tostr.append( '{' );
		tostr.append( "\"cmd\":" ).append( "insert" ).append( ',' );
		tostr.append( "\"sqlid\":" ).append( sqlId ).append( ',' );
		tostr.append( "\"occurTime\":" ).append( "20170112160012.456" ).append( ',' );
		tostr.append( "\"sql\":" ).append( sql ).append( ',' );
		tostr.append( "\"args\":" ).append( Arrays.toString( args ) ).append( ',' );
		tostr.append( "\"duration\":" ).append( duration ).append( ',' );
		tostr.append( "\"slow\":" ).append( slow ).append( ',' );
		tostr.append( "\"fetchSize\":" ).append( fetchSize ).append( ',' );
		tostr.append( "\"queryCount\":" ).append( queryCount ).append( ',' );
		tostr.append( "\"errCode\":" ).append( errCode ).append( ',' );
		tostr.append( "\"errMsg\":\"" ).append( errMsg ).append( "\"" ).append( '}' );
		return tostr.toString( );
	}
}
