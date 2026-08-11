package com.cognizant.lms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.lms.models.Book;
import com.cognizant.lms.services.BookServices;

@RestController
@RequestMapping(path = "/api/library")
public class LibraryRestController {
	private BookServices bookServices;
	
	public LibraryRestController(BookServices bookServices) {
		super();
		this.bookServices = bookServices;
	}

	@GetMapping
	public List<Book> showAllBooks(){
		List<Book> books = bookServices.getBooks();
		return books;
	}
	
	@GetMapping(path = "/{bookId}", 
				produces = {"application/xml", "application/json" })
	public ResponseEntity<?> searchBookById(@PathVariable Integer bookId){
		Book searchedBook = bookServices.getBookById(bookId);
		
		if(searchedBook != null) {
			return ResponseEntity.ok(searchedBook);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(consumes = {"application/json", "application/xml"},
				 produces = {"application/json", "application/xml"})
	public ResponseEntity<?> addNewBook(@RequestBody Book newBook){
		Book savedBook = bookServices.saveNewBook(newBook);
		
		if(savedBook != null) {
			return new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
		}
		return ResponseEntity.badRequest().body("Book with BookID: "+ newBook.getBookId() + " exists in library");
	}
	
	@DeleteMapping(path="/{bid}")
	public ResponseEntity<?> deleteExistingBook(@PathVariable Integer bid){
		boolean isBookDeleted = bookServices.deleteBookById(bid);
		if(isBookDeleted) 
			return ResponseEntity.ok().build();
		
		return ResponseEntity.badRequest().build();
	}	

	@PutMapping(path="/{bid}")
	public ResponseEntity<Book> modifyExistingBook(@PathVariable Integer bid, @RequestBody Book book){
		Book updatedBook = bookServices.modifyBook(bid, book);
		if(updatedBook != null) {
			return ResponseEntity.ok(updatedBook);
		}
		return ResponseEntity.notFound().build();
	}	
}
