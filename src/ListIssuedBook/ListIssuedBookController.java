/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListIssuedBook;




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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class ListIssuedBookController implements Initializable {
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;      
        Statement st=null;
    
    ObservableList<issuedBook> issuedbooksList = FXCollections.observableArrayList();
    
    @FXML
    private AnchorPane tablePanel;
    @FXML
    private TableView<issuedBook> issuedBookTable;
    @FXML
    private TableColumn<issuedBook, String> bookIdColumn;
    @FXML
    private TableColumn<issuedBook, String> bookNameColumn;
    @FXML
    private TableColumn<issuedBook, String> startingDateColumn;
    @FXML
    private TableColumn<issuedBook, String> dueDateColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       InitCol();
       try {
                loadData();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ListIssuedBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
    }    
    public static class issuedBook{
        private final SimpleStringProperty bookId;
        private final SimpleStringProperty bookName;
        private final SimpleStringProperty startingDate;
        private final SimpleStringProperty dueDate;

        public issuedBook(String bookId, String bookName, String startingDate, String dueDate) {
            this.bookId = new SimpleStringProperty (bookId);
            this.bookName = new SimpleStringProperty (bookName);
            this.startingDate = new SimpleStringProperty (startingDate);
            this.dueDate = new SimpleStringProperty (dueDate);
        }

        public String getBookId() {
            return bookId.get();
        }

        public String getBookName() {
            return bookName.get();
        }

        public String getStartingDate() {
            return startingDate.get();
        }

        public String getDueDate() {
            return dueDate.get();
        }
        
        
    }
    
    private void loadData() throws ClassNotFoundException, SQLException {
        
        
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from BOOKRESERVATION ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String issuedBook_Id= rs.getString("BOOKID");//sql tablosunda girilen column ile aynı isme sahip olmalı karakterler
            String issuedBook_Name= rs.getString("BOOKNAME");
            String starting_Date= rs.getString("STARTINGDATE");
            String Due_Date= rs.getString("DUEDATE");
            issuedbooksList.add(new issuedBook(issuedBook_Id, issuedBook_Name, starting_Date, Due_Date));
             
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(ListIssuedBookController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       issuedBookTable.getItems().setAll(issuedbooksList);
    
    }
    private void InitCol(){
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));  
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        startingDateColumn.setCellValueFactory(new PropertyValueFactory<>("startingDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        // aşağıdaki stringle aynı olmak zorunda 
    }
}
