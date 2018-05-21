package com.xust.wtc.typeHandle;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Spirit on 2018/5/19.
 */
public class LogisticsStatusTypeHandler implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        switch (s) {
            case "创建" : preparedStatement.setInt(i, 0); break;
            case "发货" : preparedStatement.setInt(i, 1); break;
            case "在途中" : preparedStatement.setInt(i, 2); break;
            case "签收" : preparedStatement.setInt(i, 3); break;
            case "关单" : preparedStatement.setInt(i, 5); break;
            default: preparedStatement.setInt(i, 4); break;
        }
    }

    @Override
    public String getResult(ResultSet resultSet, String s) throws SQLException {
        return transferType(resultSet.getInt(s));
    }

    @Override
    public String getResult(ResultSet resultSet, int i) throws SQLException {
        return transferType(resultSet.getInt(i));
    }

    @Override
    public String getResult(CallableStatement callableStatement, int i) throws SQLException {
        return transferType(callableStatement.getInt(i));
    }

    private String transferType(int anInt) {
        switch (anInt) {
            case 0 : return "创建";
            case 1 : return "发货";
            case 2 : return "在途中";
            case 3 : return "签收";
            case 5 : return "关单";
            default: return "问题件";
        }
    }
}
