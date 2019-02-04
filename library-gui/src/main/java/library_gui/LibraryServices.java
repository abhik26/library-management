package library_gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import library_operations.*;


@Path("/library-services")
public class LibraryServices {
	Operations operations = new Operations();
	
	/*List books*/
	@Path("/books")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> listBooks() {
    	List<Book> bookList = operations.listAllBooks();
        return bookList;
    }
	
	/* Add book*/
	@Path("/books")
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String addBook(Book book) {
		String response="";
		String title="", author="";
		int copies=0;
		title = book.getTitle();
		author = book.getAuthor();
		copies = book.getCopies();
		System.out.println(book.toString());
		response = operations.addBook(title, author, copies);
		response = "\"" + response + "\"";
		return response;
	}
	
	/*Delete book*/
	@Path("/books")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteBook(Book book) {
		String response="";
		response = operations.deleteBook(book.getId());
		response = "\"" + response + "\"";
		return response;
	}
	
	/*Update book*/
	@Path("/books")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateBook(Book book) {
		String response="";
		response = operations.updateBook(book.getId(), book.getTitle(), book.getAuthor(), book.getCopies());
		response = "\"" + response + "\"";
		return response;
	}
	
	/*List customers*/
	@Path("/customers")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> listCustomers() {
		List<Customer> customerList = operations.listAllCustomers();
		return customerList;
	}
	
	/*Add customer*/
	@Path("/customers")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addCustomer(Customer customer) {
		String response="";
		response = operations.addCustomer(customer.getName(), customer.getAddress(), customer.getContact());
		response = "\"" + response + "\"";
		return response;
	}
	
	/*Delete customer*/
	@Path("/customers")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCustomer(Customer customer) {
		String response="";
		response = operations.deleteCustomer(customer.getId());
		response = "\"" + response + "\"";
		return response;
	}
	
	/*Update customer*/
	@Path("/customers")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCustomer(Customer customer) {
		String response="";
		response = operations.updateCustomer(customer.getId(), customer.getName(), customer.getAddress(), customer.getContact());
		response = "\"" + response + "\"";
		return response;
	}
	
	/*List transactions*/
	@Path("/transactions")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transactions> listTransactions() {
		List<Transactions> transactionList = operations.listAllTransactions();
		return transactionList;
	}
	
	/*Lend book*/
	@Path("/transactions")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String lendBook(Transactions transactions) {
		String response="";
		System.out.println(transactions.toString());
		response = operations.lendBook(transactions.getCustId(), transactions.getBookId());
		response = "\"" + response + "\"";
		return response;
	}
	
	/*Return book*/
	@Path("/transactions")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String returnBook(Transactions transactions) {
		String response="";
		response = operations.returnBook(transactions.getCustId(), transactions.getBookId());
		response = "\"" + response + "\"";
		return response;
	}
}
