package com.luyuze.service;

import com.luyuze.pojo.Items;
import com.luyuze.pojo.ItemsImg;
import com.luyuze.pojo.ItemsParam;
import com.luyuze.pojo.ItemsSpec;
import com.luyuze.pojo.vo.CommentLevelCountsVO;
import com.luyuze.utils.PagedGridResult;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品id查询详情
     * @param itemId
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格列表
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品的参数信息
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的各种评价的数目
     * @param itemId
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id和评价等级查询商品评价
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level,
                                       Integer page, Integer pageSize);
}
