package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /*
    * 查询所有
    * */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        List<Spit> spitList = spitService.findAll();
        Result result = new Result(true, StatusCode.OK, "查询成功", spitList);
        return result;
    }

    /*
    * 根据id查询
    * */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        Spit spit = spitService.findById(id);
        Result result = new Result(true, StatusCode.OK, "查询成功", spit);
        return result;
    }

    /*
    * 增加数据
    * */
    @RequestMapping(method = RequestMethod.POST)
    public Result addSpit(Spit spit){
        spitService.add(spit);
        Result result = new Result(true, StatusCode.OK, "添加数据成功");
        return result;
    }

    /*
    * 修改数据
    * */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result updateSpit(@PathVariable Spit spit){
        spitService.update(spit);
        Result result = new Result(true, StatusCode.OK, "修改数据成功");
        return result;
    }

    /*
    * 根据id删除数据
    * */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        spitService.deleteById(id);
        Result result = new Result(true,StatusCode.OK,"删除数据成功");
        return result;
    }

    /*
    * 根据父类id查询吐槽列表
    * */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> spitPage = spitService.findByParentid(parentid, page, size);
        Result result = new Result(true, StatusCode.OK, "查询列表成功", spitPage);
        return result;
    }

    /*
    * 点赞
    * */
    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.PUT)
    public Result addThumbup(@PathVariable String id){
        //判断当前用户是否已经点赞，暂时先写死
        String userid = "111";
        if(redisTemplate.opsForValue().get("thumbup_" + userid + "_" + id) != null){
          return new Result(false,StatusCode.ERROR,"不能重复点赞");
        }
        spitService.addThumbup(id);
        redisTemplate.opsForValue().set("thumbup_" + userid + "_" + id,"1");
        Result result = new Result(true,StatusCode.OK,"点赞成功");
        return result;
    }
}
