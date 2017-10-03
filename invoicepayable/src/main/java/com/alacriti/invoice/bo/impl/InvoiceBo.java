package com.alacriti.invoice.bo.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.invoice.constants.DBColumnConstants;
import com.alacriti.invoice.dao.impl.InvoiceDao;
import com.alacriti.invoice.exception.BOException;
import com.alacriti.invoice.vo.InvoiceVo;
import com.alacriti.invoice.vo.PaymentVo;

public class InvoiceBo extends BaseBO {

	public InvoiceBo(Connection connection) {
		super(connection);
		
	}

	private static final Logger log = Logger.getLogger(InvoiceBo.class);

	public void createBillBo(InvoiceVo invoiceVo) throws BOException {
		try {
			log.info("In CreateBillBO");
			InvoiceDao invoiceDao = new InvoiceDao(getConnection());
			invoiceDao.createBillDao(invoiceVo);
		} catch (Exception e) {
			log.error("Exception in createBillBo " + e.getMessage(), e);
			throw new BOException();
		}
	}

	public ArrayList<InvoiceVo> getInvoiceDetailsBO(InvoiceVo invoiceVo)
			throws BOException {
		log.info("In getInvoiceDetailsBo");
		InvoiceDao invoiceDao = new InvoiceDao(getConnection());
		ArrayList<InvoiceVo> invoiceVo1 = new ArrayList<InvoiceVo>();
		String email = invoiceVo.getEmail();
		try {
			if (email.equalsIgnoreCase(DBColumnConstants.masterEmail)) {
				invoiceVo1 = invoiceDao.allInvoiceDetailsDao(invoiceVo);

			} else {
				invoiceVo1 = invoiceDao.getVendorInvoiceDetailsDao(invoiceVo);
			}
		} catch (Exception e) {
			log.error("Exception in getInvoiceDetailsBO " + e.getMessage(), e);
			
			throw new BOException();
		}
		return invoiceVo1;

	}

	public InvoiceVo getAdminInvoiceSummaryBO(InvoiceVo invoiceVo)
			throws BOException {
		log.info("In getAdminInvoiceSummaryBO");
		InvoiceDao invoiceDao = new InvoiceDao(getConnection());
		ArrayList<InvoiceVo> invoiceVo1 = null;
		InvoiceVo resultList;
		ArrayList<InvoiceVo> invoiceVo2 = null;
		try {
			invoiceVo1 = invoiceDao.getAdminInvoiceSummaryDao(invoiceVo);
			invoiceVo2 = invoiceDao.getAdminInvoiceSummaryDao2(invoiceVo);
			resultList = new InvoiceVo(invoiceVo.getPaymentStatus(),
					invoiceVo.getPendingAmount(), invoiceVo.getPaidInvoice(),
					invoiceVo.getPaidAmount());
		} catch (Exception e) {
			log.error("Exception in getAdminInvoiceSummaryBO " + e.getMessage(), e);
			throw new BOException();
		}
		return resultList;

	}

	public InvoiceVo getVendorInvoiceSummaryBO(InvoiceVo invoiceVo)
			throws BOException {
		log.info("In getInvoiceVendorSummaryBO");
		InvoiceDao invoiceDao = new InvoiceDao(getConnection());
		ArrayList<InvoiceVo> invoiceVo1 = null;
		InvoiceVo resultList;
		ArrayList<InvoiceVo> invoiceVo2 = null;
		try {
			invoiceVo1 = invoiceDao.getVendorInvoiceSummaryDao(invoiceVo);
			invoiceVo2 = invoiceDao.getVendorInvoiceSummaryDao2(invoiceVo);
			resultList = new InvoiceVo(invoiceVo.getPaymentStatus(),
					invoiceVo.getPendingAmount(), invoiceVo.getPaidInvoice(),
					invoiceVo.getPaidAmount());
		} catch (Exception e) {
			log.error("Exception in getInvoiceVendorSummaryBO " + e.getMessage(), e);
			throw new BOException();
		}
		return resultList;

	}

	public String payToVendorBo(PaymentVo paymentVo) throws BOException {
		log.info("In pay To vendor Bo ");
		int resultUpdateInvoice,resultpaytoVendor;
		String Response;
		try {
			InvoiceDao invoiceDao = new InvoiceDao(getConnection());
			resultpaytoVendor = invoiceDao.payToVendorDao(paymentVo);
			resultUpdateInvoice=invoiceDao.updateInvoice(paymentVo);
		} catch (Exception e) {
			log.error("Exception in payToVendorBo " + e.getMessage(), e);
			throw new BOException();
		}
		if (resultpaytoVendor == 1 && resultUpdateInvoice == 1)
			Response= "Payment Sussessful";
		else
			Response= "Payment Unsussessful";
		return Response;
	}

	public int totalVendorBo(String email) throws BOException {
		InvoiceDao invoiceDao = new InvoiceDao(getConnection());
		int invoiceVo1 = 1;
		try {
			if (email.equals(DBColumnConstants.masterEmail)) {
				invoiceVo1 = invoiceDao.totalCountForAdminDao();
			} else {
				invoiceVo1 = invoiceDao.totalCountForVendorDao(email);
			}
		} catch (Exception e) {
			log.error("Exception in totalVendorBo " + e.getMessage(), e);
			throw new BOException();
		}
		return invoiceVo1;
	}

	public List<InvoiceVo> getInvoiceInfoBo(InvoiceVo invoiceVo, int start,
			int end) throws BOException {
		log.info("In getInvoiceInfo Bo ");
		InvoiceDao invoiceDao = new InvoiceDao(getConnection());
		List<InvoiceVo> returnResultList;

		try {
			if (invoiceVo.getEmail().equalsIgnoreCase(
					DBColumnConstants.masterEmail)) {
				returnResultList = invoiceDao.getAllInvoiceDetailsBO(invoiceVo,
						start, end);
			} else

				returnResultList = invoiceDao.getVendorInvoiceDetailsBO(
						invoiceVo, start, end);
		} catch (Exception e) {
			log.error("Exception in getInvoiceInfo " + e.getMessage(), e);
			throw new BOException();
		}
		return returnResultList;
	}

	public List<InvoiceVo> searchInvoiceDetailsBO(InvoiceVo invoiceVo,
			int start, int end) throws BOException {
		
		log.info("In Search Invoice DetailsBo");
		InvoiceDao invoiceDao = new InvoiceDao(getConnection());
		ArrayList<InvoiceVo> invoiceVo1;
		List<InvoiceVo> returnResultList = null;

		String email = invoiceVo.getEmail();
		try {
				if (email.equalsIgnoreCase(DBColumnConstants.masterEmail)) {
					invoiceVo1 = invoiceDao
							.searchAllInvoiceDetailsDao(invoiceVo);
					if (end >= invoiceVo1.size()) {
						returnResultList = invoiceVo1.subList(start,
								invoiceVo1.size());
					} else {
						returnResultList = invoiceVo1.subList(start, end);
					}

				} else {
					invoiceVo1 = invoiceDao
							.searchvendorInvoiceDetailsDao(invoiceVo);
					if (end >= invoiceVo1.size()) {
						returnResultList = invoiceVo1.subList(start,
								invoiceVo1.size());
					} else {
						returnResultList = invoiceVo1.subList(start, end);
					}
				}
		} catch (Exception e) {
			log.error("Exception in searchInvoiceDetailsBO " + e.getMessage(), e);
			throw new BOException();
		}
		return returnResultList;
	}

	public int totalSearchBo(InvoiceVo invoiceVo) throws BOException {
		log.info("In Total Search Bo");
		InvoiceDao invoiceDao = new InvoiceDao(getConnection());
		ArrayList<InvoiceVo> invoiceVo1;
		int resultInvoiceSearch = 0;
		String email = invoiceVo.getEmail();
		try {
			if (email.equals(DBColumnConstants.masterEmail)) {
				invoiceVo1 = invoiceDao.searchAllInvoiceDetailsDao(invoiceVo);
				resultInvoiceSearch = invoiceVo1.size();
			} else {
				invoiceVo1 = invoiceDao
						.searchvendorInvoiceDetailsDao(invoiceVo);
				resultInvoiceSearch = invoiceVo1.size();
			}
		} catch (Exception e) {
			log.error("Exception in totalSearchBo " + e.getMessage(), e);
			throw new BOException();
		}
		return resultInvoiceSearch;
	}

}
