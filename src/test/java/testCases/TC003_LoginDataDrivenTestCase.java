package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/* Data is valid -login success -test pass - logout
 * data is valid - login failed - test fail
 * 
 * data is inavlid -- ligin success -- test fail-- logout
 * dta is invalid -- login failed -- test pass
 */
public class TC003_LoginDataDrivenTestCase extends BaseClass{
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class,groups="Datadriven") //getting data provider from different class
	public void verify_LoginDatadrivenTestCase(String email, String pwd, String exp )
	{
		logger.info("********Starting TC003_LoginDDT******");
		try
		{
		//homepage
				HomePage hp=new HomePage(driver);
				hp.clickMyAccount();
				hp.clickLogin();
				
				//Loginpage
				LoginPage lp=new LoginPage(driver);
				lp.setEmail(email);
				lp.setPassword(pwd);
				lp.clickLogin();
				
				//myaccount
				MyAccountPage macc=new MyAccountPage(driver);
				boolean targetpage=macc.isMyAccountPageExists();
				
				//Assert.assertEquals(targetpage, true, "Login failed");
				//Assert.assertTrue(targetpage);
				
				if(exp.equalsIgnoreCase("valid")) 
				{
					if(targetpage==true)
					{
						macc.clickLogout();
						Assert.assertTrue(true);
						
					}
					else
					{
						Assert.assertTrue(false);
					}
				}
				
				if(exp.equalsIgnoreCase("invalid"))
				{
					if(targetpage==true)
					{
						macc.clickLogout();
						Assert.assertTrue(false);
					}
					else
					{
						Assert.assertTrue(true);
					}
				}
				
		      }
		catch(Exception e)
		{
			Assert.fail();
		}
				logger.info("********Finished TC003_LoginDDT******");

	}

}
