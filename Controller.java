import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import java.util.Vector;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Program Name: Controller.java
 * Purpose: Contains Controller and Model, Constructs the GUI and Makes Connections along with Query Statements
 * Coder: Sandeep Singh (Sandy) - 0869722  & Andrea Darceuil - 0804065 -  Section 01
 * Date: Aug. 1, 2019
 */

public class Controller extends Application implements EventHandler<ActionEvent>
{
	//GLOBAL VARIABLES
  public Button allBooksBtn, allBooksOutLoanBtn, allBorBtn, subjectFindBtn, authorFindBtn, exitBtn, 
                aboutBtn, updateUserBtn, newUserBtn, updateBtn, cancelBtn, addBookBtn, returnBookBtn, returnBtn, addLoanBtn, loanBtn
                , lastBtn, addAuthorBtn;
  public TextField subjectTxt, authorTxt, updateUserTxt,newUserFirstNameTxt, newUserLastNameTxt, firstnametxt, lastNametxt, emailTxt
                   ,newEmailTxt, ntitleTxt,nISBNTxt, nEdNumTxt, nSubTxt, authFirstNameTxt, authLastNameTxt, userEmailTxt, addUserEmailTxt,
                   commentTxt, dueDateTxt, userIdTxt;
  public Label operationsLbl, welcomeLabel, findSubjectLbl, findauthorLbl, updateUserLbl, newUserLbl,
               firstNameLbl, lastNameLbl,emailLbl, newBookLbl, ntitle, nISBN, nEdNum, nSubLbl, newAuthorLbl,
               authFirstName, authLastName, loanedBookLbl, returnDateLbl, returnDate, selectBookLbl, addBookLoanLbl, dateOut;
  public int userId = 0;
  public ArrayList<String>authorFirstNames=new ArrayList<String>();
  public ComboBox loanedBooks;
  public ComboBox loanDuration;
  public final ObservableList loanedBooksCB = FXCollections.observableArrayList();
  public ArrayList<String>authorLastNames=new ArrayList<String>();
  ArrayList<String[][]>authorNames=new ArrayList<String[][]>();
  
  //DIALOG BOXES
  Dialog<Pair<String, String>>  dialog;
  Dialog<Pair<String, String>>  loanDialog;
  Dialog<Pair<String, String>>  confirmLoanDialog;
  Dialog<Pair<String, String>>  addLoandialog;
  List<Integer> bookIds;
  
  //FLAGS
  boolean loanQuery = false;
  boolean returnQuery = false;
  boolean addLoanQuery = false;
  boolean runLoanQuery  = false;
  boolean lastQuery = false;
	//Class Global Scope
		private View view = null;
		String email = "", firstName = "", lastName= "", existingFirstName = "", existingLastName = "";
		int userID = 0;
		boolean userQuery = false;
		boolean updateQuery = false;
		LocalDate date;
		LocalDate dueDate;
	//boolean bookUpdate=false;
			boolean bookAdd=false;
			String title, isbn, edNum, subTxt, authorFirstName,authorLastName, bookString, authorString, bookAuthorString;
			
	public void init()
	{
		
	}
	
	//TO FULFILL THE CONTRACT, we must over-ride the start() method. This method
	// is kind of like our constructor in a Swing app.
	public void start(Stage myStage)//the JVM will provide the Stage object for us
	{
		
		
		//set the title bar text
		myStage.setTitle("Library");
		 
		//NODES
		BorderPane rootNode = new BorderPane();
		BorderPane userNode = new BorderPane();
		BorderPane bookNode = new BorderPane();
		BorderPane loanNode = new BorderPane();

		//TABS
		TabPane tabPane = new TabPane();
		Tab welcomeTab = new Tab("HOME");
		Tab borrowerTab = new Tab("USER");
		Tab addNewBookTab = new Tab("BOOKS");
		Tab loanTab = new Tab("LOAN");
		
	  //MAKE A SCENE!
		Scene myScene = new Scene(tabPane, 430, 400);//sizes it 
		
		
		//HOME-TAB - TOP PANE
		BorderPane topPane = new BorderPane();
		welcomeLabel = new Label("Welcome to our Library!");
		BorderPane.setAlignment(welcomeLabel, Pos.CENTER);
		BorderPane.setMargin(welcomeLabel, new Insets(12,12,12,12));
		
		welcomeLabel.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD, 28));
		welcomeLabel.setStyle("-fx-border-color: black;");
		welcomeLabel.setTextFill(Color.SKYBLUE);
		topPane.setTop(welcomeLabel);
		
		
		//HOME-TAB - MIDDLE PANE
		GridPane middlePane = new GridPane();
		middlePane.setAlignment(Pos.CENTER);
		middlePane.setHgap(10);
		middlePane.setVgap(10);
		operationsLbl = new Label("Operations: ");
		operationsLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		allBooksBtn = new Button("Show all Books");
		allBooksBtn.setOnAction(this);
		allBooksBtn.setMinWidth(middlePane.getPrefWidth());
		allBooksOutLoanBtn = new Button("Show all Out-Loaned Books");
		allBooksOutLoanBtn.setOnAction(this);
		allBorBtn = new Button("Show all Borrowers");
		allBorBtn.setOnAction(this);
		allBooksBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		allBooksOutLoanBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		allBorBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		middlePane.add(operationsLbl,1,1,1,1);
		middlePane.add(allBooksBtn,1,3,1,1);
		middlePane.add(allBooksOutLoanBtn,1,4,1,1);
		middlePane.add(allBorBtn,1,5,1,1);
		
    //HOME-TAB - BOTTOM PANE
		GridPane bottomPane = new GridPane();
		bottomPane.setAlignment(Pos.CENTER);
		bottomPane.setHgap(20);
		bottomPane.setVgap(10);
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		findSubjectLbl = new Label("Find Books By Subject: ");
		findSubjectLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		subjectTxt = new TextField("Enter Subject Here");
		subjectTxt.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent mouseEvent) {
	    	subjectTxt.setText("");
	    	}});
		subjectFindBtn = new Button("Search by Subject");		
		subjectFindBtn.setOnAction(this);
		findauthorLbl = new Label("Find Books By Author: ");
		findauthorLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		authorTxt = new TextField("Enter Author's Name");
		authorTxt.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent mouseEvent) {
	    	authorTxt.setText("");}});
		authorFindBtn = new Button("Search by Author");
		authorFindBtn.setOnAction(this);
		exitBtn = new Button("Click to Exit");
		aboutBtn = new Button("Know about Creators");
		aboutBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent mouseEvent) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("About Creators");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Made by Sandy and Andrea!");	    	alert.showAndWait(); }
	});
		subjectFindBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		authorFindBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		exitBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		exitBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent mouseEvent) {
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setTitle("Exit");
	    	alert.setHeaderText("Please Confirm");
	    	alert.setContentText("Do you want to close the application?");
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == ButtonType.OK){
	    		myStage.close();
	    	} else {
	    	    alert.close();
	    	}}});
		aboutBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		bottomPane.add(findSubjectLbl, 0,0,2,1);
		bottomPane.add(subjectTxt, 0,1,2,1);
		bottomPane.add(subjectFindBtn, 0,2,2,1);
		bottomPane.add(findauthorLbl, 2,0,2,1);
		bottomPane.add(authorTxt, 2,1,2,1);
		bottomPane.add(authorFindBtn, 2,2,2,1);
		bottomPane.add(exitBtn, 1,3,1,1);
		bottomPane.add(aboutBtn,2,3,1,1);
		
		
		
		rootNode.setTop(topPane);
		rootNode.setCenter(middlePane);
		rootNode.setBottom(bottomPane);
		
		//USER TAB
		GridPane topPane2 = new GridPane();
		//topPane2.setAlignment(Pos.CENTER);
		topPane2.setHgap(20);
		topPane2.setVgap(10);
		updateUserLbl = new Label("In order to update existing user enter your registered Email: ");
		updateUserTxt = new TextField();
		updateUserBtn = new Button("Update Existing User");
		updateUserBtn.setOnAction(this);
		topPane2.add(updateUserLbl,1,1,1,1);
		topPane2.add(updateUserTxt,1,2,1,1);
		topPane2.add(updateUserBtn,1,3,1,1);
		
		
		GridPane middlePane2 = new GridPane();
		middlePane2.setAlignment(Pos.CENTER_LEFT);
		middlePane2.setHgap(10);
		middlePane2.setVgap(10);
		newUserLbl = new Label("Add New User:");	
		firstNameLbl = new Label("First Name: ");
		lastNameLbl = new Label("Last Name: ");
		emailLbl = new Label("Email: ");
		newUserFirstNameTxt = new TextField();
		newUserLastNameTxt = new TextField();
		newEmailTxt = new TextField();
		newUserBtn = new Button("Add New User");
		newUserBtn.setOnAction(this);
		updateUserBtn.setOnAction(this);
		middlePane2.add(newUserLbl,2,1,1,1);
		middlePane2.add(firstNameLbl,2,2,1,1);
		middlePane2.add(newUserFirstNameTxt,3,2,1,1);
		middlePane2.add(lastNameLbl,2,3,1,1);
		middlePane2.add(newUserLastNameTxt,3,3,1,1);
		middlePane2.add(emailLbl,2,4,1,1);
		middlePane2.add(newEmailTxt,3,4,1,1);
		middlePane2.add(newUserBtn,2,5,1,1);
		
		
		userNode.setTop(topPane2);
		userNode.setCenter(middlePane2);
		
		//ADD BOOK TAB
		GridPane bookPane = new GridPane();
		bookPane.setAlignment(Pos.CENTER_LEFT);
		bookPane.setHgap(10);
		bookPane.setVgap(10);
		newBookLbl = new Label("Add new Book: ");
		ntitle = new Label("Title: ");
		nISBN = new Label("ISBN: ");
		nEdNum = new Label("Edition No.: ");
		nSubLbl = new Label("Subject: ");
		newAuthorLbl = new Label("Add multiple authors by clicking \'Add Author\'");
		authFirstName = new Label("Author's First Name: ");
		authLastName = new Label("Author's Last Name: ");
		ntitleTxt = new TextField();
		nISBNTxt = new TextField();
		nEdNumTxt = new TextField();
		nSubTxt = new TextField();
		authFirstNameTxt = new TextField();
		authLastNameTxt = new TextField();
		addBookBtn = new Button("Add Book");
		addBookBtn.setOnAction(this);
		addAuthorBtn=new Button("Add Author");
		addAuthorBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		    	int ISBNnum=0;
		    	try {
		    		//setting up string array to take first and last name
		    		String[][] names =new String [1][2];
		    		//data validation for empty fields and ISBN length and type(numeric)
		    		if (!ntitleTxt.getText().equals("") || !nISBNTxt.getText().equals("") || !nEdNumTxt.getText().equals("") || !nSubTxt.getText().equals("")) {
		    				if(nISBNTxt.getText().length()==13 && parseIntChecker(nISBNTxt.getText())) {
		    									//validation for empty author first and last name fields
					    						if(!authFirstNameTxt.getText().equals("")|| !authLastNameTxt.getText().equals(""))
					    						{
					    							authorFirstName =authFirstNameTxt.getText();
					    							authorLastName =authLastNameTxt.getText();
					    							//Vector<String>[][] names =new Vector<String>[1][2];
					    							
					    							names[0][0]=authorFirstName;
					    							names[0][1]=authorLastName;
					    							
					    							authorNames.add(names);
	    						
					    							authFirstNameTxt.setText("");
					    							authLastNameTxt.setText("");
					    						}
					    						else {
					    							Alert alert = new Alert(AlertType.INFORMATION);
					    				    	alert.setTitle("Result");
					    				    	alert.setHeaderText(null);
					    				    	alert.setContentText("Please input a value for both Author first and last name");
					    				    	alert.showAndWait();
					    						}
		    						}
		    				else if (!parseIntChecker(nISBNTxt.getText())){
		    						Alert alert = new Alert(AlertType.INFORMATION);
		    				    	alert.setTitle("Result");
		    				    	alert.setHeaderText(null);
		    				    	alert.setContentText("ISBN number must be numeric!!");
		    				    	alert.showAndWait();
		    					}
		    				
		    				else {
		    					Alert alert = new Alert(AlertType.INFORMATION);
	    				    	alert.setTitle("Result");
	    				    	alert.setHeaderText(null);
	    				    	alert.setContentText("ISBN number length is not correct");
	    				    	alert.showAndWait();
		    				}
		    			
		    		}
		    		else {
		    			Alert alert = new Alert(AlertType.INFORMATION);
		    	    	alert.setTitle("Error");
		    	    	alert.setHeaderText(null);
		    	    	alert.setContentText("Book information must be filled out");
		    	    	alert.showAndWait();
		    		}
				}
				catch(Exception ex) {}
			
		}});	
		bookPane.add(newBookLbl,1,1,1,1);
		bookPane.add(ntitle,1,2,1,1);
		bookPane.add(ntitleTxt,2,2,1,1);
		bookPane.add(nISBN,1,3,1,1);
		bookPane.add(nISBNTxt,2,3,1,1);
		bookPane.add(nEdNum,1,4,1,1);
		bookPane.add(nEdNumTxt,2,4,1,1);
		bookPane.add(nSubLbl,1,5,1,1);
		bookPane.add(nSubTxt,2,5,1,1);
		bookPane.add(newAuthorLbl,1,6,1,1);
		bookPane.add(authFirstName,1,7,1,1);
		bookPane.add(authFirstNameTxt,2,7,1,1);
		bookPane.add(authLastName,1,8,1,1);
		bookPane.add(authLastNameTxt,2,8,1,1);
		bookPane.add(addAuthorBtn, 2,9, 1, 1);
		bookPane.add(addBookBtn,1,10,1,1);		
		
		bookNode.setTop(bookPane);
		
		
		GridPane loanBottomPane = new GridPane();
		loanBottomPane.setAlignment(Pos.CENTER_LEFT);
		loanBottomPane.setHgap(10);
		loanBottomPane.setVgap(10);
		loanedBookLbl = new Label("Enter your Email Id, in order to start return process: ");
		userEmailTxt = new TextField();
		returnBookBtn = new Button("Start Return");
		returnBookBtn.setOnAction(this);
		loanBottomPane.add(loanedBookLbl, 2, 3,1,1);
		loanBottomPane.add(userEmailTxt, 2, 4,1,1);
		loanBottomPane.add(returnBookBtn, 2, 5, 5,5);
		loanBottomPane.setPadding(new Insets(10, 10, 10, 10));
		loanNode.setBottom(loanBottomPane);
		
		
		GridPane loanTopPane = new GridPane();
		loanTopPane.setAlignment(Pos.CENTER_LEFT);
		loanTopPane.setHgap(10);
		loanTopPane.setVgap(10);
		addBookLoanLbl = new Label("Click below to start the LOAN Process: ");
		//addUserEmailTxt = new TextField();
		addLoanBtn = new Button("Add Loan");
//		addLoanBtn.setMaxSize(100, 200);
//
//		addLoanBtn.setStyle("-fx-background-color: linear-gradient(#ff5400, #be1d00);-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
//    -fx-background-radius: 30;
//    -fx-background-insets: 0;
//    -fx-text-fill: white;
		addLoanBtn.setOnAction(this);
		loanTopPane.add(addBookLoanLbl, 2, 3,1,1);
		//loanTopPane.add(addUserEmailTxt, 2, 4,1,1);
		loanTopPane.add(addLoanBtn, 2, 5, 1,1);
		loanTopPane.setPadding(new Insets(10, 10, 10, 10));
		loanNode.setTop(loanTopPane);
		
		//Setting Contents
		welcomeTab.setContent(rootNode);
		borrowerTab.setContent(userNode);
		addNewBookTab.setContent(bookNode);
		loanTab.setContent(loanNode);
		
		//Adding Tabs
		tabPane.getTabs().add(welcomeTab);
		tabPane.getTabs().add(borrowerTab);
		tabPane.getTabs().add(addNewBookTab);
		tabPane.getTabs().add(loanTab);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		//put the scene object up on the stage
		myStage.setScene(myScene);
		
		//LAST LINE...RAISE THE CURTAIN!
		myStage.show();	
		
	}//end start
	
	//over-ride the stop() method if necessary
	public void stop()
	{
		//stub
		
	}//end stop
	
	public void fillComboBox()
	{
		String query = "select book.Title " + 
				"from book " + 
				"INNER JOIN book_Loan ON book.BookID = book_Loan.Book_BookID ";
	}
	
	
	public void handle(ActionEvent e)
	{

		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRslt = null; 
		PreparedStatement myPrepStmt = null, bookPrepStmt=null, authorPrepStmt=null, bookAthrPrepStmt=null;		String stmt = null;
		
		loanQuery = false;
		addLoanQuery = false;
		runLoanQuery = false;
		userQuery = false;
		bookAdd = false;
		updateQuery = false;
		bookAdd=false;
		returnQuery = false;
		lastQuery = false;
		
		//SHOW ALL BOOKS
		if(e.getSource() == allBooksBtn)
		{
			updateQuery = false;
			bookAdd=false;
			returnQuery = false;
			lastQuery = false;
			stmt = "SELECT *"
					+ " FROM book";
		}
		//ALL BOOKS OUT LOANED
		else if(e.getSource() == allBooksOutLoanBtn)
		{
			updateQuery = false;
			bookAdd=false;
			returnQuery = false;
			lastQuery = false;

			stmt = "select book.title, borrower.first_name, borrower.last_name, book_loan.Date_Due " + 
					"from book " + 
					"INNER JOIN book_loan ON book.BookID = book_loan.Book_BookID " +
					"INNER JOIN borrower ON book_loan.Borrower_Borrower_ID = borrower.Borrower_ID " +
					"WHERE book_loan.date_returned IS NULL";
		}
		//ALL BORROWERS
		else if(e.getSource() == allBorBtn)
		{
			updateQuery = false;
			bookAdd=false;
			lastQuery = false;

			
			returnQuery = false;

			stmt = "SELECT *"
					+ " FROM borrower";
		}
		//FIND BY SUBJECT
		else if(e.getSource() == subjectFindBtn)
		{
			updateQuery = false;
			returnQuery = false;
			lastQuery = false;

      if (subjectTxt.getText().equals("") || subjectTxt.getText().equals("Enter Subject Here"))
      {
      	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Result");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Subject can't be empty!");
	    	alert.showAndWait();
      }
      else
      {
			String sub = subjectTxt.getText();
			stmt = "SELECT *"
					+ " FROM book "
					+ "WHERE book.Subject = '" + sub + "'";
      }
		}
		//FIND BY AUTHOR
		else if(e.getSource() == authorFindBtn)
		{
			updateQuery = false;
			returnQuery = false;
			lastQuery = false;

			if (authorTxt.getText().equals("") || authorTxt.getText().equals("Enter Author's Name"))
      {
      	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Result");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Author's name can't be empty!");
	    	alert.showAndWait();
      }
			else {
			String author = authorTxt.getText();
			String [] authorNames = author.split(" ");
			if(authorNames.length < 2)
			{
				stmt = "select book.title, author.first_name, author.last_name " + 
						"from book " + 
						"INNER JOIN book_author ON book.BookID = book_author.Book_BookID " +
						"INNER JOIN author ON book_author.author_authorID = author.AuthorID" +
						" WHERE author.first_name = '" + authorNames[0] + "' OR author.last_name = '" + authorNames[0] + "'";
			}
			else if (authorNames.length == 2)
			{
				stmt = "select book.title, author.first_name, author.last_name " + 
						"from book " + 
						"INNER JOIN book_author ON book.BookID = book_author.Book_BookID " +
						"INNER JOIN author ON book_author.author_authorID = author.AuthorID" +
						" WHERE author.first_name = '" + authorNames[0] + "' AND author.last_name = '" + authorNames[1] + 
						"' OR  author.first_name = '" + authorNames[1] + "' AND author.last_name = '" + authorNames[0] + "'";
			}
		
			}
		}
		//UPDATE USER INFO
		else if(e.getSource() == updateUserBtn)
		{
			lastQuery = false;

			if(updateUserTxt.getText().equals(""))
			{
				Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Result");
	    	alert.setHeaderText(null);
	    	alert.setContentText("This Field can't be empty");
	    	alert.showAndWait();
			}
			else
			{
			
			email = updateUserTxt.getText();
			//updateUserTxt.setText("");
			stmt = "SELECT * FROM borrower WHERE Borrower_email = '" + email + "'"; 
			userQuery = true;
			bookAdd=false;
			}

		}
		//UPDATE THE INFO
		else if(e.getSource() == updateBtn)
		{
			lastQuery = false;

			if(updateUserTxt.getText().equals("") || emailTxt.getText().equals("") || firstnametxt.getText().equals("") || lastNametxt.getText().equals("") )
			{
				Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Result");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Please fill all the fields!!");
	    	alert.showAndWait();
			}
			else
			{
			
			userQuery = false;
			stmt = "UPDATE borrower " 
			      +"SET borrower_email = '" + emailTxt.getText() + "', first_name = '" + firstnametxt.getText() 
			      + "', last_name = '" + lastNametxt.getText() + "' WHERE borrower_Id = '" + userID + "'";
			updateQuery = true;
			bookAdd=false;
			}

		}
		//ADD NEW USER
		else if(e.getSource() == newUserBtn)
		{
			lastQuery = false;

			if(newUserFirstNameTxt.getText().equals("") || newUserLastNameTxt.getText().equals("") || newEmailTxt.getText().equals(""))
			{
				Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Result");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Please fill all the fields!!");
	    	alert.showAndWait();
			}
			else
			{
			userQuery = false;
			stmt = "INSERT INTO borrower " + "(first_name, last_name, borrower_email) " +
		         "VALUES ('" + newUserFirstNameTxt.getText() + "','" + newUserLastNameTxt.getText() + "','" + newEmailTxt.getText() + "')";
			updateQuery = true;
			bookAdd=false;
			}

		}
		//START THE RETURN PROCESS
		else if (e.getSource() == returnBookBtn)
		{
			lastQuery = false;

			
			email = userEmailTxt.getText();
			stmt = "select book.Title, book.bookID, borrower.borrower_ID " + 
					"from book " + 
					"INNER JOIN book_Loan ON book.BookID = book_Loan.Book_BookID " + 
					"INNER JOIN borrower ON book_loan.Borrower_Borrower_ID = Borrower.Borrower_ID " + 
					"WHERE Borrower.borrower_email = '" + email + "' && book_loan.date_returned IS NULL ";
			loanQuery = true;
			bookAdd=false;

		}
		//RETURN BOOK
		else if(e.getSource() == returnBtn)
		{
			lastQuery = false;
			loanQuery = false;
			
	    stmt = "SELECT * FROM BOOK";
			stmt = "UPDATE book_loan " + 
					"INNER JOIN book ON book_loan.Book_bookID = book.bookID " + 
					"SET  book_loan.Date_returned = '" + date.toString() +  "', book.available = 1 " + 
					"WHERE book_BookID = " + bookIds.get(loanedBooks.getSelectionModel().getSelectedIndex()) + 
					" && borrower_borrower_ID = "+ userId + " && date_returned IS NULL";
			returnQuery = true;
			
		}
		//START ADD LOAN PROCESS
		else if(e.getSource() == addLoanBtn)
		{
			lastQuery = false;
      loanQuery = false;
			stmt = "select book.Title, book.bookID from book\r\n" + 
					"WHERE book.available = 1";
			addLoanQuery = true;
		}
		//PROCESS THE LOAN
		else if(e.getSource() == loanBtn)
		{
			lastQuery = false;


			 if(parseIntChecker(userIdTxt.getText()))
	      {
				 date = LocalDate.now();
					dueDate = LocalDate.now();
					//Calendar c = Calendar.getInstance();
					if(loanDuration.getSelectionModel().getSelectedIndex() == 0)
					{
						
						dueDate = dueDate.plusDays(7);
					}
					else if(loanDuration.getSelectionModel().getSelectedIndex() == 1)
					{
						dueDate = dueDate.plusDays(14);

					}
					else if(loanDuration.getSelectionModel().getSelectedIndex() == 2)
					{
						dueDate = dueDate.plusDays(21);
					}

					stmt = "INSERT INTO book_loan " + 
							"(Book_BookID, Borrower_Borrower_ID, book_loan.Comment, " + 
							"Date_Out,Date_Due) " + 
							"VALUES(" + bookIds.get(loanedBooks.getSelectionModel().getSelectedIndex()) + " , " +  Integer.parseInt(userIdTxt.getText()) + ", \'" +  commentTxt.getText() +
							"\' , \'" + date.toString() + "\',\'" +  dueDate.toString() + "\')";
					runLoanQuery = true;
	      }
			 else
			 {
				 Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setTitle("Result");
		    	alert.setHeaderText(null);
		    	alert.setContentText("UserId is invalid!");
		    	alert.showAndWait();
			 }
			
		}
		//FINSIHES THE LOAN PROCESS
		else if(e.getSource() == lastBtn)
		{
			lastQuery = false;

			confirmLoanDialog.close();

			stmt = "UPDATE Book " + 
					"SET Available = 0 " + 
					"WHERE BookId = " + bookIds.get(loanedBooks.getSelectionModel().getSelectedIndex());
			lastQuery = true;
			runLoanQuery = false;
			
		}
		//ADD BOOK PROCESS
		else if(e.getSource()==addBookBtn)
		{
			lastQuery = false;
//Validation for book add to make sure all user fields are filled out
				 if (!ntitleTxt.getText().equals("") || !nISBNTxt.getText().equals("") || !nEdNumTxt.getText().equals("") || !nSubTxt.getText().equals("") && authorNames.size()!=0)
				 {
						    		
									title=ntitleTxt.getText();
									isbn = nISBNTxt.getText();
									edNum = nEdNumTxt.getText();
									subTxt = nSubTxt.getText();
						    		
									//TODO no field for book available
									bookString="INSERT INTO book "+ 
											"(Title, ISBN, Edition_Number, Subject, Available) "+
											"VALUES(?, ?, ?,?, 1) ";
						    		 authorString="INSERT INTO author "+ 
											"(Last_Name, First_Name) "+
											"VALUES(?, ?) ";
						    		 bookAuthorString = "INSERT INTO book_author "+ 
											"(Book_BookID, Author_AuthorID) "+
											"VALUES(?, ?) ";
									stmt="INSERT INTO book "+ 
											"(Title, ISBN, Edition_Number, Subject, Available) "+
											"VALUES(?, ?, ?,?, 1) ";
									
								
						try {
							myConn = DriverManager.getConnection(
					         "jdbc:mysql://localhost:3306/info3136_books", 
                   "root","root");		
							//Adding book and author as a transaction to avoid orphaned authors
							//procedure will return the primary keys from the book and author table which will 
							myConn.setAutoCommit(false);
							//creating prepared statement for book to allow for user entered entries
							bookPrepStmt=myConn.prepareStatement(bookString, Statement.RETURN_GENERATED_KEYS);
							bookPrepStmt.setString(1, title);//sets (i.e the first parameter)
							bookPrepStmt.setString(2, isbn);
							bookPrepStmt.setString(3, edNum);//(i.e the first parameter)
							bookPrepStmt.setString(4, subTxt);
							//clearing out titles
							ntitleTxt.setText("");
							nISBNTxt.setText("");
							nEdNumTxt.setText(""); 
							nSubTxt.setText(""); 
							//author prepared statement to allow for user entered values of first and last names and to 
							authorPrepStmt=myConn.prepareStatement(authorString, Statement.RETURN_GENERATED_KEYS);
							//prepared statement for the book author junction table
							bookAthrPrepStmt=myConn.prepareStatement(bookAuthorString);
							ArrayList<Integer>authorPrimaryKeys=new ArrayList<Integer>();
							ArrayList<PreparedStatement>authorPreparedStatements = new ArrayList<PreparedStatement>();
							ArrayList<PreparedStatement>bookAuthorPreparedStatements = new ArrayList<PreparedStatement>();
							int bookKey=0, authorKey=0;
							//getting Author names and preparing statements for each prepared statement generated
							
							
											try {
												//book update
												int returnedValue = bookPrepStmt.executeUpdate();
									    		
									    		ResultSet s = bookPrepStmt.getGeneratedKeys();
								    			if(s.next())
									    		 //getting primary key from the  book update
									    		 bookKey=s.getInt(1);
								    			bookAthrPrepStmt.setInt(1, bookKey);
													    			if (authorNames.size()>0)
																	{
																		for(int i =0; i<authorNames.size();i++)
																		{
																								try {
																								String[][] name=authorNames.get(i);
																								authorPrepStmt.setString(1, name[0][1]);
																								
																								authorPrepStmt.setString(2, name[0][0]);
																								
																								int returnedValue1= authorPrepStmt.executeUpdate();
																								ResultSet s1 = authorPrepStmt.getGeneratedKeys();
																				    			if(s1.next())
																					    		authorKey=s1.getInt(1);
																				    			bookAthrPrepStmt.setInt(2, authorKey);
																				    			int returnedValue2 = bookAthrPrepStmt.executeUpdate();
																								//authorPreparedStatements.add(authorPrepStmt);
																						
																								
																								}
																								
																								catch(Exception ex) {}
																		}
																	}
																	else 
																	{
																		Alert alert = new Alert(AlertType.INFORMATION);
																    	alert.setTitle("Result");
																    	alert.setHeaderText(null);
																    	alert.setContentText("Author fields cannot be empty!");
																    	alert.showAndWait();
																	}
								    			
											}
											catch(Exception ex) {
												myConn.rollback();	
											}
											bookAdd=true;
											myConn.commit();
						}
						catch(Exception ev) {
						
						}
					}
				  else {
				    			    		
				    			Alert alert = new Alert(AlertType.INFORMATION);
						    	alert.setTitle("Result");
						    	alert.setHeaderText(null);
						    	alert.setContentText("Some fields not properly filled out");
						    	alert.showAndWait();
				    		}
}
		
	
		
		try
		{
		//Create Connection
		myConn = DriverManager.getConnection(
					         "jdbc:mysql://localhost:3306/info3136_books", 
					                       "root","root");		
		
		
		//Create Prepared Statement
		myPrepStmt = myConn.prepareStatement(stmt);
		

		

		if(updateQuery == true && userQuery == false)
		{
			
			myPrepStmt.executeUpdate();
			//dialog.close();
			Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Result");
    	alert.setHeaderText(null);
    	alert.setContentText("User Updated Successfully!!");
    	alert.showAndWait();
    	newUserFirstNameTxt.setText("");
    	newUserLastNameTxt.setText("");
    	newEmailTxt.setText("");
    	//updateQuery = false;
    	
		}
		else if(returnQuery == true && loanQuery == false)
		{
			myPrepStmt.executeUpdate();
			//dialog.close();
			Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Result");
    	alert.setHeaderText(null);
    	alert.setContentText("User Updated Successfully!!");
    	alert.showAndWait();
    	loanQuery = false;
		}
		else if(runLoanQuery == true)
		{
			myPrepStmt.executeUpdate();
		  // Create the custom dialog.
			confirmLoanDialog = new Dialog<>();
			confirmLoanDialog.setTitle("Loan Added: ");
			confirmLoanDialog.setHeaderText("Confirm the loan: ");
			confirmLoanDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
			confirmLoanDialog.close(); 
		  Label lbl = new Label("Click CONFIRM to finish off the loan process: ");
		  lastBtn = new Button("Confirm");
		  GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			lastBtn.requestFocus();
			grid.add(lbl, 1, 1);
			grid.add(lastBtn, 1, 2);
			confirmLoanDialog.getDialogPane().setContent(grid);

		  lastBtn.setOnAction(this);
		  confirmLoanDialog.showAndWait();
    	loanQuery = false;
    	addLoanQuery = false;
		}
		else if (lastQuery == true)
		{
			myPrepStmt.executeUpdate();
			confirmLoanDialog.close();
			addLoanQuery = false;
			
		}
		else if(bookAdd==true) {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Result");
	    	alert.setHeaderText(null);
	    	alert.setContentText("Book Added Successfully!!");
	    	alert.showAndWait();
	    	}
		else
		{
		//Execute the statement
		myRslt = myPrepStmt.executeQuery();
		}
		
		
		
		
		if(userQuery == false && updateQuery == false && loanQuery == false && returnQuery == false && addLoanQuery == false && 
				lastQuery == false && bookAdd==false)
		{
    //call the method resultSetToTableModel() in DbUtils class which accepts the result
		// of the query and returns a model
		TableModel model = DbUtils.resultSetToTableModel(myRslt);
		//Create the actual view
			view = new View(model);
		}
		else if (userQuery == true)
		{
		  // Create the custom dialog.
			dialog = new Dialog<>();
			dialog.setTitle("Update Info: ");
			dialog.setHeaderText("Please Update your Information: ");
			
		  
		  updateBtn = new Button("Update");
		  dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
		  dialog.close(); 
//		  cancelBtn = new Button("Cancel");
//		  cancelBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//		    @Override
//		    public void handle(MouseEvent mouseEvent) {
//		    	dialog.close();}});
		  
		  
		  // Create the username and password labels and fields.
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			myRslt.next();
			firstnametxt = new TextField(myRslt.getString("First_Name"));			
			lastNametxt = new TextField(myRslt.getString("Last_Name"));
			emailTxt = new TextField(myRslt.getString("Borrower_email"));
      userID = Integer.parseInt(myRslt.getString("Borrower_ID"));
      updateBtn.setOnAction(this);
			grid.add(new Label("First Name:"), 0, 0);
			grid.add(firstnametxt, 1, 0);
			grid.add(new Label("Last Name:"), 0, 1);
			grid.add(lastNametxt, 1, 1);
			grid.add(new Label("Email:"), 0, 2);
			grid.add(emailTxt, 1, 2);
			updateBtn.setAlignment(Pos.CENTER_RIGHT);
			grid.add(updateBtn, 1,4);
			
			
			dialog.getDialogPane().setContent(grid);
			
			dialog.showAndWait();

		}
		//ADD LOAN WIZARD
		else if(loanQuery == true)
		{
			
		  // Create the custom dialog.
			loanDialog = new Dialog<>();
			loanDialog.setTitle("Return Book Wizard: ");
			loanDialog.setHeaderText("Curretly Loaned books:");
			
		  
			returnBtn = new Button("Return");
			loanDialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
			loanDialog.close(); 
//		  cancelBtn = new Button("Cancel");
//		  cancelBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//		    @Override
//		    public void handle(MouseEvent mouseEvent) {
//		    	dialog.close();}});
		  
		  
		  // Create the username and password labels and fields.
			GridPane loangrid = new GridPane();
			loangrid.setHgap(10);
			loangrid.setVgap(10);
			loangrid.setPadding(new Insets(20, 150, 10, 10));
			bookIds = new ArrayList<Integer>();
			loanedBooksCB.clear();		
			while(myRslt.next())
			{
				userId = myRslt.getInt("Borrower_ID");
				bookIds.add(myRslt.getInt("bookId"));
				loanedBooksCB.add(myRslt.getString("Title"));
			}

			
			loanedBooks = new ComboBox();
			//loanedBooks.getItems().removeAll(loanedBooksCB);
			loanedBooks.getItems().addAll(loanedBooksCB);
		  loanedBooks.getSelectionModel().selectFirst();
      selectBookLbl = new Label("Select book: ");
			returnDateLbl = new Label("Return Date: ");
			date = LocalDate.now();
			returnDate = new Label(date.toString());
      returnBtn = new Button("Return");
     
      returnBtn.setOnAction(this);
      loangrid.add(selectBookLbl,1,1);
			loangrid.add(loanedBooks,2,1);
			loangrid.add(returnDateLbl,1,2);
			loangrid.add(returnDate,2,2);
			loangrid.add(returnBtn, 2,3);
			loanDialog.getDialogPane().setContent(loangrid);
			
			loanDialog.showAndWait();
		}
		//ADD LOAN WIZARD
		else if (addLoanQuery == true)
		{
		  // Create the custom dialog.
			addLoandialog = new Dialog<>();
			addLoandialog.setTitle("Add Book Loan Wizard: ");
			addLoandialog.setHeaderText("Add Loan: ");
			
		  
			addLoanBtn = new Button("Add Loan");
			addLoandialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
			addLoandialog.close(); 
//		  cancelBtn = new Button("Cancel");
//		  cancelBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//		    @Override
//		    public void handle(MouseEvent mouseEvent) {
//		    	dialog.close();}});
		  
		  
			GridPane addLoangrid = new GridPane();
			addLoangrid.setHgap(10);
			addLoangrid.setVgap(10);
			addLoangrid.setPadding(new Insets(20, 150, 10, 10));
			bookIds = new ArrayList<Integer>();
			loanedBooksCB.clear();		
			while(myRslt.next())
			{
				bookIds.add(myRslt.getInt("BookID"));
				loanedBooksCB.add(myRslt.getString("Title"));
			}

			
			loanedBooks = new ComboBox();
			String durations[] = {"In One Week", "In Two Weeks", "In Three Weeks"};
			loanDuration = 
          new ComboBox(FXCollections 
                      .observableArrayList(durations)); 
			loanDuration.getSelectionModel().selectFirst();

			//loanedBooks.getItems().removeAll(loanedBooksCB);
			loanedBooks.getItems().addAll(loanedBooksCB);
		  loanedBooks.getSelectionModel().selectFirst();
      commentTxt = new TextField("");
      dueDateTxt = new TextField("");
      userIdTxt = new TextField("");
     
      date = LocalDate.now();
      dateOut = new Label(date.toString());
      Label availableBooks = new Label("Choose Book: ");
      Label cmmnt = new Label("Comment: ");
      Label dueDateLbl = new Label("Due Date: ");
      Label UserId = new Label("Borrower ID: ");
      Label dateOutLbl = new Label("Date Out: ");
      
      
      loanBtn = new Button("Add Loan");
      loanBtn.setOnAction(this);
      addLoangrid.add(availableBooks,1,1);
		  addLoangrid.add(loanedBooks,2,1);
		  addLoangrid.add(cmmnt,1,2);
      addLoangrid.add(commentTxt, 2, 2);
      addLoangrid.add(dueDateLbl, 1, 3);
      addLoangrid.add(loanDuration, 2, 3);
      addLoangrid.add(UserId, 1, 4);
      addLoangrid.add(userIdTxt, 2, 4);
      addLoangrid.add(dateOutLbl, 1, 5);
      addLoangrid.add(dateOut, 2, 5);
      addLoangrid.add(loanBtn, 2, 6);
			
		  addLoandialog.getDialogPane().setContent(addLoangrid);
			
		  addLoandialog.showAndWait();
		}
		
			
		}//end try
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, "SQL Exception, message is: " + ex.getMessage());
			
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "ERROR! Enter in correct format " + ex.getMessage());
		}
		finally
		{
			//clean up code if bad things start to happen. This code ALWAYS runs.					
			try
			{
				if(myRslt != null)
				  myRslt.close();
				if(myStmt != null)
				  myStmt.close();
				if(myConn != null)
				  myConn.close();	
			}//end try
			catch(SQLException ex)
			{
				JOptionPane.showMessageDialog(null, "SQLException INSIDE finally block: "+ ex.getMessage());
				ex.printStackTrace();
			}
		}//end finally
		
		
	}
	
	/*
	 * Checks whether the input string is of type LONG
	 */
	public boolean parseIntChecker(String s)
	{
		try {  
	    Long.parseLong(s);  
	    return true;
	  } catch(NumberFormatException e){  
	    return false;  
	  } 
		
	}
	
	public static void main(String[] args)
	{
		// the only line of code you need in the JavaFX main()...
		launch(args);

	}//end main

	


}
//end Class