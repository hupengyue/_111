package com.xiyoutest.utils;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BaseDao<T> {

    @Resource
    private SessionFactory sessionFactory;
    @Resource
    private ServletContext servletContext;
    @Resource
    private HttpServletRequest request;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 获取当前Session对象
     *
     * @return
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 保存对象
     */

    public void save(T entity) {
        this.getCurrentSession().save(entity);
    }

    /**
     * 删除对象
     */

    public void delete(T entity) {
        this.getCurrentSession().delete(entity);
    }

    /**
     *
     */

    public void update(T entity) {
        this.getCurrentSession().update(entity);
    }

    /**
     * 合并
     */

    public void merge(T entity) {
        this.getCurrentSession().merge(entity);
    }

    /**
     * 保存
     */

    public void saveOrUpdate(T entity) {
        this.getCurrentSession().saveOrUpdate(entity);
    }

    /**
     * ID加载对象
     */
    @SuppressWarnings("unchecked")
    public T load(Class<T> c, Serializable id) {
        return (T) this.getCurrentSession().load(c, id);
    }

    /**
     * ID获取对象
     */
    @SuppressWarnings("unchecked")
    public T get(Class<T> c, Serializable id) {
        return (T) this.getCurrentSession().get(c, id);
    }

    /**
     * HQL语句获取对象
     */
    public T get(String hql) {
        return this.get(hql, null);
    }

    /**
     * HQL语句和参 获取对象
     */
    public T get(String hql, Map<String, Object> params) {
        return this.get(hql, 0, params);
    }

    public T get(String hql, int position, Map<String, Object> params) {
        List<T> list = this.find(hql, position, 1, params);
        if (list != null && list.size() > 0) {
            // 返回第一个对象
            return list.get(0);
        }
        return null;
    }

    public T get(String hql, int position) {
        return this.get(hql, position, null);
    }

    /**
     * HQL 询  集
     *
     * @param hql
     * @return
     */
    public List<T> find(String hql) {
        return find(hql, null);
    }

    /**
     * 询对象集合
     */
    @SuppressWarnings("unchecked")
    public List<T> find(String hql, Map<String, Object> params) {
        return find(hql, 0, 0, params);
    }

    /**
     * 分页   集
     */
    @SuppressWarnings("unchecked")
    public List<T> find(String hql, Integer start, Integer rows,
                        Map<String, Object> params) {

        // 创建Query
        Query query = this.getCurrentSession().createQuery(hql);

        // 如果参数非空
        if (params != null && params.size() > 0) {
            // 设置参数
            this.setParameters(query, params);
        }

        List<T> list;
        if (rows == null || rows < 0) rows = 0;
        if (start == null || start < 0) start = 0;
        if (rows == 0)
            list = query.setFirstResult(start).list();
        else
            list = query.setFirstResult(start).setMaxResults(rows).list();
        if (list == null) list = new ArrayList<>();
        return list;
    }

    /**
     * 参   Select count(*) from
     */
    public Long count(String hql) {
        // 通过另一个方法实现^_^
        return this.count(hql, null);
    }

    /**
     * 带参   Select count(*) from
     */
    public Long count(String hql, Map<String, Object> params) {

        // 给传递过来的hql添加前缀内容
        hql =
                "select count(*) " + hql;

        // 创建Query
        Query query = this.getCurrentSession().createQuery(hql);

        // 如果参数非空
        if (
                params != null && params.size() > 0) {
            // 设置参数
            this.setParameters(query, params);
        }

        // 返回查询结果
        return (Long) query.uniqueResult();
    }

    /**
     * 行HQL
     */
    public Integer executeHql(String hql) {
        // 通过另一个方法实现^_^
        return this.executeHql(hql, null);
    }

    /**
     * 行HQL
     */
    public Integer executeHql(String hql, Map<String, Object> params) {

        // 创建Query
        Query query = this.getCurrentSession().createQuery(hql);

        // 如果参数不为null
        if (params != null && params.size() > 0) {
            // 设置参数
            this.setParameters(query, params);
        }

        // 执行HQL
        return query.executeUpdate();
    }

    /**
     * Query赋值
     *
     * @param query  Hibernate Query对象
     * @param params Map集合参
     */
    private void setParameters(Query query, Map<String, Object> params) {

        // 获取所有Key
        Set<String> keys = params.keySet();

        // 循环取值,赋值
        for (String key : keys) {

            // 获取到参数中的对象
            Object obj = params.get(key);

            // 判断参数类型
            if (obj instanceof Collection<?>) {// 是集合
                // System.out.println("参数为集合的大小为:" + ((Collection<?>)
                // obj).size());
                query.setParameterList(key, (Collection<?>) obj);
            } else if (obj instanceof Object[]) {// 是数组
                // System.out.println("参数为数姐的大小为:" + ((Object[]) obj).length);
                query.setParameterList(key, (Object[]) obj);
            } else {// 普通对象
                // System.out.println("普通对象");
                query.setParameter(key, obj);
            }
        }
    }

    public void clear() {
        Session session = this.getCurrentSession();
        session.flush();
        session.clear();
    }

    public static final Pattern pattern = Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})$");

    public String getIp(HttpServletRequest request) {//获得客户端的IP,如果有更好的方法可以直接代替
        String ip = request.getHeader("x-forwarded-for");
        if (!StringUtils.isEmpty(ip)) {
            Matcher matcher = pattern.matcher(ip);
            if (matcher.find())
                ip = matcher.group(1);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
                || "null".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
                || "null".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)
                || "null".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1"))
            ip = "127.0.0.1";
        return ip;
    }

    public String getIp() {
        return getIp(this.request);
    }

    @PostConstruct
    void initLogger() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("log4j.properties"));
            String realPath = servletContext.getRealPath("/WEB-INF/logs/");
            properties.setProperty("log4j.appender.out.File", realPath + properties.getProperty("log4j.appender.out.File"));
            properties.setProperty("log4j.appender.err.File", realPath + properties.getProperty("log4j.appender.err.File"));
            PropertyConfigurator.configure(properties);
        } catch (IOException e) {
        }
    }
}
