package com.juchia.tutor.business.feigin.controller;


import com.juchia.tutor.api.business.bo.FeignTeacherBO1;
import com.juchia.tutor.api.business.client.IFeignTeacher;
import com.juchia.tutor.api.business.vo.FeignTeacherVO;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.common.validate.group.Insert;
import com.juchia.tutor.business.feigin.service.FeignTeacherService;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 * 从 service中接受DTO 将DTO转换成VO返回给前端
 * 有时VO可以省略，直接把DTO返回给前端，前端再自己根据字段转换成要显示的内容，如将 1 转换成男 2 转成女
 * 可以用DTO替代VO的地方，可以省略掉VO，DTO转VO交给前端处理
 * @author juchia
 * @since 2019-08-23
 */
@Validated
@RestController
@RequestMapping("/feign/teachers") //TODO 服务间调用是否经过网关?答：经过
@Slf4j
public class FeignTeacherController implements IFeignTeacher {

    @Autowired
    private FeignTeacherService feignTeacherService;
    /**
     * 新增老师
     * @param
     * @return
     */
    @Override
    @PostMapping
    public FeignTeacherVO saveTeacher(@Validated(Insert.class) FeignTeacherBO1 feignUserBO) {
        Teacher po = feignTeacherService.saveTeacher(feignUserBO);
        return BeanCopyUtils.copyBean(po,FeignTeacherVO.class);
    }


    /**
     * 根据ID获取老师
     * @return
     * @throws ResourceNotFondException
     */
    @GetMapping //这是userId
    public FeignTeacherVO getById(@RequestParam("id")Long id) throws ResourceNotFondException {
        Teacher teacher = feignTeacherService.myGetById(id);
        return BeanCopyUtils.copyBean(teacher,FeignTeacherVO.class);
    }

    /**
     * 根据ID更新老师
     * @return
     */
    @PostMapping("patch")
    public FeignTeacherVO patchById(@RequestBody FeignTeacherBO1 feignTeacherBO) {
        Teacher teacher = feignTeacherService.patchById(feignTeacherBO);
        return BeanCopyUtils.copyBean(teacher,FeignTeacherVO.class);
    }

}

