/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addingUser;


import ListUser.ListUserController;
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

/**
 * FXML Controller class
 *
 * @author khas
 */
public class AddingUserController implements Initializable {

    @FXML
    private AnchorPane mainPanel;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField userId;
    @FXML
    private TextField userName;
    @FXML
    private TextField userSurname;

    Connection con= null;
    PreparedStatement ps = null; 
    ResultSet rs = null;
    Boolean editMode = false;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addUser(ActionEvent event) throws ClassNotFoundException, SQLException {
         int user_ID = Integer.parseInt(userId.getText());
        String user_name = userName.getText();
        String user_surname = userSurname.getText();
     
        
        if (user_name.isEmpty() || user_surname.isEmpty()){ // we control the all data enterence 
        
             Alert alert = new  Alert(Alert.AlertType.ERROR); // we catch the error
             alert.setHeaderText("catched the error"); 
             alert.setContentText("please enter the all fields");
             alert.showAndWait();
        }
      else{
            if(editMode){ // edit butonunun tıklanıp tıklanmadığını kontorl ettik 
             updateUser();
             }
             else{
                 try {
               Class.forName("oracle.jdbc.driver.OracleDriver"); 
               con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       
            String SqlZ = " INSERT INTO USERINFORMATION VALUES (?,?,?)";
            ps = con.prepareStatement(SqlZ);
            ps.setInt(1,user_ID); // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
            ps.setString(2,user_name);
            ps.setString(3,user_surname);
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
             alert.setHeaderText("The process of adding a  is complete");
             alert.setContentText(" please make new operation"); 
             alert.showAndWait();
            Stage stage = (Stage) mainPanel.getScene().getWindow();
            stage.close(); // we close the window when we clicked the cancel button    
             }

         
    
        }
    }
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
        
    }

    @FXML
    private void dataInputKontrol(KeyEvent event) {
        try{
            int number = Integer.parseInt(userId.getText()); // book ıd text file ından değeri aldık 
            userId.setStyle("-fx-control-inner-background: #ffffff;"); // data input control line , if we can wrong type input it will show white line
   
        }
    
        catch(NumberFormatException e){
                
           
            userId.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
    
        
        
        
    }
    
     public void getTextForUpdate(ListUserController.User user){// update komutu için açılan ekranda text fielddan girilen değerleri aldık ve methoda yolladık
        userId.setText(user.getUserId());
        userName.setText(user.getUserName());
        userSurname.setText(user.getUserSurname());
        editMode = true;
    }  
    
    public void updateUser() throws SQLException, ClassNotFoundException{ // BOOK nesnesi oluşturdum txt fiedlddan alınan yeni değeri burda tuttuk
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        ListUserController.User user = new ListUserController.User(
               userId.getText(), 
               userName.getText(), 
               userSurname.getText()); 
               
      
      String sql = "update USERINFORMATION SET USERNAME=?,USERSURNAME=? where USERID=?";
       
       ps = con.prepareStatement(sql);
       // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
       ps.setString(1,user.getUserName());
       ps.setString(2,user.getUserSurname());
       ps.setInt(3,Integer.valueOf(user.getUserId()));
       ps.executeUpdate(); 
      
      String sql1 = "update BOOKRESERVATION SET USERNAME=?,USERSURNAME=? where USERID=?"; // bookreservation listedeki değerleride güncellemek için kullandım
        ps = con.prepareStatement(sql1);
       // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
       ps.setString(1,user.getUserName());
       ps.setString(2,user.getUserSurname());
       ps.setInt(3,Integer.valueOf(user.getUserId())) ;
       ps.executeUpdate(); 
       
       
       
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
    
       
   }

   
}
