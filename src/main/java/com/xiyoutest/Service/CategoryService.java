package com.xiyoutest.Service;

import com.xiyoutest.Controller.Base;
import com.xiyoutest.model.Category;
import com.xiyoutest.utils.BaseDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by hpyba on 2017/4/24.
 */
@Service
public class CategoryService extends Base{
    @Resource
    BaseDao<Category> baseDao;

    @Transactional
    public Category get (Integer id){
        Category category =(Category) _baseDao.get(Category.class,id);
//        String hql = "from Category c where c.id =:id";
//        Map map = new HashMap(){
//            {
//                put("id",id);
//            }
//        };
//        Category category = (Category) baseDao.get(hql,map);
        return category;
    }

}
