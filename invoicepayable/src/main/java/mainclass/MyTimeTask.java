package mainclass;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.alacriti.invoice.constants.DBColumnConstants;
import com.alacriti.invoice.delegate.InvoiceDelegate;
import com.alacriti.invoice.delegate.VendorDelegate;
import com.alacriti.invoice.util.MailingService;
import com.alacriti.invoice.vo.PaymentVo;



public class MyTimeTask extends TimerTask {
	private static final Logger log = Logger
			.getLogger(InvoiceDelegate.class);
	MailingService sendMail = new MailingService();
	@Override
	public void run() {
		Date date = new Date();
		System.out.println(" In Timer Class at " + date);
		System.out.println("In Test");
		ArrayList<PaymentVo> resultReceived= new ArrayList<PaymentVo>();
		VendorDelegate vendordelegate= new VendorDelegate();
		String email=null;
		String message=null;
		String invoice;
		String sub="PaymentReceived";
	resultReceived= vendordelegate.mailToSend();
	if(resultReceived.size()==0)
		log.info("No Payments Received In Last 5 Hours");
	else
	for(int i=0;i<resultReceived.size();i++)
	{
		email=resultReceived.get(i).getEmail();
		invoice=resultReceived.get(i).getInvoice();
		message="Received Your Amount of  " + resultReceived.get(i).getAmount() + " Against your invoice No " + resultReceived.get(i).getInvoice() +". On "  +resultReceived.get(i).getPaidOn()+ " Thank You "; 
		System.out.println("Email Received " + i + resultReceived.get(i).getEmail());
		System.out.println("InvoiceVo Received " + i + resultReceived.get(i).getInvoice());
		System.out.println("Amount Received " + i + resultReceived.get(i).getAmount());
		System.out.println("PaidOn Received " + i + resultReceived.get(i).getPaidOn());
		sendMail.send(DBColumnConstants.fromEmail, DBColumnConstants.timePass, email, sub, message);
		mailSent(invoice);
	}
		
//		sendMail.send("smartboy1654@gmail.com","kmvhcu7818","amansaini011@gmail.com","Leave Notification","your leave has been successfully accepted by your: Test ");
	}
	private void mailSent(String invoice) {
		 VendorDelegate vendorDelegate= new VendorDelegate();
		 vendorDelegate.mailSent(invoice);
	}

}
