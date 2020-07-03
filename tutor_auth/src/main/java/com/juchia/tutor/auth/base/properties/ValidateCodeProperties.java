package com.juchia.tutor.auth.base.properties;

/**
 * 总验证码配置
 *
 */
public class ValidateCodeProperties {
	
	/**
	 * 图片验证码配置子项
	 */
	private ImageCodeProperties image = new ImageCodeProperties();
	/**
	 * 短信验证码配置子项
	 */
	private SmsCodeProperties sms = new SmsCodeProperties();

	public ImageCodeProperties getImage() {
		return image; 
	}

	public void setImage(ImageCodeProperties image) {
		this.image = image;
	}

	public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}
	
}
