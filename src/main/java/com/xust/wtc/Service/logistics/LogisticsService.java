package com.xust.wtc.Service.logistics;

import com.xust.wtc.Entity.Company;
import com.xust.wtc.Entity.DisplayLogistics;
import com.xust.wtc.Entity.Logistics;
import com.xust.wtc.Entity.Result;

import java.util.List;

/**
 * Created by Spirit on 2018/1/9.
 */
public interface LogisticsService {

    int addLogistics(int lendId);

    Result updateLogistics(Logistics logistics);

    DisplayLogistics findLogisticsByLendId(int lendId);

    List<Company> findLogisticsCompany();
}
