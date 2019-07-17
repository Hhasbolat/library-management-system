/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListUser;

import ListBook.ListBookController;
import addingBook.AddingBookController;
import addingUser.AddingUserController;
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
public class ListUserController implements Initializable {
       Connection con=null;
        PreparedStatement ps = null; 
        ResultSet rs = null;      
        Statement st=null;
    
 ObservableList<User> usersList = FXCollections.observableArrayList();
    
    
    @FXML
    private AnchorPane tablePanel;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> userIdColumn;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> userSurnameColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       InitCol();
            try {
                loadData();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ListUserController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
    }  

    @FXML
    private void deleteUser(ActionEvent event) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");               
                                                      
        User delete = userTable.getSelectionModel().getSelectedItem(); // we generated 
        if (delete== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select user ");
            alert.setContentText("choose user for delete operation");
            alert.showAndWait();
            return; 
        }
        else{
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123"); 
            String sql = "delete from USERINFORMATION where USERID="+delete.getUserId();
            ps = con.prepareStatement(sql);
            rs =ps.executeQuery(); 
            String sql1 = "delete from BOOKRESERVATION where USERID="+delete.getUserId();
            ps = con.prepareStatement(sql1);
            rs =ps.executeQuery(); 
            //usersList.remove(delete);
       
          JOptionPane.showMessageDialog( null,"delete operation was succesful" );
          
          Stage stage = (Stage) tablePanel.getScene().getWindow();
          stage.close(); // we close the window when we clicked the cancel button
    
        }
        
        
    }

    @FXML
    private void editUser(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        
         Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");   
    
        User edit = userTable.getSelectionModel().getSelectedItem(); // we generated 
        if (edit== null){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("you did not select book ");
            alert.setContentText("choose book for edit operation");
            alert.showAndWait();
            return; 
        }
        else{ 
            //update işlemini yaptığımız sırada seçtiğimiz satırın bilgilerini update pencerisine eklemek için kullandık 
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addingUser/AddingUser.fxml"));
            Parent parent = loader.load();
            
            AddingUserController controller = loader.getController();
            controller.getTextForUpdate(edit);
            
            Stage stage = new Stage(StageStyle.DECORATED );
            stage.setTitle("Editing operation");
            stage.setScene(new Scene(parent));
            stage.show();
            
             
           
           
        
        }   
           Stage stage = (Stage) tablePanel.getScene().getWindow();
           stage.close(); // we close the window when we clicked the cancel button
    
        
    }

    

   
    
    
    
    public static class User{
        private final SimpleStringProperty UserId;
        private final SimpleStringProperty UserName;
        private final SimpleStringProperty UserSurname;

        public User(String UserId, String UserName, String UserSurname) {
            this.UserId = new SimpleStringProperty (UserId);
            this.UserName = new SimpleStringProperty (UserName);
            this.UserSurname = new SimpleStringProperty (UserSurname);
        }

        public String getUserId() {
            return UserId.get();
        }

        public String getUserName() {
            return UserName.get();
        }

        public String getUserSurname() {
            return UserSurname.get();
        }
    
    
}
    
    private void InitCol(){
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("UserId"));  
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        userSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("UserSurname"));
    }  
    private void loadData() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        String Sql2 = "select * from USERINFORMATION ";
        ps = con.prepareStatement(Sql2);
        rs =ps.executeQuery(); 
        
        try {
            while(rs.next()) {
            String userId= rs.getString("USERID");//sql tablosunda girilen column ile aynı isme sahip olmalı karakterler
            String userName= rs.getString("USERNAME");
            String userSurname= rs.getString("USERSURNAME");
            
            usersList.add(new User(userId, userName, userSurname));
             
                  
        }

            
        } catch (SQLException e) {
            Logger.getLogger(ListUserController.class.getName()).log(Level.SEVERE,null,e);
        }
       
       userTable.getItems().setAll(usersList);
    
    }
    
    
       
}
