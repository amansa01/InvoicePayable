package com.alacriti.invoice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.invoice.constants.DBColumnConstants;
import com.alacriti.invoice.exception.DAOException;
import com.alacriti.invoice.vo.InvoiceVo;
import com.alacriti.invoice.vo.PaymentVo;

public class InvoiceDao extends BaseDAO {
	private static final Logger log = Logger.getLogger(InvoiceDao.class);

	public InvoiceDao(Connection conn) {
		super(conn);
	}

	public void createBillDao(InvoiceVo invoiceVo) throws Exception {
		log.info("In Create Bill Dao");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int amount = Integer.parseInt(invoiceVo.getAmount());
		java.util.Date date = sdf.parse(invoiceVo.getInvoiceDate());
		java.sql.Date invoiceDate = new java.sql.Date(date.getTime());
		java.util.Date date2 = sdf.parse(invoiceVo.getDueDate());
		java.sql.Date dueDate = new java.sql.Date(date2.getTime());
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String insert = "insert into "
					+ DBColumnConstants.invoiceTable
					+ "( name,email,invoice_creation_date,due_date,payment_status,email_sent,amount ) values(?,?,?,?,?,?,?)";
			ps = connection.prepareStatement(insert);
			ps.setString(1, invoiceVo.getName());
			ps.setString(2, invoiceVo.getEmail());
			ps.setDate(3, (invoiceDate));
			ps.setDate(4, (dueDate));
			ps.setInt(5, DBColumnConstants.paymentStatusFalse);
			ps.setInt(6, DBColumnConstants.emailStatusFalse);
			ps.setInt(7, amount);
			int res = ps.executeUpdate();

		} catch (SQLException e) {
			log.error("SQLException in createBillDao " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}
	}

	public ArrayList<InvoiceVo> getVendorInvoiceDetailsDao(InvoiceVo invoiceVo)
			throws DAOException {
		log.info("In getVendorInvoiceDetailsDao");
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<InvoiceVo> invoiceVO = new ArrayList<InvoiceVo>();
		String invoiceId, email, creationDate, dueDate, paymentStatus, amount;

		try {
			String insert = "Select invoice_id,email,invoice_creation_date,due_date,payment_status,amount from "
					+ DBColumnConstants.invoiceTable + " where email=?";
			ps = connection.prepareStatement(insert);
			ps.setString(1, invoiceVo.getEmail());
			rs = ps.executeQuery();
			while (rs.next()) {

				invoiceId = rs.getString("invoice_id");
				email = rs.getString("email");
				creationDate = String(rs.getDate("invoice_creation_date"));
				dueDate = String(rs.getDate("due_date"));
				paymentStatus = rs.getString("payment_status");
				amount = Integer.toString(rs.getInt("amount"));
				invoiceVO.add(new InvoiceVo(invoiceId, email, creationDate,
						dueDate, paymentStatus, amount));
			}

		} catch (SQLException e) {
			log.error("SQLException in getInvoiceDetailsDao " + e.getMessage(),
					e);
			throw new DAOException();
		} finally {
			close(ps, rs);
		}

		return invoiceVO;
	}

	public ArrayList<InvoiceVo> allInvoiceDetailsDao(InvoiceVo invoiceVo)
			throws DAOException {
		log.info("In allInvoiceDetailsDao");
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<InvoiceVo> invoiceVO = new ArrayList<InvoiceVo>();
		String invoiceId, email, creationDate, dueDate, paymentStatus, amount;

		try {
			String insert = "Select invoice_id,email,invoice_creation_date,due_date,amount, case when payment_status='0' Then 'Unpaid' else 'Paid' end as payment_status from "
					+ DBColumnConstants.invoiceTable;
			ps = connection.prepareStatement(insert);
			rs = ps.executeQuery();
			while (rs.next()) {

				invoiceId = rs.getString("invoice_id");
				email = rs.getString("email");
				creationDate = String(rs.getDate("invoice_creation_date"));
				dueDate = String(rs.getDate("due_date"));
				paymentStatus = rs.getString("payment_status");
				amount = Integer.toString(rs.getInt("amount"));
				invoiceVO.add(new InvoiceVo(invoiceId, email, creationDate,
						dueDate, paymentStatus, amount));
			}

		} catch (SQLException e) {
			log.error("SQLException in allInvoiceDetailsDao " + e.getMessage(),
					e);
			throw new DAOException();
		} finally {
			close(ps, rs);
		}

		return invoiceVO;
	}

	private String String(java.sql.Date date) {
		String dateStr = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dateStr = df.format(date);
		} catch (Exception ex) {

		}
		return dateStr;
	}

	public int payToVendorDao(PaymentVo paymentVo) throws DAOException {
		log.info("In pay To vendor DAO");
		ResultSet rs = null;
		PreparedStatement ps = null;
		String result = null;
		int result1 = 0;
		int res = 0;
		int amount = Integer.parseInt(paymentVo.getAmount());
		int accountNumber = Integer.parseInt(paymentVo.getAccountNumber());
		java.sql.Date todaydate = new java.sql.Date(
				new java.util.Date().getTime());
		try {
			String insert = "insert into "
					+ DBColumnConstants.paymentTable
					+ "(invoice,email,amount,acc_name,acc_number,acc_type,ifsc,paid_on ) values(?,?,?,?,?,?,?,?)";
			ps = connection.prepareStatement(insert);
			ps.setString(1, paymentVo.getInvoice());
			ps.setString(2, paymentVo.getEmail());
			ps.setInt(3, amount);
			ps.setString(4, paymentVo.getAccountName());
			ps.setInt(5, accountNumber);
			ps.setString(6, paymentVo.getAccountType());
			ps.setString(7, paymentVo.getIfsc());
			ps.setDate(8, todaydate);
			res = ps.executeUpdate();
		} catch (SQLException e) {
			log.error("SQLException in payToVendorDao " + e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}
		return res;
	}

	public int updateInvoice(PaymentVo paymentVo) throws SQLException,
			DAOException {
		log.info("updateInvoice");
		int res;
		PreparedStatement ps = null;
		try {
			String insert = "update " + DBColumnConstants.invoiceTable
					+ " set payment_status=? where invoice_id=?";
			ps = connection.prepareStatement(insert);
			ps.setInt(1, 1);
			ps.setString(2, paymentVo.getInvoice());
			res = ps.executeUpdate();
		} catch (SQLException e) {
			log.error("SQLException in updateInvoice " + e.getMessage(), e);
			throw new DAOException();
		}
		return res;
	}

	public int totalCountForAdminDao() throws DAOException {
		log.info("In totalCountForAdminDao");
		ResultSet rs = null;
		PreparedStatement ps = null;
		int result = 0;

		try {

			String insert = "select count(invoice_id) as coun from "
					+ DBColumnConstants.invoiceTable;
			ps = connection.prepareStatement(insert);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = (rs.getInt(1));
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in totalCountForAdminDao " + e.getMessage(),
					e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}

		return result;
	}

	public int totalCountForVendorDao(String email) throws DAOException {
		log.info("In totalCountForVendorDao");
		ResultSet rs = null;
		PreparedStatement ps = null;
		int result = 0;
		try {

			String insert = "select count(invoice_id) as sum from "
					+ DBColumnConstants.invoiceTable + " where email=?";
			ps = connection.prepareStatement(insert);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = (rs.getInt("sum"));
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in totalCountForVendorDao " + e.getMessage(),
					e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}
//		log.error(result);
		return result;
	}

	public ArrayList<InvoiceVo> getAdminInvoiceSummaryDao(InvoiceVo invoiceVo)
			throws DAOException {
		log.info("In getAdminInvoiceSummaryDao");
		ResultSet rs = null;
		PreparedStatement ps = null;

		String pendingAmount, pendingInvoice;
		ArrayList<InvoiceVo> result = new ArrayList<InvoiceVo>();
		try {
			String insert = "select sum(amount) as pendingAmount, count(payment_status) as pendingStatus from "
					+ DBColumnConstants.invoiceTable
					+ " where payment_status=?";
			ps = connection.prepareStatement(insert);
			ps.setInt(1, DBColumnConstants.paymentStatusFalse);
			rs = ps.executeQuery();
			if (rs.next()) {
				pendingAmount = Integer.toString(rs.getInt("pendingAmount"));
				pendingInvoice = Integer.toString(rs.getInt("pendingStatus"));
				// result.add(new
				// InvoiceVo(pendingInvoice,pendingAmount,null,null));
				invoiceVo.setPendingAmount(pendingAmount);
				invoiceVo.setPaymentStatus(pendingInvoice);
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in getAdminInvoiceSummaryDao "
							+ e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(ps, rs);
		}

		return result;
	}

	public ArrayList<InvoiceVo> getAdminInvoiceSummaryDao2(InvoiceVo invoiceVo)
			throws DAOException {
		log.info("getAdminInvoiceSummaryDao2");
		ResultSet rs = null;
		PreparedStatement ps = null;
		String pendingAmount, totalPaid;
		ArrayList<InvoiceVo> result = new ArrayList<InvoiceVo>();

		try {
			String insert = "select sum(amount) as paidAmount, count(payment_status) as totalPaid from "
					+ DBColumnConstants.invoiceTable
					+ " where payment_status= ?";
			ps = connection.prepareStatement(insert);
			ps.setInt(1, DBColumnConstants.paymentStatusTrue);
			rs = ps.executeQuery();
			if (rs.next()) {
				pendingAmount = Integer.toString(rs.getInt("paidAmount"));
				totalPaid = Integer.toString(rs.getInt("totalPaid"));
				invoiceVo.setPaidAmount(pendingAmount);
				invoiceVo.setPaidInvoice(totalPaid);
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in getAdminInvoiceSummaryDao2 "
							+ e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(ps, rs);
		}

		return result;
	}

	public ArrayList<InvoiceVo> getVendorInvoiceSummaryDao(InvoiceVo invoiceVo)
			throws DAOException {
		log.info("getVendorInvoiceSummaryDao");
		ResultSet rs = null;
		PreparedStatement ps = null;

		String pendingAmount, pendingInvoice;

		ArrayList<InvoiceVo> result = new ArrayList<InvoiceVo>();
		try {
			String insert = "select sum(amount) as pendingAmount, count(payment_status) as pendingStatus from "
					+ DBColumnConstants.invoiceTable
					+ " where email=? and payment_status=?";
			ps = connection.prepareStatement(insert);
			ps.setString(1, invoiceVo.getEmail());
			ps.setInt(2, DBColumnConstants.paymentStatusFalse);
			rs = ps.executeQuery();
			if (rs.next()) {
				pendingAmount = Integer.toString(rs.getInt("pendingAmount"));
				pendingInvoice = Integer.toString(rs.getInt("pendingStatus"));
				invoiceVo.setPendingAmount(pendingAmount);
				invoiceVo.setPaymentStatus(pendingInvoice);
			}

		} catch (SQLException e) {
			log.error(
					"SQLException getSelectedInvoiceDetailsBO "
							+ e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(ps, rs);
		}

		return result;
	}

	public ArrayList<InvoiceVo> getVendorInvoiceSummaryDao2(InvoiceVo invoiceVo)
			throws DAOException {
		log.info("getVendorInvoiceSummaryDao2");
		ResultSet rs = null;
		PreparedStatement ps = null;
		log.getLoggerRepository();
		// InvoiceVo result =null;
		String pendingAmount, totalPaid;
		ArrayList<InvoiceVo> result = new ArrayList<InvoiceVo>();
		try {
			String insert = "select sum(amount) as paidAmount, count(payment_status) as totalPaid from "
					+ DBColumnConstants.invoiceTable
					+ " invoice where email=? and payment_status=?";
			ps = connection.prepareStatement(insert);
			ps.setString(1, invoiceVo.getEmail());
			ps.setInt(2, DBColumnConstants.paymentStatusTrue);
			rs = ps.executeQuery();
			if (rs.next()) {
				pendingAmount = Integer.toString(rs.getInt("paidAmount"));
				totalPaid = Integer.toString(rs.getInt("totalPaid"));
				invoiceVo.setPaidAmount(pendingAmount);
				invoiceVo.setPaidInvoice(totalPaid);
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in getVendorInvoiceSummaryDao2 "
							+ e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(ps, rs);
		}

		return result;
	}

	public ArrayList<InvoiceVo> searchvendorInvoiceDetailsDao(
			InvoiceVo invoiceVo) throws DAOException {
		log.info("searchvendorInvoiceDetailsDao");
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<InvoiceVo> invoiceVO = new ArrayList<InvoiceVo>();
		String invoiceId, email, creationDate, paymentStatus, amount;
		String searchElement = invoiceVo.getText();
		String invoiceDate = invoiceVo.getInvoiceDate();
		String dueDate = invoiceVo.getDueDate();
		System.out.println("Due Date" + dueDate);
		System.out.println("Invoice Date" + invoiceDate);
		try {
			String insert = "select invoice_id,email,invoice_creation_date,due_date,"
					+ "payment_status,amount FROM invoice where invoice_id LIKE \"%"
					+ searchElement
					+ "%\" "
					+ "or name LIKE \"%"
					+ searchElement
					+ "%\" or email LIKE \"%"
					+ searchElement
					+ "%\" or payment_status LIKE \"%"
					+ searchElement
					+ "%\" or amount LIKE \"%"
					+ searchElement
					+ "%\" and email=?";

			ps = connection.prepareStatement(insert);
			ps.setString(1, invoiceVo.getEmail());
			rs = ps.executeQuery();
			while (rs.next()) {

				invoiceId = rs.getString("invoice_id");
				email = rs.getString("email");

				creationDate = String(rs.getDate("invoice_creation_date"));
				dueDate = String(rs.getDate("due_date"));
				paymentStatus = rs.getString("payment_status");
				amount = Integer.toString(rs.getInt("amount"));

				invoiceVO.add(new InvoiceVo(invoiceId, email, creationDate,
						dueDate, paymentStatus, amount));
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in searchvendorInvoiceDetailsDao "
							+ e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}
		return invoiceVO;
	}

	public ArrayList<InvoiceVo> searchAllInvoiceDetailsDao(InvoiceVo invoiceVo)
			throws DAOException {
		log.info("searchAllInvoiceDetailsDao");
		log.getLoggerRepository();
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<InvoiceVo> invoiceVO = new ArrayList<InvoiceVo>();
		String invoiceId, email, creationDate, dueDate, paymentStatus, amount;
		String searchElement = invoiceVo.getText();
		System.out.println("In Dao Search element" + searchElement);
		try {

			String insert = "select invoice_id,email,invoice_creation_date,due_date,"
					+ "case when payment_status=? Then 'Unpaid' else 'Paid' end as payment_status,amount FROM "
					+ DBColumnConstants.invoiceTable
					+ " where invoice_id LIKE \"%"
					+ searchElement
					+ "%\" "
					+ "or name LIKE \"%"
					+ searchElement
					+ "%\" or email LIKE \"%"
					+ searchElement
					+ "%\" or payment_status LIKE \"%"
					+ searchElement
					+ "%\" or amount LIKE \"%" + searchElement + "%\"";

			ps = connection.prepareStatement(insert);
			ps.setInt(1, DBColumnConstants.paymentStatusFalse);
			rs = ps.executeQuery();
			// .out.println("In Try Search 23 Dao");
			while (rs.next()) {

				invoiceId = rs.getString("invoice_id");
				email = rs.getString("email");
				// .out.println("Invoice id Before Conversion" + invoiceId);
				creationDate = String(rs.getDate("invoice_creation_date"));
				dueDate = String(rs.getDate("due_date"));
				paymentStatus = rs.getString("payment_status");
				amount = Integer.toString(rs.getInt("amount"));
				// .out.println("Invoice id " + invoiceId);

				invoiceVO.add(new InvoiceVo(invoiceId, email, creationDate,
						dueDate, paymentStatus, amount));
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in searchAllInvoiceDetailsDao "
							+ e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}

		return invoiceVO;
	}

	public List<InvoiceVo> getAllInvoiceDetailsBO(InvoiceVo invoiceVo,
			int start, int end) throws DAOException {
		log.info("getAllInvoiceDetailsBO");
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<InvoiceVo> invoiceVO = new ArrayList<InvoiceVo>();
		String invoiceId, email, creationDate, dueDate, paymentStatus, amount;

		try {
			String insert = "Select invoice_id,email,invoice_creation_date,due_date,amount, case when payment_status=? Then 'Unpaid' else 'Paid' end as payment_status from "
					+ DBColumnConstants.invoiceTable + " limit ?,? ;";
			ps = connection.prepareStatement(insert);
			ps.setInt(1, DBColumnConstants.paymentStatusFalse);
			ps.setInt(2, start);
			ps.setInt(3, DBColumnConstants.limit);
			rs = ps.executeQuery();
			while (rs.next()) {

				invoiceId = rs.getString("invoice_id");
				email = rs.getString("email");
				// .out.println("Invoice id Before Conversion" + invoiceId);
				creationDate = String(rs.getDate("invoice_creation_date"));
				dueDate = String(rs.getDate("due_date"));
				paymentStatus = rs.getString("payment_status");
				amount = Integer.toString(rs.getInt("amount"));
				// .out.println("Invoice id " + invoiceId);

				invoiceVO.add(new InvoiceVo(invoiceId, email, creationDate,
						dueDate, paymentStatus, amount));
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in getAllInvoiceDetailsBO " + e.getMessage(),
					e);
			throw new DAOException();
		} finally {
			close(ps, rs);
		}
		return invoiceVO;

	}

	public List<InvoiceVo> getVendorInvoiceDetailsBO(InvoiceVo invoiceVo,
			int start, int end) throws DAOException {
		log.info("getSelectedInvoiceDetailsBO");
		ResultSet rs = null;
		PreparedStatement ps = null;
		ArrayList<InvoiceVo> invoiceVO = new ArrayList<InvoiceVo>();
		String invoiceId, email, creationDate, dueDate, paymentStatus, amount;

		try {
			String insert = "Select invoice_id,email,invoice_creation_date,due_date,amount, case when payment_status=? Then 'Unpaid' else 'Paid' end as payment_status from "
					+ DBColumnConstants.invoiceTable
					+ " where email = ? limit ?,? ;";
			ps = connection.prepareStatement(insert);
			ps.setInt(1, DBColumnConstants.paymentStatusFalse);
			ps.setString(2, invoiceVo.getEmail());

			ps.setInt(3, start);
			ps.setInt(4, DBColumnConstants.limit);
			rs = ps.executeQuery();
			while (rs.next()) {

				invoiceId = rs.getString("invoice_id");
				email = rs.getString("email");
				// .out.println("Invoice id Before Conversion" + invoiceId);
				creationDate = String(rs.getDate("invoice_creation_date"));
				dueDate = String(rs.getDate("due_date"));
				paymentStatus = rs.getString("payment_status");
				amount = Integer.toString(rs.getInt("amount"));
				// .out.println("Invoice id " + invoiceId);

				invoiceVO.add(new InvoiceVo(invoiceId, email, creationDate,
						dueDate, paymentStatus, amount));
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in getSelectedInvoiceDetailsBO "
							+ e.getMessage(), e);
			throw new DAOException();
		} finally {
			close(ps, rs);
		}
		return invoiceVO;
	}

	public ArrayList<InvoiceVo> searchAllInvoiceDetailsByDateDao(
			InvoiceVo invoiceVo) throws Exception {
		log.info("searchAllInvoiceDetailsByDateDao");

		ResultSet rs = null;
		PreparedStatement ps = null;

		ArrayList<InvoiceVo> invoiceVO = new ArrayList<InvoiceVo>();
		String invoiceId, email, creationDate, paymentStatus, amount, dueDateRes;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		java.util.Date date = sdf.parse(invoiceVo.getInvoiceDate());
		System.out.println("Date Formatted " + sdf.format(date));

		java.sql.Date invoiceDate = new java.sql.Date(date.getTime());
		java.util.Date date2 = sdf.parse(invoiceVo.getDueDate());
		java.sql.Date dueDate = new java.sql.Date(date2.getTime());

		try {
			String insert = "select invoice_id,email,invoice_creation_date,due_date,"
					+ "payment_status,amount FROM invoice where invoice_creation_date  between invoice_creation_date =? and due_date = ? ";
			ps = connection.prepareStatement(insert);
			ps.setDate(1, invoiceDate);
			ps.setDate(2, dueDate);
			rs = ps.executeQuery();
			while (rs.next()) {

				invoiceId = rs.getString("invoice_id");
				email = rs.getString("email");
				creationDate = String(rs.getDate("invoice_creation_date"));
				dueDateRes = String(rs.getDate("due_date"));
				paymentStatus = rs.getString("payment_status");
				amount = Integer.toString(rs.getInt("amount"));

				invoiceVO.add(new InvoiceVo(invoiceId, email, creationDate,
						dueDateRes, paymentStatus, amount));
			}

		} catch (SQLException e) {
			log.error(
					"SQLException in searchAllInvoiceDetailsByDateDao "
							+ e.getMessage(), e);
			throw new DAOException();

		} finally {
			close(ps, rs);
		}
		return invoiceVO;
	}
}
