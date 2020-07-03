package com.juchia.tutor.api.business.client;


import com.juchia.tutor.api.business.bo.FeignTeacherBO1;
import com.juchia.tutor.api.business.vo.FeignTeacherVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("business2")
public interface IFeignTeacher {

//  新添老师
    @PostMapping("/feign/teachers")
    FeignTeacherVO saveTeacher(@RequestBody FeignTeacherBO1 feignTeacherBO);


    /**
     * 根据ID获取老师
     * @return
     */
    @GetMapping("/feign/teachers") //这是userId
    FeignTeacherVO getById(@RequestParam("id")Long id) throws Exception;

    /**
     * 根据ID更新老师
     * @return
     */
    @PostMapping("/feign/teachers/patch")
    FeignTeacherVO patchById(@RequestBody FeignTeacherBO1 feignTeacherBO);

}
