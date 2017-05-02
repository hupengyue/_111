package com.xiyoutest.Service.message;
import com.xiyoutest.utils.CheckSumBuilder;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SendMessage {
    private static final String SERVER_URL="https://api.netease.im/sms/sendcode.action";//发送验证码的请求路径URL
    private static final String APP_KEY="b1424e33e55c3d17b04c80b3f78eaca1";//账号
    private static final String APP_SECRET="a6d92145e5f7";//密钥
    private static final String NONCE="123456";//随机数

    @Transactional
    public  String sendMsg(String phone) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost post = new HttpPost(SERVER_URL);//建立HttpPost对象

        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum= CheckSumBuilder.getCheckSum(APP_SECRET,NONCE,curTime);

        //设置请求的header
        post.addHeader("AppKey",APP_KEY);
        post.addHeader("Nonce",NONCE);
        post.addHeader("CurTime",curTime);
        post.addHeader("CheckSum",checkSum);
        post.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        System.out.println("验证码内容: "+checkSum);

        //设置请求参数
        List<NameValuePair> nameValuePairs =new ArrayList();//建立一个NameValuePair数组，java向url发送post请求，在发送post请求时用于存储欲发送的参数
        nameValuePairs.add(new BasicNameValuePair("mobile",phone));//添加参数

        post.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));//设置编码

        //执行请求
        HttpResponse response=httpclient.execute(post);//发送Post，并返回一个HttpResponse对象
        String responseEntity= EntityUtils.toString(response.getEntity(),"utf-8");

        //判断是否发送成功，发送成功返回true
        String code= JSON.parseObject(responseEntity).getString("code");
        if (code.equals("200")){
            return "success";
        }
        return "error";
    }
}

