package library_operations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;
import org.hibernate.query.*;
import org.hibernate.cfg.Configuration;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class Operations {
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	Scanner sc = new Scanner(System.in);
    
	SessionFactory factory = new Configuration().configure().buildSessionFactory();
    
    /*function to list all the books present in the database*/
    public List<Book> listAllBooks() {
    	Session session = factory.openSession();
    	List<Book> result = session.createNativeQuery("select * from books", Book.class).list();
    	session.close();
    	return result;
    }
    
    /*function to add a book to books table*/
    public String addBook(String title, String author, int copies) {
    	
    	String result = null;
    	Session session = factory.openSession();  
    	Transaction t = session.beginTransaction();
    	Book book = new Book();    
        book.setTitle(title);
        book.setAuthor(author);
        book.setCopies(copies);
        try{
        	session.save(book);
        	t.commit();
        	result = "..Book successfully added..";
        } 
        catch (Exception e) {
        	result = e.getMessage();
        }
        session.close();
        return result;
    }
    
    /*function to delete a book from books table*/
    public String deleteBook(int id) {
    	
    	String result=null;
    	Session session = factory.openSession();
    	Transaction t = session.beginTransaction();
    	Book book = findBookById(id, session);
    	if(book!=null) {
    		try {
    			session.delete(book);
    			t.commit();
        		result = "..Book deleted successfully..";
    		} catch(Exception e) {
    			result = e.getMessage();
    		}
    	}
    	else
    		result="No such book found!!";
    	session.close();
		return result;
    }
    
    /*function to update a book details in books table*/
    public String updateBook(int id, String new_title, String new_author, int new_copies) {
    	
    	Book book;
    	String result=null;
    	Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		book = findBookById(id, session);
    	if (book != null) {
			book.setTitle(new_title);
			book.setAuthor(new_author);
			book.setCopies(new_copies);
			session.update(book);
			result = "..successfully updated..";
		}
		else {
			result = "No such book found!!";
		}
    	t.commit();
		session.close();
		return result;
    }
    
    /*function to find a book by its id in books table*/
    public Book findBookById(int id, Session session) {
    	Book book = session.get(Book.class, id);
    	return book;
    	
    }
    
    /*function to find customer by its id in customers table*/
    public Customer findCustById(int cust_id, Session session) {
    	Customer cust = session.get(Customer.class, cust_id);
    	return cust;
    }
    
    /*function to search for a book in books table and print its details*/
    public String searchBook(int id) {
    	String result = null;
    	Session session = factory.openSession();
    	Book book = findBookById(id, session);
    	if (book != null) {
    		result = book.toString();
    	}
    	else {
    		result = "No such book found!!";
    	}
    	session.close();
    	return result;
    }
    
    /*function to list all the transactions present in the database*/
    public List<Transactions> listAllTransactions() {
    	Session session = factory.openSession();
    	List<Transactions> result = (List<Transactions>)session.createNativeQuery("select * from transactions", Transactions.class).list();
    	session.close();
    	return result;
    }
    
    /*function to lend a book to a customer*/
    public String lendBook(int cust_id, int book_id) {
    	
    	String result=null;
    	Session session = factory.openSession();
    	Transaction t = session.beginTransaction();
    	Book book = findBookById(book_id, session);
    	Customer cust = findCustById(cust_id, session);
    	if (book!=null && cust!=null) {
    		
    		Query query1 = session.createQuery("select count(*) from Transactions where book_id = :book_id and return_date is NULL");
    		query1.setParameter("book_id", book_id);
    		int booksIssued = Integer.parseInt(query1.getSingleResult().toString());
    		
    		//Query query2 = session.createQuery("select copies from Books where id = :id");
    		//query2.setParameter("id", book_id);
    		int copies = book.getCopies();
    		
    		Query query3 = session.createQuery("select count(*) from Transactions where cust_id = :cust_id and book_id = :book_id and return_date is NULL");
    		query3.setParameter("cust_id", cust_id);
    		query3.setParameter("book_id", book_id);
    		int issuable = Integer.parseInt(query3.getSingleResult().toString());
    		
    		if (booksIssued < copies && issuable < 1) {
    			Transactions ts = new Transactions();
    			ts.setCustId(cust_id);
    			ts.setBookId(book_id);
    			ts.setIssueDate(new Timestamp(System.currentTimeMillis()).toString());
    			session.save(ts);
    			t.commit();
    			result = "..Book successfully lended..";
    		}
    		else if (issuable > 0) {
    			result = "The book is already issued to the user!!";
    		}
    		else if (booksIssued >= copies) {
    			result = "No copies available!!";
    		}
    	}
    	else if (book == null) {
    		result = "Wrong book id!!";
    	}
    	else if (cust == null) {
    		result = "Wrong customer id!!";
    	}
		session.close();
		return result;
    }
    
    /*function to return book back to library*/
    public String returnBook(int cust_id, int book_id) {
    	
    	String result = null;
    	Session session = factory.openSession();
    	Transaction t = session.beginTransaction();
    	Book book = findBookById(book_id, session);
    	Customer cust = findCustById(cust_id, session);
    	if (cust != null && book != null) {
    		Query query = session.createQuery("select id from Transactions where cust_id = :cust_id and book_id = :book_id and return_date is NULL");
    		query.setParameter("cust_id", cust_id);
    		query.setParameter("book_id", book_id);
    		int id = 0;
    		Transactions tc = null;
    		try {
    			id = Integer.parseInt(query.getSingleResult().toString());
    		} catch(Exception e) {
    			
    		}
    		tc = session.get(Transactions.class, id);
    		if (tc != null) {
    			tc.setReturnDate(new Timestamp(System.currentTimeMillis()).toString());
        		session.update(tc);
        		t.commit();
        		result = "..Book successfully returned..";
    		}
    		else
    			result = "No such transactions!!";
    	}
    	else if (cust == null) {
    		result = "Invalid customer id!!";
    	}
    	else if (book == null) {
    		result = "Invalid book id!!";
    	}
		session.close();
		return result;
    }
    
    /*function to list all customers in the database*/
    public List<Customer> listAllCustomers() {
    	Session session = factory.openSession();
    	List<Customer> result = session.createNativeQuery("select c.* from Customers c", Customer.class).getResultList();
    	session.close();
    	return result;
    }
    
    /*function to add customer*/
    public String addCustomer(String name, String address, long contact) {
    	Customer customer = new Customer();
    	String result="";
    	Session session = factory.openSession();
    	Transaction t = session.beginTransaction();
    	customer.setName(name);
    	customer.setAddress(address);
    	customer.setContact(contact);
    	try {
    		session.save(customer);
    		t.commit();
    		result = "..customer successfully added..";
    	} catch(Exception e) {
    		result = e.getMessage();
    	}
    	session.close();
    	return result;
    }
    
    /*function to delete customer*/
    public String deleteCustomer(int id) {
    	String result="";
    	Session session = factory.openSession();
    	Transaction t = session.beginTransaction();
    	Customer customer = findCustById(id, session);
    	if (customer != null) {
    		try {
    			session.delete(customer);
    			t.commit();
        		result = "..Customer deleted successfully..";
    		} catch(Exception e) {
    			result = e.getMessage();
    		}
    	} else
    		result = "No such customer found!";
    	session.close();
    	return result;
    }
    
    /*function to update customer*/
    public String updateCustomer(int id, String name, String address, long contact) {
    	String result = "";
    	Session session = factory.openSession();
    	Transaction t = session.beginTransaction();
    	Customer customer = findCustById(id, session);
    	if (customer != null) {
    		customer.setName(name);
    		customer.setAddress(address);
    		customer.setContact(contact);
    		try {
    			session.update(customer);
    			t.commit();
    			result="..successfully updated..";
    		} catch(Exception e) {
    			result = e.getMessage();
    		}
    		session.update(customer);
    	} else
    		result = "No such customer found!";
    	session.close();
    	return result;
    }
    
    /*function to close the SessionFactory*/
    public void close() {
    	factory.close();
    }
}