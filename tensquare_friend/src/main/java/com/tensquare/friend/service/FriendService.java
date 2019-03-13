package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserClient userClient;

    @Transactional
    public int addFriend(String userid,String friendid){
        //判断用户，如果已经添加了这个好友，则不在进行任何操作
        if(friendDao.selectCount(userid,friendid) > 0){
            return 0;
        }
        Friend friend = new Friend();
        friend.setFriendid(friendid);
        friend.setUserid(userid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断对方是否喜欢你
        if( friendDao.selectCount(friendid,userid) > 0){
            userClient.incFansCount(userid,1);//增加自己的关注度
            userClient.incFansCount(friendid,1);//增加粉丝的关注度
        }
        return 1;
    }

    /*
     * 向不喜欢列表中添加记录
     * */
    public void addNoFriend(String userid,String friendid){
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }

    /*
    * 删除好友
    * */
    @Transactional
    public void deleteFriend(String userid,String friendid){
        friendDao.deleteFriend(userid,friendid);
        friendDao.updateLike(userid,friendid,"0");
        userClient.incFansCount(userid,-1);//减少自己的关注度
        userClient.incFansCount(friendid,-1);//减少粉丝的关注度
        addNoFriend(userid,friendid);//向不喜欢列表中添加
    }
}
