package com.xust.wtc.Service.logistics;

import com.xust.wtc.Entity.logistics.Company;
import com.xust.wtc.Entity.logistics.DisplayLogistics;
import com.xust.wtc.Entity.logistics.Logistics;
import com.xust.wtc.Entity.Result;
import com.xust.wtc.Entity.book.LenderInfo;

import java.util.List;

/**
 * Created by Spirit on 2018/1/9.
 */
public interface LogisticsService {

    int addLogistics(LenderInfo lend);

    Result updateLogistics(Logistics logistics);

    DisplayLogistics findLogisticsByLendId(int lendId);

    List<Company> findLogisticsCompany();
}
