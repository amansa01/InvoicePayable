package com.alacriti.invoice.util;

import java.sql.Date;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import com.fasterxml.jackson.annotation.JsonFormat;

public class model {
	private byte[] file;
	private String email;
	private String duedate;
	private String invoicedate;	

	public String getEmail() {
		return email;
	}
	
	public String getDuedate() {
		return duedate;
	}

	@FormParam("duedate")
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getInvoicedate() {
		return invoicedate;
	}
	@FormParam("invoicedate")	
	public void setInvoicedate(String invoicedate) {
		this.invoicedate = invoicedate;
	}

	@FormParam("email")
	public void setEmail(String email) {
		this.email = email;
	}

	private String invoiceno;

	public String getInvoiceno() {
		return invoiceno;
	}

	@FormParam("invoiceno")
	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	// public model(byte[] file){
	// this.file=file;
	// }
	@FormParam("file")
	@PartType("application/octet-stream")
	public void setFile(byte[] file) {
		this.file = file;
	}

	public byte[] getFile() {
		return file;
	}
}
