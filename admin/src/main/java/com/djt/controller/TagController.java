package com.djt.controller;

import com.djt.domain.ResponseResult;
import com.djt.domain.dto.TagListDto;
import com.djt.domain.vo.PageVo;
import com.djt.domain.vo.TagVo;
import com.djt.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Resource
    TagService tagService;
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto ){
        System.out.println("返回数据");
        return tagService.pagTagService(pageNum,pageSize,tagListDto);
    }
    @PostMapping()
    public ResponseResult addTag(@RequestBody TagListDto addTag){
        return tagService.addTag(addTag);
    }
    @DeleteMapping("{id}")
    public ResponseResult deleteTag(@PathVariable String id){
        return tagService.deleteTag(id);
    }
    @GetMapping("{id}")
    public ResponseResult getTag(@PathVariable String id){
        return tagService.getTag(id);
    }
    @PutMapping()
    public ResponseResult UpdateTag(TagVo tagVo){
        return tagService.UpdateTag(tagVo);
    }
    @GetMapping("/listAllTag")
    public ResponseResult getAllTag(){
        return tagService.getAllTag();
    }
}
