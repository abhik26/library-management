package library_operations;

public class Transactions {
	private int custId, bookId, id;
	private String issueDate, returnDate;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCustId() {
		return custId;
	}
	
	public void setCustId(int cust_id) {
		this.custId = cust_id;
	}
	
	public int getBookId() {
		return bookId;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	public String getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	
	public String getReturnDate() {
		return returnDate;
	}
	
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	
	public String toString() {
		return "Transaction id: " + id + ", Customer id: " + custId + ", Book id: " + bookId + ", Issue date: " + issueDate + ", Return date: " + returnDate;
	}
}