package cn.com.caogen.mapper;

import cn.com.caogen.entity.Operation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/10
 */
@Repository
public interface OperaMapper {
    public void add(Operation operation);

    public List<Operation> queryAll();

    List<Operation> queryCondition(Map<String, Object> parmMap);

    List<Operation> queryByDate(Map<String, Object> parmMap);

    List<Map<String, Object>> queryoperatype(Map<String, Object> parmMap);

    List<Map<String, Object>> queryoperacount(Map<String, Object> parmMap);

    List<Operation> queryScope(Map<String, Object> parmMap);

    Operation queryById(int id);
    void update(Operation operation);

    int queryScopeCount(Map<String, Object> parmMap);

    int queryConditionCount(Map<String, Object> parmMap);

    List<Operation> queryByOrderNum(String snumber);

    void delete(String snumber);
}
