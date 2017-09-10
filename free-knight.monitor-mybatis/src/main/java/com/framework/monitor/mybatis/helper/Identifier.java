package com.framework.monitor.mybatis.helper;

/**
 * SQL标识符计算器.
 * 
 * @author a123
 *
 */
public final class Identifier {

	public static int getIdentifier( final String... src )
	{
		if ( src == null )
		{
			return 0;
		}

		final StringBuilder tostr = new StringBuilder( src[ 0 ] );
		for ( int index = 1, length = src.length; index < length; index++ )
		{
			tostr.append( '-' );
			tostr.append( src[ index ] );
		}

		int hashCode = tostr.toString( ).hashCode( );
		hashCode = Math.abs( hashCode );
		return hashCode;
	}

	private Identifier( )
	{
		throw new AssertionError( "Uninstantiable class." );
	}
	
}
