package com.alacriti.invoice.delegate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.alacriti.invoice.bo.impl.InvoiceBo;
import com.alacriti.invoice.constants.Constants;
import com.alacriti.invoice.constants.DBColumnConstants;
import com.alacriti.invoice.util.model;
import com.alacriti.invoice.vo.InvoiceVo;
import com.alacriti.invoice.vo.PaymentVo;

public class InvoiceDelegate extends BaseDelegate {
	private static final Logger log = Logger
			.getLogger(InvoiceDelegate.class);

	public ArrayList<InvoiceVo> receiveInvoiceDelegate(@MultipartForm model form)
			throws Exception {
		log.info("In receive InvoiceDelegate");
		
		DateFormat formatter;
		Date date;
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		date = (Date) formatter.parse(form.getInvoicedate());
		String fileLocation = "/home/amans/Downloads/Wildfly/wildfly-10.1.0.Final/CSV";
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String fileName = fileLocation + "/" + df.format(new Date());
		ArrayList<InvoiceVo> invoiceVo = new ArrayList<InvoiceVo>();
		File file = new File(fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(form.getFile());
		fop.flush();
		fop.close();
		String line = "";

		boolean  res1, res2, res3;
		String cvsSplitBy = ",";
		int sum = 0;
		String l1 = "Test", l2 = "String", column1, column2, column3, column4, statusofInvoice = "true";
		Pattern p = Pattern.compile("[a-zA-Z]+");
//		final String namePattern = "[a-zA-Z]+";
//		final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//		final String phonePattern = ("\\d{10}");
		final String number = "[0-9]*";

		BufferedReader br = new BufferedReader(new FileReader(fileName));

		br.readLine();
		while ((line = br.readLine()) != null) {
			String[] invoice = line.split(cvsSplitBy);


			column1 = invoice[0];
			column2 = invoice[1];
			column3 = invoice[2];
			column4 = invoice[3];
			res1 = Pattern.compile(number).matcher(invoice[1]).matches();
			res2 = Pattern.compile(number).matcher(invoice[2]).matches();
			res3 = Pattern.compile(number).matcher(invoice[3]).matches();
			if (!res1) {
				invoice[1] = invoice[1].concat("*");
			}
			if (!res2) {
				invoice[2] = invoice[2].concat("*");
			}
			if (!res3) {
				invoice[3] = invoice[3].concat("*");
			}
			
			if (column1.compareTo(Constants.Total) == 0) {
				
				if (sum != Integer.parseInt(invoice[3]))
					statusofInvoice = "false";
			}
		

			sum += Integer.parseInt(invoice[3]);

			invoiceVo.add(new InvoiceVo(l1, l2, column1, column2, column3, column4, statusofInvoice));

		}

		return invoiceVo;
	}

	public void createInvoiceDelegate(InvoiceVo invoiceVo) {

		log.info("In Create Invoice delegate");
		boolean flag = false;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			InvoiceBo invoiceBo = new InvoiceBo(getConnection());
			invoiceBo.createBillBo(invoiceVo);

		} catch (Exception e) {
			log.error("Exception in createInvoiceDelegate " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		

	}

	public ArrayList<InvoiceVo> getInvoiceDetails(InvoiceVo invoiceVo) {
		log.info("In Invoice delegate");
		boolean flag = false;
		Connection connection = null;
		ArrayList<InvoiceVo> invoiceVo1 = new ArrayList<InvoiceVo>();
		try {
			connection = startDBTransaction();
			setConnection(connection);
			InvoiceBo invoiceBo = new InvoiceBo(getConnection());
			invoiceVo1=invoiceBo.getInvoiceDetailsBO(invoiceVo);
		} catch (Exception e) {
			log.error("Exception in getInvoiceDetails " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return invoiceVo1;
		
	}
	public InvoiceVo getInvoiceSummary(InvoiceVo invoiceVo) {
		log.info("In getInvoiceSummary");
		boolean flag = false;
		Connection connection = null;
		InvoiceVo invoiceVo1 = null;
		String testEmail=invoiceVo.getEmail();
		try {
			connection = startDBTransaction();
			setConnection(connection);
			InvoiceBo invoiceBo = new InvoiceBo(getConnection());
			if(testEmail.equalsIgnoreCase(DBColumnConstants.masterEmail))
				{invoiceVo1=invoiceBo.getAdminInvoiceSummaryBO(invoiceVo);}
			else
			{
				invoiceVo1=invoiceBo.getVendorInvoiceSummaryBO(invoiceVo);
			}
		} catch (Exception e) {
			log.error("Exception in getInvoiceSummary " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return invoiceVo1;
		
	}

	public String payToVendorDelegate(PaymentVo paymentVo) {
		log.info("In pay To vendor");
		boolean flag = false;
		String response = null;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			InvoiceBo invoiceBo = new InvoiceBo(getConnection());
			response=invoiceBo.payToVendorBo(paymentVo);

		} catch (Exception e) {
			log.error("Exception in payToVendorDelegate " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		
		return response;
		
	}

	public int totalInvoiceDelegate(String email) {
		log.info("In Total vendor  Delegate");
		boolean flag = false;
		int responseBo=0;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			InvoiceBo invoiceBo = new InvoiceBo(getConnection());
			responseBo=invoiceBo.totalVendorBo(email);

		} catch (Exception e) {
			log.error("Exception in getMessage " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return responseBo;
	}

		
	public int searchInvoiceDelegate(InvoiceVo invoiceVo) {
		log.info("SearchInvoiceDelegate");
		boolean flag = false;
		int responseBo=0;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			InvoiceBo invoiceBo = new InvoiceBo(getConnection());
			responseBo=invoiceBo.totalSearchBo(invoiceVo);

		} catch (Exception e) {
			log.error("Exception in SearchInvoiceDelegate " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return responseBo;
	}
	
	public List<InvoiceVo> getInvoicePageList(InvoiceVo invoiceVo, int start, int end) {
		
		boolean flag = false;
		Connection connection = null;
		List<InvoiceVo> resultList=null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			InvoiceBo invoiceBo = new InvoiceBo(getConnection());
			resultList=invoiceBo.getInvoiceInfoBo( invoiceVo,start, end);

		} catch (Exception e) {
			log.error("Exception in getMessage " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return resultList;
		
	}

	public List<InvoiceVo> getInvoiceSearch(InvoiceVo invoiceVo,int start, int end) {
		log.getLoggerRepository();
		boolean flag = false;
		Connection connection = null;
		List<InvoiceVo> invoiceVo1 = new ArrayList<InvoiceVo>();
		try {
			connection = startDBTransaction();
			setConnection(connection);
			InvoiceBo invoiceBo = new InvoiceBo(getConnection());
			invoiceVo1=invoiceBo.searchInvoiceDetailsBO(invoiceVo,start,end);
		} catch (Exception e) {
			log.error("Exception in getInvoiceSearch " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return invoiceVo1;
	}


}
