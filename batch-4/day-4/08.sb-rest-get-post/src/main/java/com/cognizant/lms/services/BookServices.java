package com.cognizant.lms.services;

import java.util.List;

import com.cognizant.lms.models.Book;

public interface BookServices {
	public List<Book> getBooks();
	public Book getBookById(Integer bid);
	
	public Book saveNewBook(Book book);
	public boolean deleteBookById(Integer bid);
	
	public Book modifyBook(Integer bid, Book book);
}
