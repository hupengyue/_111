package com.xiyoutest.Controller;
import com.xiyoutest.utils.BaseDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hpyba on 2017/4/28.
 */
@RestController
@RequestMapping("/coverphoto")
public class CoverPhotoController extends Base{
    @Resource
    BaseDao baseDao;

    @RequestMapping("/add")
    public Map add(String location){
        if(location == null) throw new RuntimeException("ErrCode 204,parameter 'location' cannot be null");
        Map<String, Object> result = new HashMap<>();
        result.put("state", "0");
        result.put("id",coverPhotoService.add(location));
        return result;

    }

}
