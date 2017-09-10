package com.framework.monitor.mybatis.policy;

/**
 * SQL执行结果, 记录受影响行、读取行、错误码等信息.
 * 
 * @author a123
 *
 */
public class ResultSet {

	private int fetchSize;

	private int queryCount;

	private int errorCode;

	private String errMsg;

	public ResultSet( )
	{
		this( 10000, "ok" );
	}

	public ResultSet( final int errorCode, final String errMsg )
	{
		this.errorCode = errorCode;
		this.errMsg = errMsg;
	}

	public ResultSet( final int fetchSize, final int queryCount )
	{
		this( );
		this.fetchSize = fetchSize;
		this.queryCount = queryCount;
	}

	public int getFetchSize( )
	{
		return fetchSize;
	}

	public void setFetchSize( final int fetchSize )
	{
		this.fetchSize = fetchSize;
	}

	public int getQueryCount( )
	{
		return queryCount;
	}

	public void setQueryCount( final int queryCount )
	{
		this.queryCount = queryCount;
	}

	public int getErrorCode( )
	{
		return errorCode;
	}

	public ResultSet setErrorCode( final int errorCode )
	{
		this.errorCode = errorCode;
		return this;
	}

	public String getErrMsg( )
	{
		return errMsg;
	}

	public ResultSet setErrMsg( final String errMsg )
	{
		this.errMsg = errMsg;
		return this;
	}
	
}
