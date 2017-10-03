package com.alacriti.invoice.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VendorVo {
	String Name,Email,ContactNo,Status;

	public VendorVo(String name, String email, String contactNo, String status) {
		super();
		Name = name;
		Email = email;
		ContactNo = contactNo;
		Status = status;
	}
	public VendorVo(){}

	public VendorVo(String name, String email, String contactNo) {
		super();
		Name = name;
		Email = email;
		ContactNo = contactNo;
	
	}
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getContactNo() {
		return ContactNo;
	}

	public void setContactNo(String contactNo) {
		ContactNo = contactNo;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
}
