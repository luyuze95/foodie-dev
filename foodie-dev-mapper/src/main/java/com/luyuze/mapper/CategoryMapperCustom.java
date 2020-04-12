package com.luyuze.mapper;


import com.luyuze.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryMapperCustom {

    List<CategoryVO> getSubCatList(Integer rootCatId);
}