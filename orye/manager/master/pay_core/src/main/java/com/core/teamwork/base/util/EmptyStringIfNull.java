/**  
 * @Title: EmptyStringIfNull.java
 * @Package com.iwuduan.teamwork.base.util
 * @Description: Map空值映射设置默认值
 * @author pengzhihao
 * @date 2016-6-16
 */
package com.core.teamwork.base.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * <P>ClassName: EmptyStringIfNull</P>
 * <P>@Description: Map空值映射设置默认值</P>
 * <P>Company:</P>
 * @author pengzhihao
 * @date 2016-6-16下午7:10:28
 */
public class EmptyStringIfNull implements TypeHandler<String>{

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, java.lang.String)
	 */
	@Override
	public String getResult(ResultSet rs, String columnName) throws SQLException {
		// TODO Auto-generated method stub
		 return (rs.getString(columnName) == null) ? "" : rs.getString(columnName); 
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, int)
	 */
	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return (rs.getString(columnIndex) == null) ? "" : rs.getString(columnIndex);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement, int)
	 */
	@Override
	public String getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		// TODO Auto-generated method stub
		 return (cs.getString(columnIndex) == null) ? "" : cs.getString(columnIndex);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)
	 */
	@Override
	public void setParameter(PreparedStatement arg0, int arg1, String arg2,
			JdbcType arg3) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
