package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import org.apache.commons.mail.DataSourceResolver;
//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.ImageHtmlEmail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;


	public class ExtentReportManager implements ITestListener
	{

		public ExtentSparkReporter sparkReporter; //Ui of the report
		public ExtentReports extent;  //populate common info on the report
		public ExtentTest test;  //Creating test case entires in the report and update status of the test method
		
		String repName;
		
			public void onStart(ITestContext testContext) {
				
				/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
				Date dt=new Date();
				String currentdatetimestamp=df.format(dt);
				*/
				
				String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); 
				repName = "Test-Report-" + timestamp + ".html";
				sparkReporter=new ExtentSparkReporter(".\\reports\\"+ repName); //specify location of the 
				
				sparkReporter.config().setDocumentTitle("opencart Automation Report"); //Title of report
				sparkReporter.config().setReportName("opencart Functional Testing"); //Name of the report
				sparkReporter.config().setTheme(Theme.DARK);
				
				extent=new ExtentReports();
				extent.attachReporter(sparkReporter);
				
				extent.setSystemInfo("application", "opencart");
				extent.setSystemInfo("Module", "Admin");
				extent.setSystemInfo("Sub Module", "Customers");
				
				extent.setSystemInfo("User Name", System.getProperty("user.name"));
				extent.setSystemInfo("Environment", "QA");
				
				String os= testContext.getCurrentXmlTest().getParameter("os");
				extent.setSystemInfo("Operating System", os);

				String browser= testContext.getCurrentXmlTest().getParameter("browser");
				extent.setSystemInfo("Browser", browser);
				
				List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
				if(!includedGroups.isEmpty()) {
					extent.setSystemInfo("Groups", includedGroups.toString());
				}
			}
			
			public void onTestSuccess(ITestResult result) {
				
				test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
				test.assignCategory(result.getMethod().getGroups()); // to display groups in report
				test.log(Status.PASS, result.getName()+ "got successfully executed"); //update status p/f/s
			}
			
			public void onTestFailure(ITestResult result) {
				test = extent.createTest(result.getTestClass().getName()); 
				test.assignCategory(result.getMethod().getGroups()); // to display groups in report

				test.log(Status.FAIL, result.getName() + "got failed");
				test.log(Status.INFO, result.getThrowable().getMessage());
				
				try 
				{
					String imgpath= new BaseClass().captureScreen(result.getName());
					test.addScreenCaptureFromPath(imgpath);
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
			}
			
			public void onTestSkipped(ITestResult result) {
				
				test = extent.createTest(result.getName());
				test.assignCategory(result.getMethod().getGroups()); // to display groups in report
				test.log(Status.SKIP, result.getName() + "got skipped");
				test.log(Status.INFO, result.getThrowable().getMessage());

			}
			
			public void onFinish(ITestContext testContext) {
				
				extent.flush();
				
				String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
				File extentReport=new File(pathOfExtentReport);
				
				try
				{
					Desktop.getDesktop().browse(extentReport.toURI());
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
			/*	try
				{
					URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
					
					//create the email message
					
					ImageHtmlEmail email=new ImageHtmlEmail();
					email.setDataSourceResolver(new DataSourceResolver(url));
					email.setHostName("smtp.googleemail.com");
					email.setSmtpPort(465);
					email.setAuthenticator(new DefaultAuthenticator("reddyvenkatesh529@gmail.com", "passowrd"));
					email.setSSLOnConnect(true);
					email.setFrom("reddyvenkatesh529@gmail.com");//sender
					email.setSubject("Test Results");
					email.setMsg("Please find Attached Repoert......");
					email.addTo("nagavenkatesh529@gmail.com"); //receiver
					email.attach(url,"extent report", "please check report");
					email.send(); //send the email
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
				}*/
				
			}
			
			
	}

