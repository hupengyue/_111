package com.xiyoutest.Service;

import com.xiyoutest.Controller.Base;
import com.xiyoutest.model.*;
import com.xiyoutest.utils.BaseDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by hpyba on 2017/4/17.
 */
@Service
public class ArticleService extends Base{
    @Resource
    BaseDao<Article> baseDao;

    public Article getId(int id)
    {
        return baseDao.get(Article.class,id);
    }

    @Transactional
    public int add(String author, String title, int category_id, int styleId, String text,
                   Timestamp createTime , Boolean isTop, List<String> photos)
    {
//        System.out.println("articleService_add() called");

        //向cover_photo表中添加图片数据，并保存返回的photo的id
        List<Integer> coverPhotoId = new ArrayList<Integer>();
        for(int i = 0; i<photos.size();++i){
            coverPhotoId.add(coverPhotoService.add(photos.get(i)));
            //hpy_test：
            System.out.println("coverPhotoId =" + coverPhotoId.get(i));
        }


        Category category = (Category) _baseDao.get(Category.class, category_id);
        if(category == null) throw new RuntimeException("ErrCode:206, parameter category is wrong");

        Style style =(Style) _baseDao.get(Style.class,styleId);
        if(style == null) throw new RuntimeException("ErrCode: 207, parameter styleId is wrong");

        Article article = new Article();
        article.setAuthor(author);
        article.setTitle(title);
        article.setCategory(category);
        article.setStyle(style);
        article.setText(text);

        if(createTime != null) //createTime = new Timestamp(System.currentTimeMillis());
            article.setCreateTime(createTime);
        if (isTop != null) article.setTop(isTop);

        //从cover_photo表中取出对应的photo，并将其id保存到article表中
        switch (photos.size()){
            case 0:
                break;
            case 1:
                CoverPhoto photo1 = (CoverPhoto) _baseDao.get(CoverPhoto.class,coverPhotoId.get(0));
                if(photo1 == null)
                    throw new RuntimeException("Error: code 1");
                article.setCover_photo1(photo1);
                break;
            case 3:
                CoverPhoto photo3_1 = (CoverPhoto) _baseDao.get(CoverPhoto.class,coverPhotoId.get(0));
                CoverPhoto photo3_2 = (CoverPhoto) _baseDao.get(CoverPhoto.class,coverPhotoId.get(1));
                CoverPhoto photo3_3 = (CoverPhoto) _baseDao.get(CoverPhoto.class,coverPhotoId.get(2));
                if(photo3_1 == null ||photo3_2 == null ||photo3_3 == null )
                    throw new RuntimeException("Error: code 3");
                article.setCover_photo1(photo3_1);
                article.setCover_photo2(photo3_2);
                article.setCover_photo3(photo3_3);
                break;
        }

        baseDao.save(article);
        return article.getId();
    }
    @Transactional
    //服务层函数，返回void
    public void delete(Article id){
        baseDao.delete(id);

    }
    @Transactional
    public void update(int id,String author, String title, Integer category_id, Integer styleId, String text,
                       Timestamp createTime , Boolean isTop,List<String> photoes)
    {
        //测试用的List<String> photos
        List<String> photos = new ArrayList<String>();
        photos.add("newAddressjitfdtr");
////        photos.add("newAddress2");
////        photos.add("newAddress3");

        Article newArticle = articleService.getId(id);
        if(newArticle == null) throw new RuntimeException();
        if(author != null) newArticle.setAuthor(author);
        if(title != null) newArticle.setText(title);


        if(category_id != null && Integer.valueOf(category_id)<= 5 && Integer.valueOf(category_id)>=1) {
            Category category = (Category)_baseDao.get(Category.class, category_id);
            newArticle.setCategory(category);
        }


        if(text != null) newArticle.setText(text);
        if(createTime != null) newArticle.setCreateTime(createTime);
        if(isTop != null) newArticle.setTop(isTop);
        //检查照片
        System.out.println(photos);
        int oldPhotoNum=0,newPhotoNum =0;
        int stylePhotoNumMatched = 0;
        if(photos == null){
            //获取文章原来含有几张图片
             if(newArticle.getCover_photo1() != null)
                 oldPhotoNum = 1;
             if(newArticle.getCover_photo2() != null)
                 oldPhotoNum = 2;
             if(newArticle.getCover_photo3() != null)
                 oldPhotoNum = 3;
             System.out.println("oldPhotoNum: " + oldPhotoNum);
             if(styleId != null) {
                 if(styleId_photoNum_match(styleId, oldPhotoNum) ==false)
                     throw new RuntimeException("ErrCode 997");
                 else stylePhotoNumMatched =1;
             }
             //styleId ==null
             else stylePhotoNumMatched =1;
        }
        //photos != null
        else{
            newPhotoNum = photos.size();
            if(styleId != null){
                //不匹配
                if(styleId_photoNum_match(styleId,newPhotoNum)==false)
                    throw new RuntimeException("ErrCOde 999");
                //匹配了
                stylePhotoNumMatched =1;
                //根据styleId来设置照片
                if(styleId ==1 ||styleId ==2 || styleId ==3) {

                    CoverPhoto p = (CoverPhoto) coverPhotoService.get(photos.get(0));
                    newArticle.setCover_photo1(p);
                }
                else if(styleId == 4){
                    CoverPhoto p3_1 = (CoverPhoto) coverPhotoService.get(photos.get(0));
                    newArticle.setCover_photo1(p3_1);
                    CoverPhoto p3_2 = (CoverPhoto) coverPhotoService.get(photos.get(1));
                    newArticle.setCover_photo2(p3_2);
                    CoverPhoto p3_3 = (CoverPhoto) coverPhotoService.get(photos.get(2));
                    newArticle.setCover_photo3(p3_3);
                }

            }
            //styleId ==null
            else{
                int oldStyleId = newArticle.getStyle().getId();
                //不匹配
                if(styleId_photoNum_match(oldStyleId,newPhotoNum) == false)
                    throw new RuntimeException("ErrCode 998");
                //匹配了
                stylePhotoNumMatched =1;
                //根据styleId来设置照片
                if(oldStyleId ==1 ||oldStyleId ==2 || oldStyleId ==3) {
                    CoverPhoto _p = (CoverPhoto) coverPhotoService.get(photos.get(0));
                    newArticle.setCover_photo1(_p);
                }
                else if(oldStyleId == 4){
                    CoverPhoto _p3_1 = (CoverPhoto) coverPhotoService.get(photos.get(0));
                    newArticle.setCover_photo1(_p3_1);
                    CoverPhoto _p3_2 = (CoverPhoto) coverPhotoService.get(photos.get(1));
                    newArticle.setCover_photo2(_p3_2);
                    CoverPhoto _p3_3 = (CoverPhoto) coverPhotoService.get(photos.get(2));
                    newArticle.setCover_photo3(_p3_3);
                }

            }
        }

        if(styleId != null && stylePhotoNumMatched ==1 && Integer.valueOf(styleId)<= 5 && Integer.valueOf(styleId)>=1){
            Style style = (Style) _baseDao.get(Style.class,styleId);
            newArticle.setStyle(style);
        }

        baseDao.update(newArticle);


    }
    @Transactional
    public Map get(Integer page, Integer total, Integer rows,
                   Integer tag_id,Integer category_id,Boolean isRecommend,
                             String keyword,Integer article_id,
                                Boolean isTop, String order)
    {
        Map result  = new HashMap();
        String hql = "from Article as a where 1=1 ";
        Map<String, Object> param = new HashMap<>();

        Set<Article> articleListByTag = new HashSet<>();

        if(tag_id != null){
            Tag tag = (Tag) _baseDao.get(Tag.class,tag_id);
//            articleListByTag = tag.getArticleList();
            hql +=" and a in (select A_T.article from Article_Tag as A_T where A_T.tag = :tag)";
            param.put("tag",tag);
        }

        if(category_id != null){
            Category category = (Category) _baseDao.get(Category.class,category_id);
            hql += " and a.category = :category";
            param.put("category",category);
        }

        if(isRecommend != null){
            hql += " and a.isRecommend = :isRecommend";
            param.put("isRecommend",isRecommend);
        }
        //在title，author字段查询关键字
        if(keyword != null){
            keyword = "%" + keyword +"%";
            hql += " and a.title like :keyword or a.author like :keyword";
            param.put("keyword",keyword);
        }


        if(article_id != null){
            hql += " and a.id = :article_id";
            param.put("article_id",article_id);
        }
        if(isTop != null){
            hql += " and a.isTop =:isTop";
            param.put("isTop",isTop);
        }

//        Article article = (Article) baseDao.get(hql,param);
        result.put(page, baseDao.find(hql,(page-1)*rows,rows,param));
        result.put("pagecount",Math.ceil((double)baseDao.count(hql,param)/rows));

        //测试：输出捕获的文章列表
        List<Article> articleList = (ArrayList<Article>)result.get(page);
        for(Article a : articleList){
        System.out.println(a.getId()+ " " +a.getAuthor());}

        return result;
    }

    public Map pagingResult(String hql,String countSql,Map params,int page,int rows){
        Map map = new HashMap();
        map.put(page,baseDao.find(hql,(page-1)*rows,rows,params));
        map.put("pageCount",Math.ceil((double)baseDao.count(countSql,params)/rows));//进一法，算出总页数
        return map;
    }

    //辅助函数，检查article 的styleId与location数组的元素数目是否匹配
    public  boolean styleId_photoNum_match(Integer styleId,Integer photoNum){
        if(styleId <1 || styleId >6) throw new RuntimeException("ErrCode: 203");

        boolean isMatch = true;
        if(styleId ==1 || styleId ==2 || styleId ==3)
        {if(photoNum !=1) isMatch = false;}
        else if(styleId ==4) {
            if(photoNum != 3) isMatch = false;
        }
        else if(styleId == 5)
            if(photoNum != 0) isMatch = false;

        return isMatch;
    }

    public Map test001(Integer article_id){
        Map<String, Object> result = new HashMap<>();
        result.put("state","0");
        Article newArticle = (Article) _baseDao.get(Article.class, article_id);
        Set<Article_Tag> article_tags = new HashSet<>();
        article_tags = newArticle.getArticle_tags();
        for(Article_Tag a_t : article_tags){
            System.out.println("tagName "+ a_t.getTag().getName());
        }
        return result;
    }

    public Map newAdd(String author,String title, String text){


        Map<String,Object> result = new HashMap<>();
        result.put("state","0");
        Comment newComment = new Comment();
        newComment.setText("this is comment");
        newComment.setPraised_times(10);

        Article newArticle = new Article();
        newArticle.setAuthor(author);
        newArticle.setTitle(title);
        newArticle.setText(text);
        newArticle.getComments().add(newComment);
        baseDao.save(newArticle);
        result.put("id",newArticle.getId());
        return result;
    }

}
