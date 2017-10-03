package com.alacriti.invoice.vo;

import java.sql.Connection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentVo {
String invoice,email,amount,accountName,accountNumber,accountType,ifsc,verifyAccountNumber,paidOn;




public String getPaidOn() {
	return paidOn;
}

public void setPaidOn(String paidOn) {
	this.paidOn = paidOn;
}

public String getVerifyAccountNumber() {
	return verifyAccountNumber;
}

public void setVerifyAccountNumber(String verifyAccountNumber) {
	this.verifyAccountNumber = verifyAccountNumber;
}

public PaymentVo() {}

public PaymentVo(Connection connection) {
	// TODO Auto-generated constructor stub
}

public PaymentVo(String amount, String paidOn, String invoice, String email) {
	this.amount=amount;
	this.paidOn=paidOn;
	this.invoice=invoice;
	this.email=email;
}

public String getInvoice() {
	return invoice;
}

public void setInvoice(String invoice) {
	this.invoice = invoice;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getAmount() {
	return amount;
}

public void setAmount(String amount) {
	this.amount = amount;
}

public String getAccountName() {
	return accountName;
}

public void setAccountName(String accountName) {
	this.accountName = accountName;
}

public String getAccountNumber() {
	return accountNumber;
}

public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
}

public String getAccountType() {
	return accountType;
}

public void setAccountType(String accountType) {
	this.accountType = accountType;
}

public String getIfsc() {
	return ifsc;
}

public void setIfsc(String ifsc) {
	this.ifsc = ifsc;
}
}
