package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
//解决各个微服务之间的跨域问题
@CrossOrigin
@RefreshScope
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;
    @Value("${ip}")
    private String ip;

    //查询所有
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        System.out.println(ip);
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    //根据id查询
    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String labeId){
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(labeId));
    }

    //插入数据
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true,StatusCode.OK,"插入成功");
    }

    //更新数据
    @RequestMapping(value = "{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"更新数据成功");
    }

    //删除数据
    @RequestMapping(value = "{labelId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String labelId,@RequestBody Label label){
        labelService.deleteById(labelId);
        return new Result(true,StatusCode.OK,"删除数据成功");
    }
}
