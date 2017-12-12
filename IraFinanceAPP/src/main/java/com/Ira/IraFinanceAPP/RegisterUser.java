package com.Ira.IraFinanceAPP;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterUser {
	
	private int subid;
	private String password;
	private String shopname;
	private String address;
	private String mobilenumber;
 	private String emailid;
	private String gstnumber;
	private String acctlocked;
	private String substartdate;
	private String subenddate;
	
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getSubid() {
		return subid;
	}
	public void setSubid(int subid) {
		this.subid = subid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getGstnumber() {
		return gstnumber;
	}
	public void setGstnumber(String gstnumber) {
		this.gstnumber = gstnumber;
	}
	public String getAcctlocked() {
		return acctlocked;
	}
	public void setAcctlocked(String acctlocked) {
		this.acctlocked = acctlocked;
	}
	public String getSubstartdate() {
		return substartdate;
	}
	public void setSubstartdate(String substartdate) {
		this.substartdate = substartdate;
	}
	public String getSubenddate() {
		return subenddate;
	}
	public void setSubenddate(String subenddate) {
		this.subenddate = subenddate;
	}
	
	
	
	

}
