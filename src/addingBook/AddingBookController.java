/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addingBook;

import ListBook.ListBookController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;



public class AddingBookController implements Initializable {

    @FXML
    private AnchorPane mainPanel;
    @FXML
    private TextField bookPublisher;
    @FXML
    private TextField bookAuthor;
    @FXML
    private TextField bookName;
    @FXML
    private TextField Bookıd;
    @FXML
    private Button addbutton;
    @FXML
    private Button cancelButton;

    Connection con= null;
    PreparedStatement ps = null; 
    ResultSet rs = null;
    Boolean editMode = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void dataInputControlBook(KeyEvent event) {
    try{
            int number = Integer.parseInt(Bookıd.getText()); // book ıd text file ından değeri aldık 
            Bookıd.setStyle("-fx-control-inner-background: #ffffff;"); // data input control line , if we can wrong type input it will show white line
   
        }
    
        catch(NumberFormatException e){
                
           
            Bookıd.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
    
    
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
        
    }

    @FXML
    private void addBook(ActionEvent event) throws ClassNotFoundException, SQLException {
        int book_ID = Integer.parseInt(Bookıd.getText());
        String book_name = bookName.getText();
        String book_author = bookAuthor.getText();
        String book_publisher = bookPublisher.getText();
        
        
        if ( book_name.isEmpty() || book_author.isEmpty()|| book_publisher.isEmpty()){ // we control the all data enterence 
        
             Alert alert = new  Alert(Alert.AlertType.ERROR); // we catch the error
             alert.setHeaderText("catched the error"); 
             alert.setContentText("please enter the all fields");
             alert.showAndWait();
        }
        else{
             if(editMode){ // edit butonunun tıklanıp tıklanmadığını kontorl ettik 
             updateBook();
             }
             else{
                 try {
               Class.forName("oracle.jdbc.driver.OracleDriver"); 
               con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       
            String SqlZ = " INSERT INTO BOOK VALUES (?,?,?,?)";
            ps = con.prepareStatement(SqlZ);
            ps.setInt(1,book_ID); // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
            ps.setString(2,book_name);
            ps.setString(3,book_author);
            ps.setString(4,book_publisher);
            rs =ps.executeQuery(); 
           } 
            catch(SQLException s) { 
                       s.getMessage();
           }
              finally {
                           rs.close();
                           ps.close();
                           con.close();
           } 
             Alert alert = new  Alert(Alert.AlertType.INFORMATION); // we catch the error
             alert.setHeaderText("The process of adding a BOOK is complete");
             alert.setContentText(" please make new operation"); 
             alert.showAndWait();
            Stage stage = (Stage) mainPanel.getScene().getWindow();
            stage.close(); // we close the window when we clicked the cancel button    
             }

         
    
        }
        
     
        
    }
     public void getTextForUpdate(ListBookController.Book book){// update komutu için açılan ekranda text fielddan girilen değerleri aldık ve methoda yolladık
        Bookıd.setText(book.getBookId());
        bookName.setText(book.getBookName());
        bookAuthor.setText(book.getBookAuthor());
        bookPublisher.setText(book.getBookPublisher());
        editMode = true;
    }  
    public void updateBook() throws SQLException, ClassNotFoundException{ // BOOK nesnesi oluşturdum txt fiedlddan alınan yeni değeri burda tuttuk
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        ListBookController.Book book = new ListBookController.Book(
               Bookıd.getText(), 
               bookName.getText(), 
               bookAuthor.getText(), 
               bookPublisher.getText());
               
      
       String sql = "update BOOK SET BOOKNAME=?,BOOKAUTHOR=?,BOOKPUBLISHER=? where BOOKID=?";
       
       ps = con.prepareStatement(sql);
       // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
       ps.setString(1,book.getBookName());
       ps.setString(2,book.getBookAuthor());
       ps.setString(3,book.getBookPublisher());
       ps.setInt(4,Integer.valueOf(book.getBookId()));
       ps.executeUpdate(); 
      
       
       
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
    
       
   }

}
