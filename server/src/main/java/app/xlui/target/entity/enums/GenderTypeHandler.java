package app.xlui.target.entity.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Type handler, convert Gender to int in order to store into database
 * When <code>select</code> the column in database, this class will be
 * called to convert int to Gender.
 */
public class GenderTypeHandler extends BaseTypeHandler<Gender> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.value());
	}

	@Override
	public Gender getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return Gender.parse(rs.getInt(columnName));
	}

	@Override
	public Gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return Gender.parse(rs.getInt(columnIndex));
	}

	@Override
	public Gender getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return Gender.parse(cs.getInt(columnIndex));
	}
}
