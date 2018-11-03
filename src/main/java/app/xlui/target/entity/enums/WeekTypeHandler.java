package app.xlui.target.entity.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Type handler, convert Week to int in order to store into database
 * When <code>select</code> the column in database, this class will be
 * called to convert int to Week.
 */
public class WeekTypeHandler extends BaseTypeHandler<Week> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Week parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.value());
	}

	@Override
	public Week getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return Week.parse(rs.getInt(columnName));
	}

	@Override
	public Week getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return Week.parse(rs.getInt(columnIndex));
	}

	@Override
	public Week getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return Week.parse(cs.getInt(columnIndex));
	}
}
