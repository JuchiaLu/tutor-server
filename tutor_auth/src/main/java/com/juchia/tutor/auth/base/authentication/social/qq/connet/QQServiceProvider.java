package com.juchia.tutor.auth.base.authentication.social.qq.connet;


import com.juchia.tutor.auth.base.authentication.social.qq.api.QQ;
import com.juchia.tutor.auth.base.authentication.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * 服务提供者，即将access_token给这个类，这个类会自动获取QQ信息返回
 * 连接工厂会使用这个类
 * 这个类会调用QQImpl获取信息
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	private String appId;

//	QQ登录页面地址
	private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
//    private static final String URL_AUTHORIZE = "http://localhost:8088/oauth2.0/authorize";

//  code换取accessToken地址
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
//    private static final String URL_ACCESS_TOKEN = "http://localhost:8088/oauth2.0/token";



    public QQServiceProvider(String appId, String appSecret) {
		super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
		this.appId = appId;
	}
	
//	框架会帮我们走完Oauth2流程，然后拿到access_token给我们，我们需要根据拿到的access_token获取QQ信息给框架
//  然后框架会调用适配器，将获取到的信息填充到封装第三放通用信息的类中，供前台方便展出
	@Override
	public QQ getApi(String accessToken) {
		return new QQImpl(accessToken, appId);
	}

}
