package com.juchia.tutor.auth.base.properties;

/**
 * 社交登录配置项
 *
 */
public class SocialProperties {
	
	/**
	 * 社交登录功能拦截的url前缀
	 */
	private String filterProcessesUrl = "/login";

    /**
     * QQ登录子项
     */
	private QQProperties qq = new QQProperties();

    /**
     * 微信登录子项
     */
	private WeixinProperties weixin = new WeixinProperties();

	public QQProperties getQq() {
		return qq;
	}

	public void setQq(QQProperties qq) {
		this.qq = qq;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		this.filterProcessesUrl = filterProcessesUrl;
	}

	public WeixinProperties getWeixin() {
		return weixin;
	}

	public void setWeixin(WeixinProperties weixin) {
		this.weixin = weixin;
	}
	
}
