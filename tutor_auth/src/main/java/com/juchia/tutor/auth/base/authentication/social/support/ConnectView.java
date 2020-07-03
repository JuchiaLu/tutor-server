package com.juchia.tutor.auth.base.authentication.social.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 绑定或解绑结果视图
 * 这个视图要做所用第三账户的视图
 * 所以没有配置成这样：Bean({"connect/weixinConnect", "connect/weixinConnected"})
 * 因为后面的 weixinConnect 是由 providerId 决定的，每个第三方的 providerId 不一样
 * 具体的视图只能在各个第三登录的配置中 new 这个视图，起不同的名字后注入容器
 *
 * 用post方法访问 /connect/weixin 是添加绑定
 * 用delete方法访问 /connect/weixin 是删除绑定
 *
 */
public class ConnectView extends AbstractView {


    @Autowired
    private ObjectMapper objectMapper;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/json;charset=UTF-8");

		if (model.get("connections") == null) {
			response.getWriter().write(objectMapper.writeValueAsString(model));
		} else {
			response.getWriter().write(objectMapper.writeValueAsString(model));
		}

	}

}
