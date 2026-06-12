package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccounyRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registration()
	{
		logger.info("******* Starting TC001 AccountRegistrationTest ******");
		try
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount Link ");

		hp.clickRegister();
		logger.info("Clicked on Register Link ");
		
		AccounyRegistrationPage regpage=new AccounyRegistrationPage(driver);
		logger.info("Providing customer details..... ");

		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com"); //randomly generated the email
		regpage.setTelephone(randomeNumber());
		
		String password=randomeAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();  
		regpage.clickContinue();
		
		logger.info("Validating expected message ");
		String confmsg=regpage.getConfirmationMsg();
		
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed....");
			logger.debug("Debug logs.....");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!!!");
		}
		catch (Exception e)
		{
			Assert.fail();
		}
		
		logger.info("******* Fineshed TC001 AccountRegistrationTest ******");

		
	}
	
	
}
