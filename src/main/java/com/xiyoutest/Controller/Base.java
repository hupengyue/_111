package com.xiyoutest.Controller;

import com.xiyoutest.Service.ArticleService;
import com.xiyoutest.Service.CategoryService;
import com.xiyoutest.Service.CoverPhotoService;
import com.xiyoutest.utils.BaseDao;

import javax.annotation.Resource;

/**
 * Created by hpyba on 2017/4/17.
 */
public class Base {
    @Resource
    protected ArticleService articleService;
    @Resource
    protected BaseDao _baseDao;
    @Resource
    protected CoverPhotoService coverPhotoService;
    @Resource
    protected CategoryService categoryService;
}
