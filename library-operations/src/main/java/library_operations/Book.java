package library_operations;

public class Book {
	private int id;
	private int copies;
	private String title, author;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public int getCopies() {
		return copies;
	}
	
	public void setCopies(int copies) {
		this.copies = copies;
	}
	
	public String toString() {
		return "Book id: " + id + ", Title: " + title + ", Author: " + author + ", Copies: " + copies;
	}
}