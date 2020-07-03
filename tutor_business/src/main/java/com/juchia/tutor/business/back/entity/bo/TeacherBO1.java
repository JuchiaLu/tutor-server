package com.juchia.tutor.business.back.entity.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeacherBO1 implements Serializable {

//    审核通过或失败（2 通过 3失败）
    private Integer state;

//    失败原因
    private String reason;

}
