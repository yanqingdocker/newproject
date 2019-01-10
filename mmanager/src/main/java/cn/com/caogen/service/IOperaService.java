package cn.com.caogen.service;

import cn.com.caogen.entity.Operation;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/8
 */
public interface IOperaService {
    void add(Operation operation);

    List<Operation> queryAll(Map<String, Object> parmMap);

    List<Operation> queryAll(Map<String, Object> parmMap, String servicebranch);

    List<Operation> queryByDate(Map<String, Object> parmMap);

    List<Map<String, Object>> queryoperatype(int date, String servicebranch);

    List<Map<String, Object>> queryoperacount(int date, String servicebranch);

    String queryScope(Map<String, Object> parmMap);

    int datarecover(String snumber);

    int queryScopeCount(Map<String, Object> parmMap);

    int queryConditionCount(Map<String, Object> parmMap, String servicebranch);

    Operation queryById(int id);

    void update(Operation operation);
}
