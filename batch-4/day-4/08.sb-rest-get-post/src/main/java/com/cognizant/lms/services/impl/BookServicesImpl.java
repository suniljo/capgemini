package com.cognizant.lms.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.lms.models.Book;
import com.cognizant.lms.repository.BookRepository;
import com.cognizant.lms.services.BookServices;

@Service
public class BookServicesImpl implements BookServices {
	private BookRepository bookRepo;
	private List<Book> books;
	
	//constructor injection
	@Autowired
	public BookServicesImpl(BookRepository bookRepo) {
		super();
		this.bookRepo = bookRepo;
		this.books = bookRepo.getAllBooks();
	}
	
	@Override
	public List<Book> getBooks() {		
		return books;
	}



	@Override
	public Book getBookById(Integer bid) {
		Book searchedBook = books.stream()
								 .filter(bk -> bk.getBookId().equals(bid))
								 .findFirst()
								 .orElse(null);
		return searchedBook;
	}

	@Override
	public Book saveNewBook(Book book) {
		boolean isBookExists = books.stream()
								    .anyMatch(bk -> bk.getBookId().equals(book.getBookId()));
		
		if(!isBookExists) {
			books.add(book);
			return book;
		}
		return null;
	}

	@Override
	public boolean deleteBookById(Integer bid) {
		for(int i=0; i<books.size(); i++) {
			Book bk = books.get(i);
			if(bk.getBookId().equals(bid)) {
				books.remove(i);
				return true;
			}
		}
		return false;
	}

	@Override
	public Book modifyBook(Integer bid, Book book) {
		for(int i=0; i<books.size(); i++) {
			Book bk = books.get(i);
			
			if(bid.equals(bk.getBookId())) {
				bk.setBookTitle(book.getBookTitle());
				bk.setBookPrice(book.getBookPrice());
				
				books.set(i, bk);
				
				return bk;
			}
		}//for
		return null;
	}

}
