package com.framework.monitor.mybatis.helper;

/**
 * 常量表.
 * 
 * @author YRJ
 *
 */
public final class ConstantTable {

	/**
	 * 慢SQL阈值, 默认为100ms.
	 * 
	 * @return
	 */
	public static final int slowQuerySQLThreshold( )
	{
		return 100;
	}

	/**
	 * 最大查询行阈值, 默认为20.
	 * 
	 * @return
	 */
	public static final int maxQueryLineThreshold( )
	{
		return 20;
	}

	/**
	 * 获取默认数据库名称, 当数据库未指定情况下, 统一采用该默认值.
	 * 
	 * @return
	 */
	public static final String getUnknowDatabase( )
	{
		return "_DB_";
	}

	private ConstantTable( )
	{
		throw new AssertionError( "Uninstantiable class." );
	}
	
}
