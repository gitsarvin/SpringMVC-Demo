package top.mowang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import top.mowang.service.HelloService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author arvin
 * @date 2023-03-16 20:48
 **/
@Controller
public class HelloRestController {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    HelloService helloService;


    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return helloService.hello();
    }

    @RequestMapping("getAllUrl")
    @ResponseBody
    public List<Map<String, String>> getAllUrl() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        // 获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        List<Map<String, String>> list = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
            Map<String, String> map1 = new HashMap<>(4);
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition p = info.getPatternsCondition();
            assert p != null;
            for (String url : p.getPatterns()) {
                map1.put("url", url);
            }
            // 类名
            map1.put("className", method.getMethod().getDeclaringClass().getName());
            // 方法名
            map1.put("method", method.getMethod().getName());
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            for (RequestMethod requestMethod : methodsCondition.getMethods()) {
                map1.put("type", requestMethod.toString());
            }

            list.add(map1);
        }

        return list;
    }

}
