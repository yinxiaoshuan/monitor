package com.framework.monitor.mybatis.policy;

/**
 * SQL执行结果处理策略, 对执行结果进行处理.
 * 
 * @author a123
 *
 */
public interface ResultSetPolicy {

	/**
	 * 当SQL执行成功, 调用此方法对结果进行解析, 抽取受影响行、读取行数等信息.
	 * 
	 * @param result
	 * @return
	 */
	ResultSet doResultSet( Object result, Throwable cause );

	/**
	 * 当SQL发生异常时, 调用此方法对异常进行解析, 抽取错误码、产生的原因等信息.
	 * 
	 * @param e
	 * @return
	 */
	// ResultSet doException( Throwable e );
	
}
