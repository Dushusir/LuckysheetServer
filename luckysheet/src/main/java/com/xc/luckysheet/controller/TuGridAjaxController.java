package com.xc.luckysheet.controller;

import com.google.gson.Gson;
import com.xc.common.utils.JsonUtil;
import com.xc.luckysheet.entity.ResultModel;
import com.xc.luckysheet.entity.TuGridModel;
import com.xc.luckysheet.entity.UserModel;
import com.xc.luckysheet.service.TuGridService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 */
@Slf4j
@Controller
@RequestMapping(value = {"/api/tugrid","/datatu/api/tugrid"})
public class TuGridAjaxController {

    @Autowired
    private TuGridService tuGridService;

    //测试添加
    //@RequestMapping(value = "/test/add.do", produces = "text/html;charset=utf-8", method = RequestMethod.POST)
    //@ResponseBody
    @GetMapping("/test/add.do")
    public String test_add(HttpServletRequest request, TuGridModel model) {
        ResultModel resultModel = new ResultModel();
        resultModel.setResult("error");

        if(model==null){
            resultModel.setContent("参数错误");
            return new Gson().toJson(resultModel);
        }

        try {
            //UserModel userModel= SessionModelUtil.getLoginModel(request);
            UserModel userModel=new UserModel();
            userModel.setUser_id(1l);
            model.setGrid_creator(userModel.getUser_id());
            int i=tuGridService.insert(model);
            if(i>0){
                //返回操作成功的数据
                resultModel.setContent(JsonUtil.toJson(model));
                resultModel.setResult("success");
            }else{
                resultModel.setContent("操作失败");
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
            resultModel.setContent(ex.getMessage());
        }
        return  new Gson().toJson(resultModel);
    }
}
