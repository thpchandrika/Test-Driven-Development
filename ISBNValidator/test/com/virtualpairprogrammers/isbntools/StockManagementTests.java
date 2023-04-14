package com.virtualpairprogrammers.isbntools;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class StockManagementTests {

	ExternalISBNDataService testWebService;
	StockManager stockManager;
	ExternalISBNDataService testDatabaseService;

	//called before each test methods
	@Before
	public  void setup(){
      testWebService = mock(ExternalISBNDataService.class);
	  stockManager = new StockManager();
	  stockManager.setWebService(testWebService);
	  testDatabaseService= mock(ExternalISBNDataService.class);
	  stockManager.setDatabaseService(testDatabaseService);
	}

	@Test
	public void testCanGetACorrectLocatorCode() {

//		ExternalISBNDataService testWebService = new ExternalISBNDataService() {
//			@Override
//			public Book lookup(String isbn) {
//				return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
//			}
//		};
//		ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
//			@Override
//			public Book lookup(String isbn) {
//				return null;
//			}
//		};

		//ExternalISBNDataService testWebService = mock(ExternalISBNDataService.class);
		when(testWebService.lookup(anyString())).thenReturn(
				new Book("0140177396", "Of Mice And Men", "J. Steinbeck")
		);

		//ExternalISBNDataService testDatabaseService = mock(ExternalISBNDataService.class);
		when(testDatabaseService.lookup(anyString())).thenReturn(null);

		//StockManager stockManager = new StockManager();
		//stockManager.setWebService(testWebService);
		//stockManager.setDatabaseService(testDatabaseService);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);
		assertEquals("7396J4", locatorCode);
	}

	@Test
	public void databaseIsUsedIfDataIsPresent() {
//		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
//		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

		when(testDatabaseService.lookup("0140177396")).thenReturn(new Book("0140177396","abc","abc"));

//		StockManager stockManager = new StockManager();
//		stockManager.setWebService(webService);
//		stockManager.setDatabaseService(databaseService);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);

		//verify(testdatabaseService, times(1)).lookup("0140177396");
		verify(testDatabaseService).lookup("0140177396");  //same as above
		//verify(testWebService, never()).lookup(anyString());
	}

	@Test
	public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
//		ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
//		ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

		when(testDatabaseService.lookup("0140177396")).thenReturn(null);
		when(testWebService.lookup("0140177396")).thenReturn(new Book("0140177396","abc","abc"));

//		StockManager stockManager = new StockManager();
//		stockManager.setWebService(testWebService);
//		stockManager.setDatabaseService(testDatabaseService);

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);

		//verify(databaseService).lookup("0140177396");
		verify(testWebService).lookup("0140177396");
	}

}
