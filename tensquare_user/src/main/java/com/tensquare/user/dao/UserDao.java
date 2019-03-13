package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    //根据手机号查询用户
    public User findByMobile(String mobile);

    /*
    * 更新粉丝数目
    * */
    @Modifying
    @Query("update User set fanscount = User .fanscount + ?2 where User .id = ?1")
    public void updateFans(String id ,int count);

    /*
    * 更新关注数
    * */
    @Modifying
    @Query("update User set followcount = User .followcount + ?2 where User .id = ?1 ")
    public void incFollowcount(String id,int followcount);
}
