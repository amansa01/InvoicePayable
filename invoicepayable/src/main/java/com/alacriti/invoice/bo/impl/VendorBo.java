package com.alacriti.invoice.bo.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.invoice.dao.impl.VendorDao;
import com.alacriti.invoice.exception.BOException;
import com.alacriti.invoice.vo.PaymentVo;
import com.alacriti.invoice.vo.VendorVo;

public class VendorBo extends BaseBO {

	public VendorBo(Connection connection) {
		super(connection);
	}

	private static final Logger log = Logger.getLogger(VendorBo.class);

	public void createVendorBo(ArrayList<VendorVo> vendors) throws BOException {
		log.info("In createVendor Bo");
		try {
			VendorDao venderDao = new VendorDao(getConnection());
			venderDao.createVendorDao(vendors);
		} catch (Exception e) {
			log.error("Exception in createVendorBo " + e.getMessage(), e);
			throw new BOException();
		}
	}

	public List<VendorVo> vendorBo(VendorVo vendorVo, int start, int end)
			throws BOException {
		List<VendorVo> returnResultList;
		log.info("In VendorBo");
		try {
			VendorDao venderDao = new VendorDao(getConnection());
			// vendorVo = new ArrayList<VendorVo>();
			returnResultList = venderDao.vendorDao(vendorVo, start, end);
		} catch (Exception e) {
			log.error("Exception in vendorBo " + e.getMessage(), e);
			throw new BOException();
		}
		return returnResultList;

	}

	public ArrayList<VendorVo> searchVendorDetailsBO(String vendorVo)
			throws BOException {
		log.info("In SearchVendor Detail");
		ArrayList<VendorVo> vendorVoResult = new ArrayList<VendorVo>();
		try {
			VendorDao vendorDaoVo = new VendorDao(getConnection());
			vendorVoResult = vendorDaoVo.searchVendorDetailsDao(vendorVo);
		} catch (Exception e) {
			log.error("Exception in searchVendorDetailsBO " + e.getMessage(), e);
			throw new BOException();
		}
		return vendorVoResult;
	}

	public ArrayList<VendorVo> checkVendorBo(ArrayList<VendorVo> vendorVo)
			throws BOException {
		log.info("In checkVendorBo ");
		ArrayList<VendorVo> vendorVoResult = new ArrayList<VendorVo>();
		try {
			VendorDao vendorDaoVo = new VendorDao(getConnection());
			vendorVoResult = vendorDaoVo.checkVendorDetailsDao(vendorVo);
		} catch (Exception e) {
			log.error("Exception in checkVendorBo " + e.getMessage(), e);
			throw new BOException();
		}
		return vendorVoResult;
	}

	public int totalVendorBo(String email) throws BOException {
		log.info("In Total Vendors Bo ");
		VendorDao vendorDao = new VendorDao(getConnection());
		int vendorVo1 = 0;
		try {
			vendorVo1 = vendorDao.totalVendorDao();
		} catch (Exception e) {
			log.error("Exception in totalVendorBo " + e.getMessage(), e);
			throw new BOException();
		}
		return vendorVo1;
	}

	public ArrayList<PaymentVo> mailToVendorBo() throws BOException {
		log.info("In mailToVendorBo");
		VendorDao vendorDao = new VendorDao(getConnection());
		ArrayList<PaymentVo> paymentVoResult = new ArrayList<PaymentVo>();
		try {
			paymentVoResult = vendorDao.mailToVendorDao();
		} catch (Exception e) {
			log.error("Exception in mailToVendorBo " + e.getMessage(), e);
			throw new BOException();
		}
		return paymentVoResult;

	}

	public void mailBo(String invoice) throws BOException {
		log.info("In Mail ");
		VendorDao vendorDao = new VendorDao(getConnection());
		ArrayList<PaymentVo> paymentVoResult = new ArrayList<PaymentVo>();
		try {
			vendorDao.mailSentDao(invoice);
		} catch (Exception e) {
			log.error("Exception in mailBo " + e.getMessage(), e);
			throw new BOException();
		}
	}

}
