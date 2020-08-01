/**
 * 
 */
package com.github.in.ghippargi.qrcodegen.data;

/**
 * @author ghippargi
 *
 */
public class QrCodeInput {

	private Long id;
	private String info;
	private String url;
	private Long emergencyNum;
	private Long smsNum;
	private String smsMsg;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getEmergencyNum() {
		return emergencyNum;
	}
	public void setEmergencyNum(Long emergencyNum) {
		this.emergencyNum = emergencyNum;
	}
	public Long getSmsNum() {
		return smsNum;
	}
	public void setSmsNum(Long smsNum) {
		this.smsNum = smsNum;
	}
	public String getSmsMsg() {
		return smsMsg;
	}
	public void setSmsMsg(String smsMsg) {
		this.smsMsg = smsMsg;
	}
}
