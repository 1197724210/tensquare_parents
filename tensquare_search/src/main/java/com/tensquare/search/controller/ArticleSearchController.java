package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/article")
public class ArticleSearchController {
    @Autowired
    private ArticleSearchService articleSearchService;

    /*
    * 添加文章
    * */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleSearchService.add(article);
        return new Result(true,StatusCode.OK,"添加文章成功");
    }

    /*
    * 根据关键字检索信息
    * */
    @ResponseBody
    @RequestMapping(value = "/{key}/{page}/{size}",method = RequestMethod.GET)
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        Page<Article> articlePage = articleSearchService.findByTitleLike(key,page,size);
        Result result = new Result(true,StatusCode.OK,"查询成功"
                ,new PageResult<Article>(articlePage.getTotalElements(),articlePage.getContent()));
        return result;
    }
}
