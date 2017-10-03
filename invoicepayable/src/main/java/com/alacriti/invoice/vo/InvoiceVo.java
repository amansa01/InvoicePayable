package com.alacriti.invoice.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InvoiceVo {
	String Invoice,Email,Product,Quantity,UnitPrice,TotalPrice,Status,name,dueDate,amount,invoiceDate,paymentStatus;
	String pendingInvoice,pendingAmount,paidInvoice,paidAmount,sortElement;
public String getSortElement() {
		return sortElement;
	}

	public void setSortElement(String sortElement) {
		this.sortElement = sortElement;
	}

String search,text;
	public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

	public String getSearch() {
	return search;
}

public void setSearch(String search) {
	this.search = search;
}

	public InvoiceVo(String pendingInvoice, String pendingAmount,
			String paidInvoice, String paidAmount) {
		super();
		this.pendingInvoice = pendingInvoice;
		this.pendingAmount = pendingAmount;
		this.paidInvoice = paidInvoice;
		this.paidAmount = paidAmount;
	}

	public String getPendingInvoice() {
		return pendingInvoice;
	}

	public void setPendingInvoice(String pendingInvoice) {
		this.pendingInvoice = pendingInvoice;
	}

	public String getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(String pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public String getPaidInvoice() {
		return paidInvoice;
	}

	public void setPaidInvoice(String paidInvoice) {
		this.paidInvoice = paidInvoice;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoice() {
		return Invoice;
	}

	public void setInvoice(String invoice) {
		Invoice = invoice;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	public InvoiceVo(String invoice, String email, String product,
			String quantity, String unitPrice, String totalPrice, String status) {
		super();
		Invoice = invoice;
		Email = email;
		Product = product;
		Quantity = quantity;
		UnitPrice = unitPrice;
		TotalPrice = totalPrice;
		Status = status;
	}

	public InvoiceVo(){}
	

	public InvoiceVo(String invoiceId, String email,
			String creationDate, String dueDate,
			String paymentStatus, String amount) {
		super();
		Invoice=invoiceId;
		Email = email;
		this.dueDate=dueDate;
		this.amount=amount;
		this.paymentStatus=paymentStatus;
		invoiceDate=creationDate;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		UnitPrice = unitPrice;
	}

	public String getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		TotalPrice = totalPrice;
	}
}
