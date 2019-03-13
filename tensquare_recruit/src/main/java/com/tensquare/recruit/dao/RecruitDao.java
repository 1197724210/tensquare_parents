package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    //查询推荐职位列表
    public List<Recruit> findTop6ByStateOrderByCreatetime(String state);

    //查询最新职位
    public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
