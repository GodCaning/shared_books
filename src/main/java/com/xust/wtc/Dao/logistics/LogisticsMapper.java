package com.xust.wtc.Dao.logistics;

import com.xust.wtc.Entity.Company;
import com.xust.wtc.Entity.DisplayLogistics;
import com.xust.wtc.Entity.Logistics;
import com.xust.wtc.Entity.book.LenderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Spirit on 2018/1/9.
 */
@Repository
public interface LogisticsMapper {

    /**
     * 根据最终订单生成一个物流信息
     * @param lend
     * @return
     */
    int addLogistics(LenderInfo lend);

    /**
     * 添加物流编号和公司
     * @param logistics
     * @return
     */
    int updateLogistics(Logistics logistics);

    /**
     * 根据订单ID获取物流信息
     * @param lendId
     * @return
     */
    DisplayLogistics findLogisticsByLendId(@Param("lendId") int lendId);

    /**
     * 更新物流状态
     * @param status
     * @param id
     */
    void updateLogisticsStatus(@Param("status") int status, @Param("id") int id);

    /**
     * 获取全部快递公司信息
     * @return
     */
    List<Company> findLogisticsCompany();
}
