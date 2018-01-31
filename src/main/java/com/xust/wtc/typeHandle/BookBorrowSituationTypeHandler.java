package com.xust.wtc.typeHandle;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 转换书籍借阅情况
 * 1 <-> "未出借"
 * 2 <-> "出借中"
 * 3 <-> "出借"
 * Created by Spirit on 2018/1/30.
 */
public class BookBorrowSituationTypeHandler implements TypeHandler<String>{

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        switch (s) {
            case "未出借" : preparedStatement.setInt(i, 1); break;
            case "出借中" : preparedStatement.setInt(i, 2); break;
            case "出借" : preparedStatement.setInt(i, 3); break;
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
            case 1 : return "未出借";
            case 2 : return "出借中";
            case 3 : return "出借";
            default: return "删除";
        }
    }
}
