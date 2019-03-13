package com.tensquare.friend.controller;

import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController  {

    @Autowired
    private FriendService friendService;
    @Autowired
    private HttpServletRequest request;

    /*
    * 添加好友
    * */
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
        Claims user_claims = (Claims) request.getAttribute("user_claims");
        if(user_claims == null){
            return new Result(false,StatusCode.ACCESSERROR,"无权限访问");
        }
        //如果是喜欢
        if(type.equals("1")){
            if(friendService.addFriend(user_claims.getId(),friendid) == 0){
                return new Result(false,StatusCode.REPERROR,"已经添加此好友");
            }
        }else {
            //不喜欢
            friendService.addNoFriend(user_claims.getId(),friendid);//向不喜欢列表中添加记录
        }
        return new Result(true,StatusCode.OK,"操作成功");
    }

    /*
    * 删除好友
    * */
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result remove(@PathVariable String friendid){
        Claims user_claims = (Claims) request.getAttribute("user_claims");
        if(user_claims == null){
            return new Result(false,StatusCode.ACCESSERROR,"无权限访问");
        }
        friendService.deleteFriend(user_claims.getId(),friendid);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
