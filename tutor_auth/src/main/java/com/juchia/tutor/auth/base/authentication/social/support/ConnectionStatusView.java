package com.juchia.tutor.auth.base.authentication.social.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 社交帐号绑定状态视图
 * 即可以用它来查看本帐号绑定了什么第三方帐号
 * 用GET方法访问 /connect 这个路径，进会返回这个视图
 */
@Component("connect/status")
public class ConnectionStatusView extends AbstractView {
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, List<Connection<?>>> connections = (Map<String, List<Connection<?>>>) model.get("connectionMap");
		
		Map<String, Boolean> result = new HashMap<>();
		for (String key : connections.keySet()) {
			result.put(key, CollectionUtils.isNotEmpty(connections.get(key)));
		}
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(model));
	}

}
