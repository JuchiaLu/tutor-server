package com.juchia.tutor.auth.base.authentication.social.qq.api;

import lombok.Data;

/**
 * QQ信息实体类
 *
 */
@Data
public class QQUserInfo {
    private String openId;
    private String ret;
    private String msg;
    private String is_lost;
    private String nickname;
    private String gender;
    private String gender_type;
    private String province;
    private String city;
    private String year;
    private String constellation;
    private String figureurl;
    private String figureurl_1;
    private String figureurl_2;
    private String figureurl_qq_1;
    private String figureurl_qq_2;
    private String figureurl_qq;
    private String figureurl_type;
    private String is_yellow_vip;
    private String vip;
    private String yellow_vip_level;
    private String level;
    private String is_yellow_year_vip;
}
