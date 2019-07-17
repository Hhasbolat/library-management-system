/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListBook;

import addingBook.AddingBookController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class ListBookController implements Initializable {
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;      
        Statement st=null;
    
    
    ObservableList<Book> booksList = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane tablePanel;
    @FXML
    private TableView<Book> BookTable;
    @FXML
    private TableColumn<Book, String> bookIdColumn;
    @FXML
    private TableColumn<Book, String> bookNameColumn;
    @FXML
    private TableColumn<Book, String> bookAuthorColumn;
    @FXML
    private TableColumn<Book, String> bookPublisherColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InitCol();
            try {
                loadData();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ListBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
    }  

    @FXML
    private void deleteBook(ActionEvent event) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
                                                      
                                                     
        Book delete = BookTable.getSelectionModel().getSelectedItem(); 
        if (delete== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select book ");
            alert.setContentText("choose book for delete operation");
            alert.showAndWait();
            return; 
        }
        else{
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123"); 
            String sql = "delete from BOOK where BOOKID="+delete.getBookId();
            ps = con.prepareStatement(sql);
            rs =ps.executeQuery(); 
            
          
       
          JOptionPane.showMessageDialog( null,"delete operation was succesful" );
          
          Stage stage = (Stage) tablePanel.getScene().getWindow();
          stage.close(); // we close the window when we clicked the cancel button
    
        }
    
        
        
    }

    @FXML
    private void editBook(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
         Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");   
    
        Book edit = BookTable.getSelectionModel().getSelectedItem(); // we generated 
        if (edit== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select book ");
            alert.setContentText("choose book for edit operation");
            alert.showAndWait();
            return; 
        }
        else{ 
            //update işlemini yaptığımız sırada seçtiğimiz satırın bilgilerini update pencerisine eklemek için kullandık 
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addingBook/addingBook.fxml"));
            Parent parent = loader.load();
            
            AddingBookController controller = loader.getController();
            controller.getTextForUpdate(edit);
            
            Stage stage = new Stage(StageStyle.DECORATED );
            stage.setTitle("Editing operation");
            stage.setScene(new Scene(parent));
            stage.show();
            
             
           
           
        
        }   
           Stage stage = (Stage) tablePanel.getScene().getWindow();
           stage.close(); // we close the window when we clicked the cancel button
    
        
    }

    
    
    public static class Book{
        
        
        private final SimpleStringProperty bookId;
        private final SimpleStringProperty bookName;
        private final SimpleStringProperty bookAuthor;
        private final SimpleStringProperty bookPublisher;

        public Book(String bookId, String bookName, String bookAuthor, String bookPublisher) {
            this.bookId = new SimpleStringProperty (bookId);
            this.bookName = new SimpleStringProperty (bookName);
            this.bookAuthor = new SimpleStringProperty (bookAuthor);
            this.bookPublisher = new SimpleStringProperty (bookPublisher);
        }

        public String getBookId() {
            return bookId.get();
        }

        public String getBookName() {
            return bookName.get();
        }

        public String getBookAuthor() {
            return bookAuthor.get();
        }

        public String getBookPublisher() {
            return bookPublisher.get();
        }
        
        
       
        
    }
        private void InitCol(){
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));  
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        bookPublisherColumn.setCellValueFactory(new PropertyValueFactory<>("bookPublisher"));
        // aşağıdaki stringle aynı olmak zorunda 
        
    }
     private void loadData() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from BOOK ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String book_Id= rs.getString("BOOKID");//sql tablosunda girilen column ile aynı isme sahip olmalı karakterler
            String book_Name= rs.getString("BOOKNAME");
            String book_author= rs.getString("BOOKAUTHOR");
            String book_publisher= rs.getString("BOOKPUBLISHER");
            booksList.add(new Book(book_Id, book_Name, book_author, book_publisher));
             
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(ListBookController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       BookTable.getItems().setAll(booksList);
    
    }
    
    
    
    
 
        
}
