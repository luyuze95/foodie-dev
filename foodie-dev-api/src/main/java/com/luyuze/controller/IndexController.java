package com.luyuze.controller;

import com.luyuze.enums.YesOrNo;
import com.luyuze.pojo.Carousel;
import com.luyuze.pojo.Category;
import com.luyuze.pojo.vo.CategoryVO;
import com.luyuze.service.CarouselService;
import com.luyuze.service.CategoryService;
import com.luyuze.utils.MyJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public MyJsonResult carousel() {
        List<Carousel> result = carouselService.queryAll(YesOrNo.YES.type);
        return MyJsonResult.ok(result);
    }

    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public MyJsonResult cats() {
        List<Category> result = categoryService.queryAllRootLevelCat();
        return MyJsonResult.ok(result);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public MyJsonResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return MyJsonResult.errorMsg("分类不存在");
        }
        List<CategoryVO> result = categoryService.getSubCatList(rootCatId);
        return MyJsonResult.ok(result);
    }
}
