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
public class LendStatusTypeHandler implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        switch (s) {
            case "订单创建成功" : preparedStatement.setInt(i, 1); break;
            case "借书人信息填写完整" : preparedStatement.setInt(i, 2); break;
            case "订单生成" : preparedStatement.setInt(i, 3); break;
            case "订单结束" : preparedStatement.setInt(i, 4); break;
            case "订单失效" : preparedStatement.setInt(i, 5); break;
            case "关单" : preparedStatement.setInt(i, 6); break;
            default: preparedStatement.setInt(i, 0); break;
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
            case 1 : return "订单创建成功";
            case 2 : return "借书人信息填写完整";
            case 3 : return "订单生成";
            case 4 : return "订单结束";
            case 5 : return "订单失效";
            case 6 : return "关单";
            default: return "出借人拒绝订单";
        }
    }
}