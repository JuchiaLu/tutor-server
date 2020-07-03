package com.juchia.tutor.business.front.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 预约表
 * </p>
 *
 * @author juchia
 * @since 2019-12-19
 */
@Data
@ApiModel(description = "首页最新5个需求,")
public class NeedQuery1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
//    @ApiModelProperty(value = "ID")
//    private Long id;
//
//    /**
//     * 称呼 详情页展示 如张同学 必选
//     */
//    private String nickname;
//
//    /**
//     * 联系电话 教员中标后才展示,详情页面不展示 必选
//     */
//    private String phone;
//
//    /**
//     * 微信号 教员中标后才展示,详情页面不展示 可选
//     */
//    private String wechat;
//
//    /**
//     * QQ 教员中标后才展示,详情页面不展示 可选
//     */
//    private String qq;
//
//    /**
//     * 性别 1男 2女 详情页展示 必选
//     */
//    private Integer gender;
//
//    /**
//     * 教师性别要求 1男 2女 0不限 搜索条件 必选
//     */
    @ApiModelProperty(value = "教员性别要求",notes = "1男 2女 0不限")
    private Integer teacherGender;
//
//    /**
//     * 一级分类_外键 搜索条件 如小学 必选
//     */
    @ApiModelProperty(value = "一级学科分类ID",notes = "如小学")
    private Long firstCategoryId;
//
//    /**
//     * 二级分类_外键 搜索条件 如一年级 必选
//     */
    @ApiModelProperty(value = "二级学科分类ID",notes = "如一年级")
    private Long secondCategoryId;
//
//    /**
//     * 三级分类_外键 搜索条件 如数学 必选
//     */
    @ApiModelProperty(value = "三级学科分类ID",notes = "如数学")
    private Long thirdCategoryId;


    //    /**
//     * 城市_外键,搜索条件 必选
//     */
    @ApiModelProperty(value = "城市ID")
    private Long cityId;

//
//    /**
//     * 地区_外键,搜索条件 必选
//     */
    @ApiModelProperty(value = "地区ID")
    private Long areaId;
//
//    /**
//     * 学员类型_外键  字典表 如拔尖型 必选
//     */
//    private Long studentTypeId;
//
//    /**
//     * 教师类型_外键 字典表 如在读大学生 必选
//     */
//    private Long teacherTypeId;
//
//    /**
//     * 详细地址 详情页展示 必选
//     */
//    private String address;
//
//    /**
//     * 其他要求 可选
//     */
//    private String demand;
//
//    /**
//     * 可上课时间, 序列化后的对象 必选
//     */
//    private String teachDate;
//
//    /**
//     *  上课次数 必选
//     */
//    private Long frequency;
//
//    /**
//     *  每次上课小时 必选
//     */
//    private Long timeHour;
//
//    /**
//     *  每小时价格 必选
//     */
//    private BigDecimal hourPrice;
//
//
//    /**
//     *  状态
//     */
//    private Integer state;
//
//
    @ApiModelProperty(value = "排序字段",notes = "可选 小时价格:hourPrice,总价格:totalPrice,添加时间:createTime")
    private String  sort;

    @ApiModelProperty(value = "升序或降序",notes = "可选 升序:asc,降序:desc")
    private String order;

    //TODO 这里只是前期调试使用, 不支持以及不赞成在 RPC 调用中把 Wrapper 进行传输 wrapper 很重
    //正确的 RPC 调用姿势是写一个 DTO 进行传输,被调用方再根据 DTO 执行相应的操作
//    public QueryWrapper<Appoint> buildQueryWrapper(){
//        QueryWrapper<Appoint> queryWrapper = new QueryWrapper<>();
//
//        if(getFirstCategoryId()!=null){
//            queryWrapper.eq("first_category_id",getFirstCategoryId());
//        }
//        if(getSecondCategoryId()!=null){
//            queryWrapper.eq("second_category_id",getSecondCategoryId());
//        }
//        if(getThirdCategoryId()!=null){
//            queryWrapper.eq("third_category_id",getThirdCategoryId());
//        }
//        if(getAreaId()!=null){
//            queryWrapper.eq("area_id",getAreaId());
//        }
//        if(getTeacherGender()!=null){
//            queryWrapper.eq("teacher_gender",getTeacherGender());
//        }
//        if(getOrderBy()!=null && getOrderBy().equals("hourPrice")){
//            queryWrapper.orderByDesc("hour_price");
//        }
//        if(getOrderBy()!=null && getOrderBy().equals("totalPrice")){
//            queryWrapper.orderByDesc("total_price");
//        }
//        if(StringUtils.isNoneBlank(getNickname())){
//            queryWrapper.like("nickname",getNickname());
//        }
//        if(getState()!=null){
//            queryWrapper.eq("state",getState());
//        }
//        return queryWrapper;
//    }


}
