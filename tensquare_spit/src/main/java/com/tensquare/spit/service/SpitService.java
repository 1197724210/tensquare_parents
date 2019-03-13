package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /*
    查询全部记录
    */
    public List<Spit> findAll(){
        List<Spit> list = spitDao.findAll();
        return list;
    }

    /*
    * 根据id查询记录
    * */
    public Spit findById(String id){
        Spit spit = spitDao.findById(id).get();
        return spit;
    }

    /*
    * 发布吐槽
    * */
    public void add(Spit spit){
        spit.setId(idWorker.nextId()+"");
        spit.setPublishtime(new Date());
        spit.setVisits(0);//访问量
        spit.setShare(0);//分享数
        spit.setComment(0);//回复数
        spit.setThumbup(0);//点赞数
        spit.setState("1");
        //如果存在上级id，回复数增加1
        if( spit.getParentid() != null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /*
    * 修改
    * */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /*
    * 删除
    * */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /*
    * 根据父类id查询吐槽列表
    * */
    public Page<Spit> findByParentid(String parentid, int page,int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Page<Spit> spitPage = spitDao.findByParentid(parentid, pageRequest);
        return spitPage;
    }

    /*
    * 点赞
    * */
    public void addThumbup(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
