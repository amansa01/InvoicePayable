package mainclass;

import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements javax.servlet.ServletContextListener
{
	public static void main(String[] args) {
	 
	   
	  }

	public void contextInitialized(ServletContextEvent sce) {
		 Timer timer = new Timer();
		 Date date=new Date();
		 System.out.println("In Date" + date);
//		// timer.schedule(new MyTimeTask(), date);
////		 int period = 100000000;//10secs
////	    timer.schedule(new MyTimeTask(), date, period );
		 ScheduledExecutorService service = Executors
	                .newSingleThreadScheduledExecutor();
	service.scheduleAtFixedRate(new MyTimeTask(), 0, 5, TimeUnit.HOURS);

	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
}
