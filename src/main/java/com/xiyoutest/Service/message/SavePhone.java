package com.xiyoutest.Service.message;

import com.xiyoutest.model.User;
import com.xiyoutest.utils.BaseDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hpyba on 2017/4/14.
 */

@Service
public class SavePhone {
    @Resource
    BaseDao baseDao;
    @Transactional
    public Map savePhone(String phoneNum){
        User user = new User();
        Map map = new HashMap();
        user.setName(phoneNum);
        baseDao.save(user);
        map.put("state","0");
        return map;

    }
}
