package com.cll.yl.back.sys.controller;

import com.cll.yl.back.common.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述信息:
 * 测试程序
 * @author CLL
 * @version 1.0
 * @date 2019/3/8 16:39
 */
@Controller
@RequestMapping(value = "/sys")
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @ResponseBody
    @RequestMapping(value = "/test")
    public AjaxResult test() {
        logger.info("------test");
        return AjaxResult.success();
    }
}
