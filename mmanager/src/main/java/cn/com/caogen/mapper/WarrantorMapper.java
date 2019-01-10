package cn.com.caogen.mapper;

import cn.com.caogen.entity.Warrantor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/7
 */
@Repository
public interface WarrantorMapper {
    void add(Warrantor warrantor);
    List<Warrantor> queryAll();

    void update(Warrantor warrantor);

    Warrantor queryById(int id);

    void deleteById(int id);
}
