package com.tensquare.article.controller;

import com.tensquare.article.pojo.Comment;
import com.tensquare.article.service.CommentService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /*
    * 新增评论
    * */
    @RequestMapping(method = RequestMethod.PUT)
    public Result add(@RequestBody Comment comment){
        commentService.add(comment);
        Result result = new Result(true,StatusCode.OK,"提交成功");
        return result;
    }

    /*
    * 根据文章id查询评论列表
    * */
    @RequestMapping(value = "/article/{articleid}",method = RequestMethod.GET)
    public Result findByArticle(@PathVariable String articleid){
        List<Comment> commentList = commentService.findByArticle(articleid);
        Result result = new Result(true,StatusCode.OK,"查询评论列表成功",commentList);
        return result;
    }
}
