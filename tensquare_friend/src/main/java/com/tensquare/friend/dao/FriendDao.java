package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {
    /*
    * 根据用户ID与被关注用户ID查询记录个数
    * */
    @Query("select count(f) from Friend f where userid = ?1 and friendid = ?2")
    public int selectCount(String userid,String friendid);

    /*
    * 更新为互相喜欢
    * */
    @Query(value = "update tb_friend set islike = ?3 where userid = ?1 and friendid = ?2",nativeQuery = true)
    public void updateLike(String userid,String friendid,String islike );

    /*
    * 删除好友
    * */
    @Query(value = "delete from tb_friend where userid = ?1 and friendid = ?2",nativeQuery = true)
    public void deleteFriend(String userid,String friendid);
}
