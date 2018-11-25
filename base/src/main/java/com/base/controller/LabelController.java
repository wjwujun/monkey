package com.base.controller;

import com.base.pojo.Label;
import com.base.service.LabelService;
import com.entity.PageResult;
import com.entity.Result;
import com.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping()
    public Result  findAll(){
        return  new Result(true,StatusCode.OK,"查询成功",labelService.findAll());

    }

    @GetMapping("/{labelId}")
    public Result finfById(@PathVariable("labelId") String labelId){
        return  new Result(true,StatusCode.OK,"查询成功",labelService.findById(labelId));
    }

    @PostMapping()
    public  Result save(@RequestBody Label label){
        labelService.save(label);
        return  new Result(true,StatusCode.OK,"添加成功");
    }

    @PutMapping("/{labelId}")
    public Result update(@PathVariable("labelId") String labelId,@RequestBody Label label ){
        label.setId(labelId);
        labelService.update(label);
        return  new Result(true,StatusCode.OK,"修改成功");
    }

    @DeleteMapping("/labelId")
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return  new Result(true,StatusCode.OK,"删除成功");
    }

    /*条件查询*/
    @PostMapping("/search")
    public Result findSearch(@RequestBody Label label){
        List<Label> list=labelService.search(label);
        return  new Result(true,StatusCode.OK,"条件查询成功",list);
    }

    /*分页查询*/
    @PostMapping("/search/{page}/{size}")
    public Result pageQuery(@RequestBody Label label,@PathVariable int page, @PathVariable int size){
        Page<Label> pageData=labelService.pageQuery(label,page,size);
        return  new Result(true,StatusCode.OK,"分页查询成功",new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }



}
