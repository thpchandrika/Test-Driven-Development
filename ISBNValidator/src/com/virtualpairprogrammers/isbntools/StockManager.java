package com.virtualpairprogrammers.isbntools;

public class StockManager {
	
	private ExternalISBNDataService webservice;
	private ExternalISBNDataService databaseservice;

	public void setWebService(ExternalISBNDataService service) {
		this.webservice = service;
	}
	
	public void setDatabaseService(ExternalISBNDataService service) {
		this.databaseservice = service;
	}


	public String getLocatorCode(String isbn) {		
		Book book = webservice.lookup(isbn);
		if (book == null) {
			book = databaseservice.lookup(isbn);
		}
		StringBuilder locatorBuilder = new StringBuilder();
		locatorBuilder.append(isbn.substring(isbn.length()-4));
		locatorBuilder.append(book.getAuthor().substring(0,1));
		locatorBuilder.append(book.getTitle().split(" ").length);
		return locatorBuilder.toString();
	}

}
