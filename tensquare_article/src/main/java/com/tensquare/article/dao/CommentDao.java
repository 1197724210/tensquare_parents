package com.tensquare.article.dao;

import com.tensquare.article.pojo.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDao extends MongoRepository<Comment,String> {

    /*
    * 根据文章id查询评论列表
    * */
    public List<Comment> findByArticleid(String articleid);
}
