package com.xiyoutest.Controller;

import com.xiyoutest.model.Article;
import com.xiyoutest.utils.BaseDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hpyba on 2017/4/17.
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends Base{
    @Resource
    BaseDao baseDao;

    @RequestMapping("/add")
    public Map add(String author,
                   String title,
                   @RequestParam(required = false) Integer category_id,
                   @RequestParam(required = false) Integer styleId,
                   String text,
                   @RequestParam(required = false) Timestamp createTime ,
                   @RequestParam(required = false) Boolean isTop,
                   @RequestParam(required = false) List<String> photoes)
    {
        System.out.println("articleController_add() called!");

        //hpy_test：三图的形式，添加数据
        List<String> photos =new ArrayList<String>();
        photos.add("paddressllopk");
        photos.add("address2fr");
        photos.add("address3ws");

        Map<String, Object> result = new HashMap<>();
        result.put("State", "0");

        //检查照片数目和StyleId是否匹配
        switch (styleId){
            case 1:
                List<String> p1 = new ArrayList<String>();
                p1.add(photos.get(0));
                result.put("id",articleService.add(author,title,category_id,styleId,text,createTime,isTop,p1));
                break;
            case 2:
                List<String> p2 = new ArrayList<String>();
                p2.add(photos.get(0));
                result.put("id",articleService.add(author,title,category_id,styleId,text,createTime,isTop,p2));
                break;
            case 3:
                List<String> p3 = new ArrayList<String>();
                p3.add(photos.get(0));
                result.put("id",articleService.add(author,title,category_id,styleId,text,createTime,isTop,p3));
                break;

            case 4:
                if(photos.size() != 3)
                    throw new RuntimeException("ErrCode 205, styleId and photoNum cannot match");
                else
                    result.put("id",articleService.add(author,title,category_id,styleId,text,createTime,isTop,photos));
                break;
            case 5:
                result.put("id",articleService.add(author,title,category_id,styleId,text,createTime,isTop,new ArrayList<String>()));
                break;
                default:
                    throw new IllegalArgumentException("Unsupported styleId in database: " + styleId);
        }

        System.out.println(result);
        return result;
    }

    @RequestMapping("/delete")
    public Map delete(int id){
        Article ids = articleService.getId(id);
        if(ids == null) throw new RuntimeException();
        articleService.delete(ids);
        Map<String, Object> result = new HashMap<>();
        result.put("state","0");
        return result;
    }

    @RequestMapping("/update")
    public Map update(int id,String author, String title,
                      @RequestParam(required = false) Integer category_id,
                      @RequestParam(required = false) Integer styleId,
                      String text,
                      Timestamp createTime , Boolean isTop,
                      @RequestParam(required = false) List<String> photos)
    {
        articleService.update(id,author,title,category_id,styleId,text,createTime,isTop,photos);
        Map<String, Object> result = new HashMap<>();
        result.put("state", "0");
        return result;

    }

    @RequestMapping("/get")
    //rows每页显示数量
    public Map get(Integer page, Integer total,Integer rows,
                   Integer category_id,Boolean isRecommend,
                   String keyword,Integer tag_id,Integer article_id,
                   Boolean isTop, String order){
        if(page == null) page = 0;
        if(rows == null ) rows = 10;
        Map<String , Object> result =new HashMap<>();
        result.put("state","0");
        Map articleDetail = articleService.get(page,total,rows,tag_id,
                category_id,isRecommend,keyword,article_id,isTop,order);
        result.put("data", (ArrayList<Article>)articleDetail.get(page));
        result.put("pagecount", articleDetail.get("pagecount"));

        //测试：输出结果
        System.out.println(result);
        return result;
    }


    //hpytest:
    @RequestMapping("/test001")
    public Map test001(Integer article_id){
        Map<String, Object> result = new HashMap<>();
        result.put("state", "0");
        result.put("returned result",articleService.test001(article_id));
        return result;
    }
    @RequestMapping("/newAdd")
    public Map newAdd(String author,String title, String text){
        Map<String ,Object> result = new HashMap<>();
        result.put("state","0");
        result.put("returned result",articleService.newAdd(author,title,text));
        return result;

    }
}
