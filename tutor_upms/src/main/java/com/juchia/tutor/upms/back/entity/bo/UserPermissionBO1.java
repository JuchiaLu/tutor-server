package com.juchia.tutor.upms.back.entity.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author juchia
 * @since 2019-11-03
 */
@Data
public class UserPermissionBO1 implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Long userId;

    private Long permissionId;

}
