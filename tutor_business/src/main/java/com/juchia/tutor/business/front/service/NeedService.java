package com.juchia.tutor.business.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.common.entity.dto.NeedDTO;
import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.business.common.entity.po.Appoint;
import com.juchia.tutor.business.common.entity.po.Need;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.common.mapper.AppointMapper;
import com.juchia.tutor.business.common.mapper.NeedMapper;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.juchia.tutor.business.front.entity.query.NeedQuery1;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NeedService extends ServiceImpl<NeedMapper, Need> {


    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    AppointMapper appointMapper;

    public MyPage<NeedDTO> pageDTO(PageQuery pageQuery, NeedQuery1 needQuery) {
//      1 将我们的分页对象转换成mybatis plus 的
        Page<NeedDTO> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<NeedDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",3); //只有状态为审核通过的才展示
        if(needQuery.getCityId()!=null){
            queryWrapper.eq("city_id",needQuery.getCityId());
        }
        if(needQuery.getAreaId()!=null){
            queryWrapper.eq("area_id",needQuery.getAreaId());
        }
        if(needQuery.getTeacherGender()!=null){
            queryWrapper.eq("teacher_gender",needQuery.getTeacherGender());
        }
        if(needQuery.getFirstCategoryId()!=null){
            queryWrapper.eq("first_category_id",needQuery.getFirstCategoryId());
        }
        if(needQuery.getSecondCategoryId()!=null){
            queryWrapper.eq("second_category_id",needQuery.getSecondCategoryId());
        }
        if(needQuery.getThirdCategoryId()!=null){
            queryWrapper.eq("third_category_id",needQuery.getThirdCategoryId());
        }
//       排序
        if(StringUtils.isNotBlank(needQuery.getSort())){
            if(StringUtils.equals("totalPrice",needQuery.getSort())){
                queryWrapper.orderBy(true,StringUtils.equals("asc",needQuery.getOrder()),"total_price");
            } else if(StringUtils.equals("hourPrice",needQuery.getSort())){
                queryWrapper.orderBy(true,StringUtils.equals("asc",needQuery.getOrder()),"hour_price");
            } else if(StringUtils.equals("createTime",needQuery.getSort())){
                queryWrapper.orderBy(true,StringUtils.equals("asc",needQuery.getOrder()),"create_time");
            }
        }

//        3调用Mapper
        IPage<NeedDTO> needDTOIPage = getBaseMapper().selectPageDTO(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<NeedDTO> myPage = BeanCopyUtils.copyBean(needDTOIPage, MyPage.class);
        myPage.setPages(needDTOIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    public NeedDTO getDTOById(Long id) throws ResourceNotFondException {
        NeedDTO needDTO = getBaseMapper().selectDTOById(id);
        if(needDTO==null){
            throw new ResourceNotFondException("资源不存在");
        }
        return needDTO;
    }

    public MyPage<TeacherDTO> pageTeachersForNeed(PageQuery pageQuery, Long id) {
        Page<NeedDTO> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//        根据需求id 查预约表 并取得全部老师(用户)ids
        QueryWrapper<Appoint> appointQueryWrapper = new QueryWrapper<>();
        appointQueryWrapper.eq("need_id",id);
        appointQueryWrapper.ne("state",10); //排除已关闭的（教员未取消预约的） TODO 当选标后其他老师会变成关闭状态
        List<Long> teacherIds = appointMapper.selectList(appointQueryWrapper)
                .stream().map(Appoint::getTeacherId).collect(Collectors.toList());

        QueryWrapper<TeacherDTO> queryWrapper = new QueryWrapper<>();
        if(teacherIds.size()==0){
            MyPage<TeacherDTO> myPage = new MyPage<>();
            myPage.setCurrent(pageQuery.getCurrent());
            myPage.setSize(pageQuery.getSize());
            return myPage;
        }

        queryWrapper.in("user_id",teacherIds);
        IPage<TeacherDTO> teacherDTOIPage = teacherMapper.selectPageDTO(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<TeacherDTO> myPage = BeanCopyUtils.copyBean(teacherDTOIPage, MyPage.class);
        myPage.setPages(teacherDTOIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }
}
