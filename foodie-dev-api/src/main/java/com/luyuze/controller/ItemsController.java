package com.luyuze.controller;

import com.luyuze.pojo.*;
import com.luyuze.pojo.vo.CommentLevelCountsVO;
import com.luyuze.pojo.vo.ItemInfoVO;
import com.luyuze.service.ItemService;
import com.luyuze.utils.MyJsonResult;
import com.luyuze.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public MyJsonResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return MyJsonResult.errorMsg(null);
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);
        return MyJsonResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public MyJsonResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return MyJsonResult.errorMsg(null);
        }
        CommentLevelCountsVO countsVO = itemService.queryCommentCounts(itemId);
        return MyJsonResult.ok(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public MyJsonResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "商品评价等级")
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "页数")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页个数")
            @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return MyJsonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);
        return MyJsonResult.ok(grid);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public MyJsonResult search(
            @ApiParam(name = "keywords", value = "搜索关键字")
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "搜索的排序规则")
            @RequestParam String sort,
            @ApiParam(name = "page", value = "页数")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页个数")
            @RequestParam Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);
        return MyJsonResult.ok(grid);
    }

    @ApiOperation(value = "根据分类id搜索商品列表", notes = "根据分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public MyJsonResult catItems(
            @ApiParam(name = "catId", value = "搜索关键字", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "搜索的排序规则")
            @RequestParam String sort,
            @ApiParam(name = "page", value = "页数")
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页个数")
            @RequestParam Integer pageSize) {
        if (catId == null) {
            return MyJsonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult grid = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return MyJsonResult.ok(grid);
    }
}
