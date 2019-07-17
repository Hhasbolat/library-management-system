/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListReservation;



import BookReservation.BookReservationController;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class ListReservationController implements Initializable {
        Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;      
        Statement st=null;
    
    
    ObservableList<BookReservation> bookReservationList = FXCollections.observableArrayList();
    @FXML
    private AnchorPane tablePanel;
    @FXML
    private TableColumn<BookReservation, String> bookIdColumn;
    @FXML
    private TableColumn<BookReservation, String> bookNameColumn;
    @FXML
    private TableColumn<BookReservation, String> userIdColumn;
    @FXML
    private TableColumn<BookReservation, String> userNameColumn;
    @FXML
    private TableColumn<BookReservation, String> userSurnameColumn;
    @FXML
    private TableColumn<BookReservation, String> startingDateColumn;
    @FXML
    private TableColumn<BookReservation, String> dueDateColumn;
    @FXML
    private TableView<BookReservation> bookReservationTable;
    @FXML
    private TableColumn<BookReservation, String> reservationIdColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            try {    
                init();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListReservationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ListReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try {
                InitCol();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ListReservationController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ListReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {
                loadData();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ListReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }    

    @FXML
    private void updateReservation(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        
        
        
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");   
    
        BookReservation edit = bookReservationTable.getSelectionModel().getSelectedItem(); // we generated 
        if (edit== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select reservation ");
            alert.setContentText("choose reservation for edit operation");
            alert.showAndWait();
            return; 
        }
        else{ 
            //update işlemini yaptığımız sırada seçtiğimiz satırın bilgilerini update pencerisine eklemek için kullandık 
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BookReservation/bookReservation.fxml"));
            Parent parent = loader.load();
            
            BookReservationController controller = loader.getController();
            controller.getTextForUpdate(edit);
            
            Stage stage = new Stage(StageStyle.DECORATED );
            stage.setTitle("Editing operation");
            stage.setScene(new Scene(parent));
            stage.show();
            
             
           
           
        
        }   
           Stage stage = (Stage) tablePanel.getScene().getWindow();
           stage.close(); // we close the window when we clicked the cancel button
    
        
    }

    @FXML
    private void deleteReservation(ActionEvent event) throws ClassNotFoundException, SQLException {
        
         Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
                                                      
                                                     
       BookReservation delete = bookReservationTable.getSelectionModel().getSelectedItem(); // we generated 
        if (delete== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select reservation ");
            alert.setContentText("choose reservation for delete operation");
            alert.showAndWait();
            return; 
        }
        else{
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123"); 
            String sql = "delete from BOOKRESERVATION where RESERVATIONID="+delete.getReservationId();
            ps = con.prepareStatement(sql);
            rs =ps.executeQuery(); 
            
            
       
          JOptionPane.showMessageDialog( null,"delete operation was succesful" );
          
          Stage stage = (Stage) tablePanel.getScene().getWindow();
          stage.close(); // we close the window when we clicked the cancel button
    
        }
    
        
        
        
    }
    
    
    public static class BookReservation{
        private final SimpleStringProperty bookId;
        private final SimpleStringProperty bookName;
        private final SimpleStringProperty userId;
        private final SimpleStringProperty userName;
        private final SimpleStringProperty userSurname;
        private final SimpleStringProperty startingDate;
        private final SimpleStringProperty DueDate;
        private final SimpleStringProperty reservationId;

       

        public BookReservation(String bookId, String bookName, String userId, String userName, String userSurname, String startingDate, String DueDate,String reservationId) {
            this.bookId = new SimpleStringProperty (bookId);
            this.bookName = new SimpleStringProperty (bookName);
            this.userId = new SimpleStringProperty (userId);
            this.userName = new SimpleStringProperty (userName);
            this.userSurname = new SimpleStringProperty (userSurname);
            this.startingDate = new SimpleStringProperty (startingDate);
            this.DueDate = new SimpleStringProperty (DueDate);
            this.reservationId = new SimpleStringProperty (reservationId);
       
        }

        public String getBookId() {
            return bookId.get();
        }

        public String getBookName() {
            return bookName.get();
        }

        public String getUserId() {
            return userId.get();
        }

        public String getUserName() {
            return userName.get();
        }

        public String getUserSurname() {
            return userSurname.get();
        }

        public String getStartingDate() {
            return startingDate.get();
        }

        public String getDueDate() {
            return DueDate.get();
        }

        public String getReservationId() {
            return reservationId.get();
        }
        
        

}
    private void InitCol() throws ClassNotFoundException, SQLException{ // databaseden aldığımız verileri array listte tutuyoruz ve initcol fonksiyonunu list nutonuna bastığımız anda ekrana verileri yazdırmak için kullandım
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));  
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("userSurname"));  
        startingDateColumn.setCellValueFactory(new PropertyValueFactory<>("startingDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("DueDate"));
        reservationIdColumn.setCellValueFactory(new PropertyValueFactory<>("reservationId")); 
        
        
        
        
         }
    
    private void init() throws ClassNotFoundException, SQLException{
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from BOOKRESERVATION ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery();
    }
    
    private void loadData() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from BOOKRESERVATION ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String book_Id= rs.getString("BOOKID");//sql tablosunda girilen column ile aynı isme sahip olmalı karakterler
            String book_Name= rs.getString("BOOKNAME");
            String user_ıd= rs.getString("USERID");
            String user_name= rs.getString("USERNAME");
            String user_surname= rs.getString("USERSURNAME");
            String starting_date= rs.getString("STARTINGDATE");
            String due_date= rs.getString("DUEDATE");
            String reservation_ıd= rs.getString("RESERVATIONID");
            
            bookReservationList.add(new BookReservation(book_Id, book_Name, user_ıd, user_name, user_surname, starting_date, due_date,reservation_ıd));
             
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(ListReservationController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       bookReservationTable.getItems().setAll(bookReservationList);
    
    }
    
    
}