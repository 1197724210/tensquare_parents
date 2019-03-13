package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient("tensquare-user")
public interface UserClient {

    /*
    * 增加粉丝数
    * */
    @RequestMapping(value = "/user/incfans/{userid}/{x}",method = RequestMethod.POST)
    public void incFansCount(@PathVariable("userid") String userid,@PathVariable("x") int x);

    @RequestMapping(value = "/user/{userid}/{friendid}/{x}", method = RequestMethod.PUT)
    public void updatefanscountandfollowcount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);

}
