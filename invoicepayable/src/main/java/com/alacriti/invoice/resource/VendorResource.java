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

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.alacriti.invoice.delegate.VendorDelegate;
import com.alacriti.invoice.util.MailingService;
import com.alacriti.invoice.util.SessionUtility;
import com.alacriti.invoice.util.model;
import com.alacriti.invoice.vo.VendorVo;

@Path("/vendor")
public class VendorResource {

	MailingService sendMail = new MailingService();



	@POST
	@Path("/checkvendorlist")
	@Consumes("multipart/form-data")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<VendorVo> checkVendor(@MultipartForm model form,
			@Context HttpServletRequest request) throws Exception {
		ArrayList<VendorVo> venderVo = new ArrayList<VendorVo>();
		VendorDelegate vendorDelegate = new VendorDelegate();
		venderVo = vendorDelegate.checkVendorDelegate(form);
		return venderVo;
	}

	@GET
	@Path("/vendorlist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<VendorVo> vendorList(@QueryParam("email") String email, @QueryParam("start") int start,
			@QueryParam("end") int end) {
	VendorVo vendor = new VendorVo();
	vendor.setEmail(email);
		ArrayList<VendorVo> vendorVo = new ArrayList<VendorVo>();
		List<VendorVo> resultList = null;
		VendorDelegate vendorDelegate = new VendorDelegate();
		resultList = vendorDelegate.vendorListDelegate(vendor,start,end);
		return resultList;
	}

	
	@POST
	@Path("/createVendorList")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createVendor(ArrayList<VendorVo> vendors,
			@Context HttpServletRequest request) {
		VendorDelegate vendorDelegate = new VendorDelegate();
		vendorDelegate.createVendorDelegate(vendors);
		return Response.status(200).entity(vendors).build();
	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<VendorVo> vendorSearch(String vendorVo,
			@Context HttpServletRequest request) throws Exception {
		VendorDelegate vendorDelegate = new VendorDelegate();
		ArrayList<VendorVo> vendorReturn = new ArrayList<VendorVo>();
		vendorReturn = vendorDelegate.vendorSearchDelegate(vendorVo);
		return vendorReturn;
	}

	
	@POST
	@Path("/total")
	@Produces(MediaType.APPLICATION_JSON)
	public Response totalPages(String email, @Context HttpServletRequest request) {
		VendorDelegate vendorDelegate = new VendorDelegate();
		int response = vendorDelegate.totalVendorDelegate(email);
		return Response.status(200).entity(response).build();

	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void login(VendorVo vendorVo, @Context HttpServletRequest request)
			throws Exception {
		SessionUtility sessionUtility = new SessionUtility();
		sessionUtility.createSession(request, vendorVo);
	}


	@GET
	@Path("/clearSession")
	@Produces(MediaType.APPLICATION_JSON)
	public String clearSession(@Context HttpServletRequest request) {
		SessionUtility sessionUtility = new SessionUtility();

		boolean session = sessionUtility.destroySession(request);
		if (session) {
			return "Session Clear";
		} else
			return "Session Not Clear";
	}
	
}
