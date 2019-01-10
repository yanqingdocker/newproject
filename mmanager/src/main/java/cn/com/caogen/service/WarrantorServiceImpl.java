package cn.com.caogen.service;

import cn.com.caogen.entity.Warrantor;
import cn.com.caogen.mapper.WarrantorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/7
 */
@Service
public class WarrantorServiceImpl implements IWarrantorService {
    @Autowired
    private WarrantorMapper warrantorMapper;
    @Override
    public void add(Warrantor warrantor) {
        warrantorMapper.add(warrantor);
    }

    @Override
    public List<Warrantor> queryAll() {
        return warrantorMapper.queryAll();
    }

    @Override
    public void update(Warrantor warrantor) {
        warrantorMapper.update(warrantor);
    }

    @Override
    public Warrantor queryById(int id) {
        return warrantorMapper.queryById(id);
    }

    @Override
    public void deleteById(int id) {
        warrantorMapper.deleteById(id);
    }
}
