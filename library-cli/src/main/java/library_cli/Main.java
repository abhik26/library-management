package library_cli;

import java.util.*;
import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import library_operations.*;

public class Main {
	public static void main(String args[]) {
		int choice = 0;
		boolean looping = true;

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		Operations operations = new Operations();

		do {
			System.out.print("\n\n1:	Add a book\n2.	Delete a book\n3.	Update book details\n4.	Add a customer\n5.	Delete a customer\n6.	Update a customer\n");
			System.out.print("7.	Search a book\n8.	lend a book\n9.	return a book\n");
			System.out.print("10.	List all books\n11.	List all customers\n12.	List all transactions\n13.	exit\n");

			choice = sc.nextInt();

			switch (choice) {

			/* choice to add a book */
			case 1: {
				String title = null, author = null, result = null;
				int copies = 0;
				try {
					System.out.print("enter book title: ");
					title = br.readLine();
					System.out.print("enter book author: ");
					author = br.readLine();
					System.out.print("enter book copies: ");
					copies = sc.nextInt();
					while (copies < 1) {
						System.out.println("copies should be greater than zero!!");
						System.out.print("enter book copies: ");
						copies = sc.nextInt();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				result = operations.addBook(title, author, copies);
				System.out.println(result);
				break;
			}

			/* choice to delete a book */
			case 2: {
				System.out.print("enter book id: ");
				int id = sc.nextInt();
				String result = operations.deleteBook(id);
				System.out.println(result);
				break;
			}

			/* choice to update a book details */
			case 3: {
				int id = 0, new_copies = 0;
				String new_title = null, new_author = null, result = null;
				try {
					System.out.print("enter book id: ");
					id = sc.nextInt();
					System.out.print("enter new title: ");
					new_title = br.readLine();
					System.out.print("enter new author: ");
					new_author = br.readLine();
					System.out.print("enter new copies: ");
					new_copies = sc.nextInt();
					while (new_copies < 1) {
						System.out.println("copies should be greater than zero!!");
						System.out.print("enter new copies: ");
						new_copies = sc.nextInt();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				result = operations.updateBook(id, new_title, new_author, new_copies);
				System.out.println(result);
				break;
			}
			
			/*choice to add a customer*/
			case 4: {
				String name="", address="", result="";
				long contact=0;
				try {
					System.out.print("enter customer name: ");
					name = br.readLine();
					System.out.print("enter customer address: ");
					address = br.readLine(); 
					System.out.print("enter customer contact no: ");
					contact = sc.nextLong();
					result = operations.addCustomer(name, address, contact);
					System.out.println(result);
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;
			}
			
			/*choice to delete a customer*/
			case 5: {
				String result="";
				int id=0;
				System.out.print("enter customer id: ");
				id = sc.nextInt();
				result = operations.deleteCustomer(id);
				System.out.println(result);
				break;
			}
			
			/*choice to update a customer*/
			case 6: {
				String result="", name="", address="";
				int id=0;
				long contact=0;
				try {
					System.out.print("enter customer id: ");
					id = sc.nextInt();
					System.out.print("enter customer name: ");
					name = br.readLine();
					System.out.print("enter customer address: ");
					address = br.readLine();
					System.out.print("enter customer contact");
					contact = sc.nextLong();
					result = operations.updateCustomer(id, name, address, contact);
					System.out.println(result);
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;
			}
			
			/* choice to search a book */
			case 7: {
				System.out.print("enter book id: ");
				int id = sc.nextInt();
				String result = operations.searchBook(id);
				System.out.println(result);
				break;
			}

			/* choice to lend a book */
			case 8: {
				String result = null;
				int cust_id, book_id;
				System.out.print("enter customer id: ");
				cust_id = sc.nextInt();
				System.out.print("enter book id: ");
				book_id = sc.nextInt();
				result = operations.lendBook(cust_id, book_id);
				System.out.println(result);
				break;
			}

			/* choice to return a book */
			case 9: {
				String result = null;
				int cust_id, book_id;
				System.out.print("enter customer id: ");
				cust_id = sc.nextInt();
				System.out.print("enter book id: ");
				book_id = sc.nextInt();
				result = operations.returnBook(cust_id, book_id);
				System.out.println(result);
				break;
			}

			/* choice to list all books */
			case 10: {
				List<Book> result = null;
				result = operations.listAllBooks();
				if (result.size() > 0) {
					for (Book b : result) {
						System.out.println(b.toString());
					}
				} else
					System.out.println("No book found!!");
				break;
			}

			/* choice to list all customers */
			case 11: {
				List<Customer> result = null;
				result = operations.listAllCustomers();
				if (result.size() > 0) {
					for (Customer c : result) {
						System.out.println(c.toString());
					}
				} else
					System.out.println("No customer found!!");
				break;
			}
			
			/* choice to list all transactions */
			case 12: {
				List<Transactions> result = null;
				result = operations.listAllTransactions();
				if (result.size() > 0) {
					for (Transactions ts : result) {
						System.out.println(ts.toString());
					}
				} else {
					System.out.println("No transaction found!");
				}
				break;
			}

			/* choice to exit */
			case 13: {
				looping = false;
				break;
			}
			default: {
				System.out.println("Invalid entry..");
			}
			}
		} while (looping);
		
		operations.close();
		sc.close();
	}
}
