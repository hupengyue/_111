package com.xiyoutest.Service;

import com.xiyoutest.Controller.Base;
import com.xiyoutest.model.CoverPhoto;
import com.xiyoutest.utils.BaseDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hpyba on 2017/4/24.
 */
@Service
public class CoverPhotoService extends Base{
    @Resource
    BaseDao<CoverPhoto> baseDao;


    @Transactional
    public Integer add(String location){
        CoverPhoto coverPhoto = new CoverPhoto();
        coverPhoto.setLocation(location);
        baseDao.save(coverPhoto);
        return coverPhoto.getId();
    }

    @Transactional
    public CoverPhoto get (String location){
        String hql = "from CoverPhoto cp where cp.location=:location";
        Map map = new HashMap(){
            {
                put("location",location);
            }
        };
        CoverPhoto coverPhoto=(CoverPhoto) baseDao.get(hql,map);
        return  coverPhoto;
    }
}
