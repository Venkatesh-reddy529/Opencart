package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	//DataProvider 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path =".\\testData\\Qafox_loginTest.xlsx"; //taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path); //creating an object for xlutility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols]; //created for two dimensional array which can store

		for(int i=1;i<=totalrows;i++)
		{
			for(int j=0;j<totalcols;j++)
			{
				logindata[i-1][j]=xlutil.getCellData("sheet1", i, j); //1,0 {[i-1] ---array inderx strsts from 0}
			}
		}
		return logindata; //returing two dimensional array
	}
	
	//DataProvider 2
	
	//DataProvider 3
}
