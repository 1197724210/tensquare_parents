package com.tensquare.article.service;

import com.tensquare.article.dao.CommentDao;
import com.tensquare.article.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;

    /*
    * 新增评论
    * */
    public void add(Comment comment){
        comment.set_id(idWorker.nextId()+"");
        commentDao.save(comment);
    }

    /*
    * 根据文章id查询评论列表
    * */
    public List<Comment> findByArticle(String article){
        List<Comment> commentList = commentDao.findByArticleid(article);
        return commentList;
    }
}
