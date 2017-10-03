package com.alacriti.invoice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.alacriti.invoice.constants.DBColumnConstants;
import com.alacriti.invoice.exception.DAOException;
import com.alacriti.invoice.vo.InvoiceVo;
import com.alacriti.invoice.vo.PaymentVo;
import com.alacriti.invoice.vo.VendorVo;

public class VendorDao extends BaseDAO {
	private static final Logger log = Logger.getLogger(VendorDao.class);

	public VendorDao(Connection conn) {
		super(conn);
	}

	public void createVendorDao(ArrayList<VendorVo> vendors)
			throws DAOException {
		log.info("In createVendor DAO");

		int[] response;
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime()
				.getTime());

		PreparedStatement ps = null;
		int rs = 0;

		for (int i = 0; i < vendors.size(); i++) {
			try {
				String insert = "insert into " + DBColumnConstants.vendorTable
						+ " values(?,?,?,?,?)";
				ps = connection.prepareStatement(insert);

				ps.setString(1, vendors.get(i).getName());
				ps.setString(2, vendors.get(i).getEmail());
				ps.setDate(3, (java.sql.Date) date);
				ps.setString(4, vendors.get(i).getContactNo());
				ps.setInt(5, DBColumnConstants.vendorStatus);
				rs = ps.executeUpdate();
			} catch (SQLException e) {
				log.error("SQLException in createVendorDao " + e.getMessage(),
						e);
				throw new DAOException();

			}
		}
		close(ps);

	}

	public ArrayList<VendorVo> vendorDao(VendorVo vendor, int start, int end)
			throws DAOException {

		log.info("In vendor DAO");

		ArrayList<VendorVo> vendorVo = new ArrayList<VendorVo>();
		PreparedStatement ps = null;
		String name, email, contactNo;

		ResultSet rs = null;
		try {
		
			String insert = "select vname,email,contact_no from "
					+ DBColumnConstants.vendorTable
					+ " where status=? limit ?,?";
			ps = connection.prepareStatement(insert);
		
			ps.setInt(1, DBColumnConstants.vendorStatus);
			ps.setInt(2, start);
			ps.setInt(3, DBColumnConstants.limit);
		
			rs = ps.executeQuery();
			while (rs.next()) {

				name = rs.getString("vname");
				email = rs.getString("email");
				contactNo = rs.getString("contact_no");
				vendorVo.add(new VendorVo(name, email, contactNo));
			}

		} catch (SQLException e) {
			log.error("SQLException in VenderDao " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}
		return vendorVo;
	}

	public ArrayList<VendorVo> searchVendorDetailsDao(String vendorVo)
			throws DAOException {
		log.info("In searchVendorDetails DAO");

		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<VendorVo> vendorVoResult = new ArrayList<VendorVo>();
		String vname, email, contact_no;

		try {
			String insert = "select vname,email,contact_no FROM "
					+ DBColumnConstants.vendorTable
					+ " where status = ? and (vname LIKE \"%" + vendorVo
					+ "%\" " + "or email LIKE \"%" + vendorVo
					+ "%\" or contact_no LIKE \"%" + vendorVo + "%\" )";

			ps = connection.prepareStatement(insert);
			ps.setInt(1, DBColumnConstants.vendorStatus);
			rs = ps.executeQuery();
			while (rs.next()) {

				vname = rs.getString("vname");
				email = rs.getString("email");
				contact_no = rs.getString("contact_no");
				vendorVoResult.add(new VendorVo(vname, email, contact_no));
			}

		} catch (SQLException e) {
			log.error("SQLException in search Vendor Details " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}
		return vendorVoResult;
	}

	public ArrayList<VendorVo> checkVendorDetailsDao(
			ArrayList<VendorVo> vendorVo) throws DAOException {
		PreparedStatement ps = null;
		String email;
		log.info("In checkVendorDetails DAO");

		ResultSet rs = null;
		for (int i = 0; i < vendorVo.size(); i++) {
			email = vendorVo.get(i).getEmail();
			try {
				String insert = "select email from "
						+ DBColumnConstants.vendorTable + " where email=?";
				ps = connection.prepareStatement(insert);

				ps.setString(1, email);
				rs = ps.executeQuery();
				if (rs.next()) {
					email = rs.getString("email");
					vendorVo.get(i).setStatus("false");
				}
			} catch (SQLException e) {
				log.error("SQLException in checkVendor Details " + e.getMessage(), e);
				throw new DAOException();

			}
		}
		close(ps, rs);
		return vendorVo;
	}

	public int totalVendorDao() throws DAOException {
		ResultSet rs = null;
		PreparedStatement ps = null;
		int result = 0;
		log.info("In totalVendor DAO");

		try {

			String insert = "select count(status) as coun from "
					+ DBColumnConstants.vendorTable + " where status= ?";
			ps = connection.prepareStatement(insert);
			ps.setInt(1, DBColumnConstants.vendorStatus);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = (rs.getInt(1));
			}

		} catch (SQLException e) {
			log.error("SQLException in totalVendorDao " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}

		return result;
	}

	public ArrayList<PaymentVo> mailToVendorDao() throws DAOException {
		log.info("In mail to Vendor DAO");

		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<PaymentVo> paymentVoResult = new ArrayList<PaymentVo>();
		String amount, paidOn, invoice, email;

		try {
			String insert = "select p.amount as amount,p.paid_on as paidOn, p.invoice as invoice ,p.email as email,i.email_sent from payment p, invoice i where i.email_sent=0  and i.invoice_id=p.invoice ";
			ps = connection.prepareStatement(insert);
			rs = ps.executeQuery();
			while (rs.next()) {

				amount = rs.getString("amount");
				paidOn = rs.getString("paidOn");
				invoice = rs.getString("invoice");
				email = rs.getString("email");
				paymentVoResult.add(new PaymentVo(amount, paidOn, invoice,
						email));
			}

		} catch (SQLException e) {
			log.error("SQLException in mailToVendorDao " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}
		return paymentVoResult;
	}

	public void mailSentDao(String invoice) throws DAOException {
		log.info("In Mail Sent DAO");

		int rs = 0;
		PreparedStatement ps = null;
		try {
			String insert = "update " + DBColumnConstants.invoiceTable
					+ " set email_sent= ? where invoice_id = ? ";
			ps = connection.prepareStatement(insert);
			ps.setInt(1, DBColumnConstants.paymentStatusTrue);
			ps.setString(2, invoice);
			rs = ps.executeUpdate();
		} catch (SQLException e) {
			log.error("SQLException in Mail Sent " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps);
		}
	}

}
