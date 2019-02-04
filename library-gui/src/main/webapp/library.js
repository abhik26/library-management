var app = angular.module("myapp", []);
app.controller('mycontrol', function($scope, $window, $http, $document) {
	
	listAllBooks();
	listAllCustomers();
	listAllTransactions();
	book_size=0; cust_size=0;
	$scope.showBookList = function() {
		$scope.div_booklist = !$scope.div_booklist;
		$scope.div_customerlist = false;
		$scope.div_lend_return_book = false;
	}

	$scope.showCustomerList = function() {
		$scope.div_booklist = false;
		$scope.div_customerlist = !$scope.div_customerlist;
		$scope.div_lend_return_book = false;
	}

	$scope.showTransactions = function() {
		$scope.div_booklist = false;
		$scope.div_customerlist = false;
		$scope.div_lend_return_book = !$scope.div_lend_return_book;
	}

	function listAllBooks() {
		$http({
			url: "webapi/library-services/books",
			method: "GET",
		}).then(function(response) {
			$scope.books = response.data;
		});
	}

	$scope.addBook = function() {
		if ($scope.title == "" || $scope.title == null) {
			document.getElementById("title").style.border="1px solid rgb(255 87 4)";
			document.getElementById("title").style.boxShadow="0 0 5px rgb(255 87 4)";
			$window.alert("enter book title!");
		} else if ($scope.author == "" || $scope.author == null) {
			document.getElementById("author").style.border="1px solid rgb(255 87 4)";
			document.getElementById("author").style.boxShadow="0 0 5px rgb(255 87 4)";
			$window.alert("enter book author!");
		} else if ($scope.copies == null || $scope.copies < 1) {
			document.getElementById("copies").style.border="1px solid rgb(255 87 4)";
			document.getElementById("copies").style.boxShadow="0 0 5px rgb(255 87 4)";
			$window.alert("enter book copies!");
		} else {
			var book = {id:0 ,title:$scope.title, author:$scope.author, copies: $scope.copies};
			/*$scope.books.push(book);*/
			var req = {
					url: "webapi/library-services/books",
					method: "POST",
					headers: {
						'content-type': 'application/json'
					},
					data: book
			};
			$http(req).then(function(response){
				$window.alert(response.data);
			});
			listAllBooks();
			//listAllBooks();
			$scope.title ="";
			$scope.author="";
			$scope.copies=null;
			document.getElementById("title").style.border="1px solid";
			document.getElementById("title").style.boxShadow="0 0 5px";
			document.getElementById("author").style.border="1px solid";
			document.getElementById("author").style.boxShadow="0 0 5px";
			document.getElementById("copies").style.border="1px solid";
			document.getElementById("copies").style.boxShadow="0 0 5px";
		}
	}

	$scope.deleteBook = function(book, index) {
		book = {id: book.id, title: book.title, author: book.author, copies: book.copies};
		//console.log(book);
		if (index >= 0) {
			$http({
				url: "webapi/library-services/books",
				method: "DELETE",
				headers: {
					'Content-Type': 'application/json'
				},
				data: book
			}).then(function(response) {
				$window.alert(response.data);
			});
			listAllBooks();
			//listAllBooks();
		} else {
			$window.alert("NO such book found!");
		}
	}

	$scope.updateBook = function(book, index) {
		title = "title" + index;
		author = "author" + index;
		copies = "copies" + index;
		if (!$scope.inputbook[index]) {
			$scope.inputbook[index] = !$scope.inputbook[index];
			$scope.update_book[index] = 'submit';
		} else {
			if (book.title == null || book.title == "") {
				$window.alert("enter book title!");
				document.getElementById(title).style.border="1px solid rgb(255 87 4)";
				document.getElementById(title).style.boxShadow="0 0 5px rgb(255 87 4)";
			} else if (book.author == null || book.author == "") {
				$window.alert("enter book author!");
				document.getElementById(author).style.border="1px solid rgb(255 87 4)";
				document.getElementById(author).style.boxShadow="0 0 5px rgb(255 87 4)";
			} else if (book.copies == null || book.copies < 1) {
				$window.alert("book copies should be greater than '0'!");
				document.getElementById(copies).style.border="1px solid rgb(255 87 4)";
				document.getElementById(copies).style.boxShadow="0 0 5px rgb(255 87 4)";
			} else {
				book = {id: book.id, title: book.title, author: book.author, copies: book.copies};
				$http({
					url: "webapi/library-services/books",
					method: "PUT",
					headers: {
						'Content-Type': 'application/json'
					},
					data: book
				}).then(function(response) {
					$window.alert(response.data);
				});
				listAllBooks();
				//listAllBooks();
				$scope.inputbook[index] = !$scope.inputbook[index];
				$scope.update_book[index] = 'update book';
				document.getElementById(title).style.border="1px solid";
				document.getElementById(title).style.boxShadow="0 0 5px";
				document.getElementById(author).style.border="1px solid";
				document.getElementById(author).style.boxShadow="0 0 5px";
				document.getElementById(copies).style.border="1px solid";
				document.getElementById(copies).style.boxShadow="0 0 5px";
			}
		}
	}

	function listAllCustomers() {
		$http({
			url: "webapi/library-services/customers",
			method: "GET"
		}).then(function (response){
			$scope.customers = response.data;
		})
	}

	$scope.addCustomer = function() {
		if ($scope.cust_name == "" || $scope.cust_name == null) {
			$window.alert("enter customer name!");
			document.getElementById("cust_name").style.border="1px solid rgb(255 87 4)";
			document.getElementById("cust_name").style.boxShadow="0 0 5px rgb(255 87 4)";
		} else if ($scope.cust_address == null || $scope.cust_address == "") {
			$window.alert("enter customer address!");
			document.getElementById("cust_address").style.border="1px solid rgb(255 87 4)";
			document.getElementById("cust_address").style.boxShadow="0 0 5px rgb(255 87 4)";
		} else if ($scope.cust_contact == null || $scope.cust_contact < 1000000000) {
			$window.alert("enter valid contact no. of 10 digit!");
			document.getElementById("cust_contact").style.border="1px solid rgb(255 87 4)";
			document.getElementById("cust_contact").style.boxShadow="0 0 5px rgb(255 87 4)";
		} else {
			customer = {id:0, name:$scope.cust_name, address: $scope.cust_address, contact:$scope.cust_contact};
			$http({
				url: "webapi/library-services/customers",
				method: "POST",
				headers: {
					'content-type': 'application/json'
				},
				data: customer
			}).then(function(response){
				$window.alert(response.data);
			});
			//$scope.customers.push(customer);
			listAllCustomers();
			//listAllCustomers();
			$scope.cust_name = "";
			$scope.cust_address="";
			$scope.cust_contact=null;
			document.getElementById("cust_name").style.border="1px solid";
			document.getElementById("cust_name").style.boxShadow="0 0 5px";
			document.getElementById("cust_contact").style.border="1px solid";
			document.getElementById("cust_contact").style.boxShadow="0 0 5px";
			document.getElementById("cust_address").style.border="1px solid";
			document.getElementById("cust_address").style.boxShadow="0 0 5px";
		}
	}

	$scope.deleteCustomer = function(customer, index) {
		if (index < 0) {
			$window.alert("No such customer found!");
		} else {
			customer = {id: customer.id, name: customer.name, address: customer.address, contact: customer.contact};
			/*customer = $scope.customers[index];
					$scope.customers.splice(index, 1);*/
			$http({
				url: "webapi/library-services/customers",
				method: "DELETE",
				headers: {
					'content-type': 'application/json'
				},
				data: customer
			}).then(function(response) {
				$window.alert(response.data);
			});
			listAllCustomers();
			//listAllCustomers();
		}	
	}

	$scope.updateCustomer = function(customer, index) {
		name = "name" + index;
		address = "address" + index;
		contact = "contact" + index;
		if (!$scope.inputcustomer[index]) {
			$scope.inputcustomer[index] = !$scope.inputcustomer[index];
			$scope.update_customer[index] = 'submit';
		} else {
			if (customer.name == "" || customer.name == null) {
				$window.alert("enter customer name!");
				document.getElementById(name).style.border="1px solid rgb(255 87 4)";
				document.getElementById(name).style.boxShadow="0 0 5px rgb(255 87 4)";
			} else if (customer.address == "" || customer.address == null) {
				$window.alert("enter customer address!");
				document.getElementById(address).style.border="1px solid rgb(255 87 4)";
				document.getElementById(address).style.boxShadow="0 0 5px rgb(255 87 4)";
			} else if (customer.contact == null || customer.contact < 1000000000) {
				$window.alert("enter valid contact no. of 10 digit!");
				document.getElementById(contact).style.border="1px solid rgb(255 87 4)";
				document.getElementById(contact).style.boxShadow="0 0 5px rgb(255 87 4)";
			} else {
				customer = {id: customer.id, name: customer.name, address: customer.address, contact: customer.contact};
				$http({
					url: "webapi/library-services/customers",
					method: "PUT",
					headers: {
						'content-type': 'application/json'
					},
					data: customer
				}).then(function(response) {
					$window.alert(response.data);
				});
				listAllCustomers();
				//listAllCustomers();
				$scope.inputcustomer[index] = !$scope.inputcustomer[index];
				$scope.update_customer[index] = 'update customer';
				document.getElementById(name).style.border="1px solid";
				document.getElementById(name).style.boxShadow="0 0 5px";
				document.getElementById(address).style.border="1px solid";
				document.getElementById(address).style.boxShadow="0 0 5px";
				document.getElementById(contact).style.border="1px solid";
				document.getElementById(contact).style.boxShadow="0 0 5px";
			}
		}
	}

	function listAllTransactions() {
		$http({
			url: "webapi/library-services/transactions",
			method: "GET"
		}).then(function (response){
			$scope.transactions = response.data;
		});
		
	}

	$scope.lendBook = function() {
		if ($scope.cust_id == undefined || $scope.cust_id == null) {
			$window.alert("enter customer id!");
			document.getElementById("cust_id").style.border="1px solid rgb(255 87 4)";
			document.getElementById("cust_id").style.boxShadow="0 0 5px rgb(255 87 4)";
		} else if ($scope.book_id == null || $scope.book_id == undefined) {
			$window.alert("enter book id!");
			document.getElementById("book_id").style.border="1px solid rgb(255 87 4)";
			document.getElementById("book_id").style.boxShadow="0 0 5px rgb(255 87 4)";
		} else {
			var transaction_index = -1;
			for (i=0; i<$scope.transactions.length; i++) {
				if ($scope.transactions[i].custId == $scope.cust_id && $scope.transactions[i].bookId == $scope.book_id && ($scope.transactions[i].returnDate == undefined || $scope.transactions[i].returnDate == null)) {
					transaction_index = i;
				}
			}
			var copiesIssued=0;
			for (i=0; i<$scope.transactions.length; i++) {
				if ($scope.transactions[i].bookId == $scope.book_id && ($scope.transactions[i].returnDate == null || $scope.transactions[i].returnDate == undefined)) {
					copiesIssued++;
				}
			}
			if (transaction_index >= 0) {
				$window.alert("the book is already issued to the customer!");	
			} else if (copies <= copiesIssued) {
				$window.alert("no copies of the book available!");
			} else {
				/*var size = $scope.transactions.length;*/
				transaction = {id: 0, custId: $scope.cust_id, bookId: $scope.book_id, issueDate: null, returnDate: null};
				/*$scope.transactions.push(transaction);
							$scope.books[book_index].copies--;*/
				$http({
					url: "webapi/library-services/transactions",
					method: "POST",
					headers: {
						'content-type': 'application/json'
					},
					data: transaction
				}).then(function(response){
					$window.alert(response.data);
				});
				listAllTransactions();
				//listAllTransactions();
				$scope.cust_id = null;
				$scope.book_id = null;
				$scope.issue_date = null;
				document.getElementById("cust_id").style.border="1px solid";
				document.getElementById("cust_id").style.boxShadow="0 0 5px";
				document.getElementById("book_id").style.border="1px solid";
				document.getElementById("book_id").style.boxShadow="0 0 5px";
			}
		}
	}

	$scope.returnBook = function(transaction, index) {
		transaction = {id: $scope.transactions[index].id, custId: $scope.transactions[index].custId, bookId: $scope.transactions[index].bookId, issueDate: $scope.transactions[index].issueDate, returnDate: $scope.transactions[index].returnDate};
		$http({
			url: "webapi/library-services/transactions",
			method: "PUT",
			headers: {
				'content-type': 'application/json'
			},
			data: transaction
		}).then(function(response){
			$window.alert(response.data);
		});
		listAllTransactions();
		//listAllTransactions();
		$scope.returninput[index] = false;
	}
});