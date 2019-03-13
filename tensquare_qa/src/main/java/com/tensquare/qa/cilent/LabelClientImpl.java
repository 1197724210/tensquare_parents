package com.tensquare.qa.cilent;

import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result findById(String labelId) {
        return new Result(false,StatusCode.ERROR,"熔断器断了");
    }
}
