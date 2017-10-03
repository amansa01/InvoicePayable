package com.alacriti.invoice.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.alacriti.invoice.delegate.InvoiceDelegate;
import com.alacriti.invoice.util.model;
import com.alacriti.invoice.vo.InvoiceVo;
import com.alacriti.invoice.vo.PaymentVo;

@Path("/invoice")
public class InvoiceResource {
	private static final Logger log = Logger.getLogger(InvoiceResource.class);

	@POST
	@Path("/checkInvoice")
	@Consumes("multipart/form-data")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<InvoiceVo> checkInvoice(@MultipartForm model form,
			@Context HttpServletRequest request) throws Exception {
		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		ArrayList<InvoiceVo> invoiceVo = new ArrayList<InvoiceVo>();
		invoiceVo = invoiceDelegate.receiveInvoiceDelegate(form);
		return invoiceVo;
	}

	@POST
	@Path("/createInvoice")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void receiveBill(InvoiceVo invoiceVo,
			@Context HttpServletRequest request) {
		// log.debugPrintCurrentMethodName();
		log.getLoggerRepository();
		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		invoiceDelegate.createInvoiceDelegate(invoiceVo);

	}

	@POST
	@Path("/invoiceDetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<InvoiceVo> invoiceDetail(InvoiceVo invoiceVo,
			@Context HttpServletRequest request) throws Exception {
		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		ArrayList<InvoiceVo> invoiceReturn = new ArrayList<InvoiceVo>();
		invoiceReturn = invoiceDelegate.getInvoiceDetails(invoiceVo);
		return invoiceReturn;
	}

	

	@POST
	@Path("/searchTotal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response totalSearchPages(InvoiceVo invoiceVo, @Context HttpServletRequest request) {

		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		int response = invoiceDelegate.searchInvoiceDelegate(invoiceVo);
		return Response.status(200).entity(response).build();

	}	
	
	@POST
	@Path("/invoiceSummary")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public InvoiceVo invoiceSummary(InvoiceVo invoiceVo,
			@Context HttpServletRequest request) throws Exception {
		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		InvoiceVo invoiceReturn = invoiceDelegate.getInvoiceSummary(invoiceVo);
		return invoiceReturn;
	}

	@POST
	@Path("/payment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String payToVendor(PaymentVo paymentVo,
			@Context HttpServletRequest request) {

		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		String response=invoiceDelegate.payToVendorDelegate(paymentVo);
		return response;


	}

	@POST
	@Path("/total")
	@Produces(MediaType.APPLICATION_JSON)
	public Response totalPages(String email, @Context HttpServletRequest request) {
		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		int response = invoiceDelegate.totalInvoiceDelegate(email);
		return Response.status(200).entity(response).build();

	}

	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvoiceVo> invoiceSearch(InvoiceVo invoiceVo, @QueryParam("start") int start,
			@QueryParam("end") int end) {
		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		List<InvoiceVo> invoiceReturn = new ArrayList<InvoiceVo>();
		invoiceReturn = invoiceDelegate.getInvoiceSearch(invoiceVo,start,end);
		return invoiceReturn;
	}
	
	@GET
	@Path("/pageList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvoiceVo> getInvoicePagination(
			@QueryParam("email") String email, @QueryParam("start") int start,
			@QueryParam("end") int end) {
		InvoiceVo invoiceVo = new InvoiceVo();
		invoiceVo.setEmail(email);
		List<InvoiceVo> resultList = null;
		InvoiceDelegate invoiceDelegate = new InvoiceDelegate();
		resultList = invoiceDelegate.getInvoicePageList(invoiceVo, start, end);
		return resultList;

	}

	

}