package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
    private JwtUtil jwtUtil;
	@Autowired
    private HttpServletRequest request;

	/*
	* 增加粉丝数量
	* */
	@RequestMapping(value = "/infans/{userid}/{x}",method = RequestMethod.POST)
	public void addFansCount(@PathVariable String userid,@PathVariable int x){
		userService.updateFans(userid,x);
	}

	/*
	*增加关注数
	* */
	@RequestMapping(value = "/infollow/{userid}/{x}",method = RequestMethod.POST)
	public void incFollowcount(@PathVariable String userid,int x){
		userService.incFollowcount(userid,x);
	}

	/*
	* 用户登录
	* */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		 user = userService.findByMobileAndPassword(user.getMobile(), user.getPassword());
		if(user != null){
		    //如果user中有数据，就生成token来储存用户数据
            String token = jwtUtil.createJWT(user.getId(), user.getNickname(), "user");
            Map map = new HashMap();
            map.put("token",token);
            map.put("name",user.getNickname());//用户昵称
            map.put("avatar",user.getAvatar());//用户头像
            return new Result(true,StatusCode.OK,"登录成功",map);
		}else{
			return new Result(false,StatusCode.LOGINERROR,"用户名或密码错误");
		}
	}

	/*
	* 注册用户
	* */
	@RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
    public Result registerUser(@RequestBody User user, @PathVariable String code ){
	    userService.add(user,code);
	    Result result = new Result(true,StatusCode.OK,"注册成功");
	    return result;
    }

	/*
	* 发送验证码
	* */
	@RequestMapping(value = "/sendsms/{mobile}",method = RequestMethod.POST)
	public Result sendsms(@PathVariable String mobile){
		userService.sendSMS(mobile);
		Result result = new Result(true,StatusCode.OK,"验证码发送成功");
		return result;
	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
        Claims user_claims = (Claims) request.getAttribute("user_claims");
        if(user_claims == null){
            return new Result(false,StatusCode.LOGINERROR,"权限不足");
        }
        userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
