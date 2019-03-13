package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;
    @Autowired
    private IdWorker idWorker;

    //添加文章
    public void add(Article article){
        article.setId(idWorker.nextId() + "");
        articleSearchDao.save(article);
    }

    /*
    * 根据关键字检索信息
    * */
    public Page<Article> findByTitleLike(String key, int page, int size){
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Article> articlePage = articleSearchDao.findByTitleOrContentLike(key, key,pageable);
        return articlePage;
    }
}
