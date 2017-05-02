package com.xiyoutest.Controller;


import com.xiyoutest.Service.message.CheckMessage;
import com.xiyoutest.Service.message.SavePhone;
import com.xiyoutest.Service.message.SendMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ygria on 2017/3/11.
 */
@RestController//结合了@Controller和@ResponseBody的功能，Spring将会为该控制器的所有处理方法应用消息转换功能，不必为每个方法都添加@ResponseBody
public class getPhoneNumber {
//    @Resource
//    BaseDao<PhonenumEntity> baseDao;
    @Resource
    CheckMessage checkMessage;
    @Resource
    SendMessage sendMessage;
    @Resource
    SavePhone savePhone;
//    @Resource
//    GetPhoneList getPhoneList;
//
//    @ExceptionHandler
//    public Map e(RuntimeException e){
//        return MapTool.Map().put("state","10000");
//    }



    @RequestMapping("/sendMessage")
    public Map sendMessage(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "", required = false) String phoneNum) {
        System.out.println("getPhone方法被调用");

        Map<String,Object> map = new HashMap();
        SendMessage sendMessage = new SendMessage();

        if(phoneNum==null||phoneNum.equals("")){
           map.put("error",10002);
           return map;
        }else{
            try {
                String str = sendMessage.sendMsg(phoneNum);
                if ("success".equals(str)) {
                    System.out.println("发送成功！");
                    map.put("state", "0");

                } else {
                    System.out.println("发送失败！");
                    map.put("state", "40001");
                }
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return map;

        }
    }

    @RequestMapping("/savePhoneNum")
    public Map savePhoneNum(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(value = "", required = false) String phoneNum,
                            @RequestParam( value="", required=false )String checkCode)
                            //@RequestParam( value="", required=false )String url)
    {
        Map map = new HashMap();
        try {
            String str = checkMessage.checkMsg(phoneNum,checkCode);
            if("success".equals(str)){
                System.out.println("验证码验证成功！");
                map.put("state", "0");
                savePhone.savePhone(phoneNum);

            }else{
                System.out.println("验证码验证失败！");
                map.put("state", "30002");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return map;

    }
//
//    @RequestMapping("/getPhoneNum")
//    public Map show(){
//        return MapTool.Mapok().put("data",getPhoneList.getPhoneList());
//
//    }


}
