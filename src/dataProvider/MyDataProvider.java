package dataProvider;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class MyDataProvider {

	
		@DataProvider(name="SearchString")
		public Object[][] getDataFromDataProvider(Method m){
			if(m.getName().equalsIgnoreCase("googleSearch")) {
				return new Object[][] {
					{"TestNG"}
				};
			}
			
			return null;
			
		}
}
