package com.juchia.tutor.auth.base.authentication.code.core;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface ValidateCode extends Serializable {

    String getCode();

    LocalDateTime getExpiredTime();

}
