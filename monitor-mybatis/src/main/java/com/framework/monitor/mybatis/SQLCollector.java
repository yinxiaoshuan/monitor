package com.framework.monitor.mybatis;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.framework.monitor.mybatis.event.MonitorEventTrigger;
import com.framework.monitor.mybatis.event.MonitorSource;
import com.framework.monitor.mybatis.helper.ConstantTable;

/**
 * 
 * @author YRJ
 *
 */
@Intercepts(
{ @Signature( type = Executor.class, method = "update", args =
{ MappedStatement.class, Object.class } ), @Signature( type = Executor.class, method = "query", args =
{ MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class } ) } )
public class SQLCollector implements Interceptor
{

	/**
	 * 慢SQL阈值, 默认为100ms.
	 * <ul>
	 * <li>代表SQL最大执行时间警报值, 超过该值代表当前SQL为慢查询, 统计记录中标记为慢查询.</li>
	 * <li>SQL执行时间只包含执行proceed方法时间, 不包含统计数据收集时间.</li>
	 * <ul>
	 */
	private long threshold;

	public void setThreshold( final long threshold )
	{
		long _threshold = threshold;
		if ( _threshold < 1 )
		{
			_threshold = ConstantTable.slowQuerySQLThreshold( );
		}
		this.threshold = _threshold;
	}

	/**
	 * 最大查询返回记录数.
	 * <ul>
	 * <li>1. 默认为: 20.</li>
	 * <li>2. 超过此值表示SQL返回记录过多, 需要限制SQL的返回.</li>
	 * </ul>
	 */
	private int maxQuery;

	public void setMaxQuery( final int maxQuery )
	{
		int _maxQuery = maxQuery;
		if ( _maxQuery <= 1 )
		{
			_maxQuery = ConstantTable.maxQueryLineThreshold( );
		}
		this.maxQuery = _maxQuery;
	}

	/**
	 * 数据库名称. 标记SQL执行在哪个数据库.
	 * <ul>
	 * <li>1. 默认为: _DB_</li>
	 * <li>2. 此值最大长度为5个字符, 且全部为大写英文.</li>
	 * </ul>
	 */
	private String database;

	public void setDatabase( final String database )
	{
		String _db = database;
		if ( _db == null || _db.trim( ).isEmpty( ) )
		{
			_db = ConstantTable.getUnknowDatabase( );
		}
		this.database = _db;
	}

	/**
	 * 事件发布器, 根据SQL执行情况触发事件监听器.
	 */
	private MonitorEventTrigger eventTrigger;

	public void setEventTrigger( final MonitorEventTrigger eventTrigger )
	{
		this.eventTrigger = eventTrigger;
	}

	@Override
	public Object intercept( final Invocation invocation ) throws Throwable
	{
		Object result = null;
		Throwable cause = null;
		final MonitorSource eventSource = new MonitorSource( invocation.getArgs( ), threshold, maxQuery, database );
		try
		{
			result = invocation.proceed( );
		} catch ( InvocationTargetException | IllegalAccessException e )
		{
			cause = e;
			throw cause;
		} finally
		{
			eventTrigger.trigger( eventSource, result, cause );
		}
		return result;
	}

	@Override
	public Object plugin( final Object target )
	{
		if ( target instanceof Executor )
		{
			return Plugin.wrap( target, this );
		}
		return target;
	}

	@Override
	public void setProperties( final Properties properties )
	{
		{
			final String threshold = properties.getProperty( "threshold" );
			setThreshold( BasicTypeConverter.parseLong( threshold, ConstantTable.slowQuerySQLThreshold( ) ) );
		}
		{
			final String maxQuery = properties.getProperty( "maxQuery" );
			setMaxQuery( BasicTypeConverter.parseInt( maxQuery, ConstantTable.maxQueryLineThreshold( ) ) );
		}
		{
			final String database = properties.getProperty( "database", String.valueOf( ConstantTable.getUnknowDatabase( ) ) );
			setDatabase( database );
		}
	}
}
