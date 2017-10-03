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
import com.alacriti.invoice.bo.impl.VendorBo;
import com.alacriti.invoice.util.model;
import com.alacriti.invoice.vo.InvoiceVo;
import com.alacriti.invoice.vo.PaymentVo;
import com.alacriti.invoice.vo.VendorVo;

public class VendorDelegate extends BaseDelegate {

	private static final Logger log = Logger
			.getLogger(VendorDelegate.class);
	
	public ArrayList<VendorVo> checkVendorDelegate(@MultipartForm model form)
			throws Exception {
			log.info("In checkVendorDelegate");
		String fileLocation = "/home/amans/Downloads/Wildfly/wildfly-10.1.0.Final/CSV";
		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
		String fileName = fileLocation + "/" + df.format(new Date());
		ArrayList<VendorVo> vendorVo = new ArrayList<VendorVo>();
		ArrayList<VendorVo> vendorVoResult = new ArrayList<VendorVo>();
		File file = new File(fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(form.getFile());
		fop.flush();
		fop.close();
		String line = "";
		boolean res1, res2, res3, res4;
		String cvsSplitBy = ",";
		String col1, col2, col3, status = "true";
		Pattern p = Pattern.compile("[a-zA-Z]+");
		final String namePattern = "[a-zA-Z]+";
		final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		final String phonePattern = ("\\d{10}");

		BufferedReader br = new BufferedReader(new FileReader(fileName));
		
		br.readLine();
		while ((line = br.readLine()) != null) {
			String[] vendor = line.split(cvsSplitBy);
			res1 = Pattern.compile(namePattern).matcher(vendor[0]).matches();
			res2 = Pattern.compile(emailPattern).matcher(vendor[1]).matches();
			res3 = Pattern.compile(phonePattern).matcher(vendor[2]).matches();
			if (!res1) {
				vendor[0] = vendor[0].concat("*");
			}
			if (!res2)
				vendor[1] = vendor[1].concat("*");
			if (!res3)
				vendor[2] = vendor[2].concat("*");
			col1 = vendor[0];
			col2 = vendor[1];
			col3 = vendor[2];
			if (vendor[0].contains("*") || vendor[1].contains("*")
					|| vendor[2].contains("*")) {
				status = "false";
			}
			vendorVo.add(new VendorVo(col1, col2, col3, status));
			status = "true";
		}	
		
		boolean flag = false;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			VendorBo venderBo = new VendorBo(getConnection());
			vendorVoResult=venderBo.checkVendorBo(vendorVo);

		} catch (Exception e) {
			log.error("Exception in checkVendorDelegate " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
			
		return vendorVoResult;	
	}

	
	public void createVendorDelegate(ArrayList<VendorVo> vendors) {
		
		log.info("In createVendorDelegate");
		boolean flag = false;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			VendorBo venderBo = new VendorBo(getConnection());
			venderBo.createVendorBo(vendors);

		} catch (Exception e) {
			log.error("Exception in createVendorDelegate " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}

	}


	public List<VendorVo> vendorListDelegate(VendorVo vendorVo,int start,int end) {
		log.info("In vendorListDelegate");
		boolean flag = false;
		Connection connection = null;
		List<VendorVo> resultList=null;

		try {
			connection = startDBTransaction();
			setConnection(connection);
			VendorBo venderBo = new VendorBo(getConnection());
			resultList=venderBo.vendorBo(vendorVo,start,end);

		} catch (Exception e) {
			log.error("Exception in vendorListDelegate " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
	return resultList;	
	}


	public ArrayList<VendorVo> vendorSearchDelegate(String vendorVo) {
		log.info("In getVendorSearch");
		boolean flag = false;
		Connection connection = null;
		ArrayList<VendorVo> vendorVoResult = new ArrayList<VendorVo>();
		try {
			connection = startDBTransaction();
			setConnection(connection);
			VendorBo vendorBo = new VendorBo(getConnection());
			vendorVoResult=vendorBo.searchVendorDetailsBO(vendorVo);
		} catch (Exception e) {
			log.error("Exception in getVendorSearch " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return vendorVoResult;
	}
	
	
	public int totalVendorDelegate(String email) {
		log.info("In Total vendor  Delegate");
		boolean flag = false;
		int responseBo=0;
		Connection connection = null;
		try {
			connection = startDBTransaction();
			setConnection(connection);
			VendorBo vendorBo = new VendorBo(getConnection());
			responseBo=vendorBo.totalVendorBo(email);

		} catch (Exception e) {
			log.error("Exception in totalVendorDelegate " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return responseBo;
	}
	
	
	public ArrayList<PaymentVo> mailToSend() {
		log.info("In MAiltoSend Delegate");
		boolean flag = false;
		Connection connection = null;
		ArrayList<PaymentVo> paymentVoResult = new ArrayList<PaymentVo>();
		try {
			connection = startDBTransaction();
			setConnection(connection);
			VendorBo vendorBo = new VendorBo(getConnection());
			paymentVoResult=vendorBo.mailToVendorBo();
		} catch (Exception e) {
			log.error("Exception in MAiltoSend " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
		return paymentVoResult;
	}
	
	public  void mailSent(String invoice){
		log.info("In Mail Sent Delegate");
		boolean flag = false;
		Connection connection = null;
		ArrayList<PaymentVo> paymentVoResult = new ArrayList<PaymentVo>();
		try {
			connection = startDBTransaction();
			setConnection(connection);
			VendorBo vendorBo = new VendorBo(getConnection());
			vendorBo.mailBo(invoice);
		} catch (Exception e) {
			log.error("Exception in Mail Sent " + e.getMessage(), e);
			flag = true;
		} finally {
			endDBTransaction(connection, flag);
		}
	}
	

}
