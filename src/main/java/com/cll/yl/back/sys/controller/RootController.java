package com.cll.yl.back.sys.controller;

import com.cll.yl.back.common.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/8 16:43
 */
@Controller
public class RootController {

    private static final Logger logger = LoggerFactory.getLogger(RootController.class);

    @RequestMapping(value = "/")
    public String root() {
        logger.info("------root");
        return "index";
    }
}
