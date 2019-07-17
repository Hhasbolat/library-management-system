
package librarymanagementsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import databaseConnection.database;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EnterenceWindowController implements Initializable {

    @FXML
    private Button main_librarian;
    @FXML
    private Button main_reservation;
    @FXML
    private Button main_book;
    @FXML
    private Label label;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        try {
            database.connection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(EnterenceWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }    

    @FXML
    private void librarianPaneliAc(ActionEvent event) {
        
         LoadWindow("/LibrarianMain/MainController.fxml", "librarian Page");

    }

    @FXML
    private void reservationPaneliAc(ActionEvent event) {
           LoadWindow("/ReservationMain/reservationMain.fxml", "reservation Page");
            
        
    }

    @FXML
    private void bookInformationPaneliAc(ActionEvent event) {
        
          LoadWindow("/BookInformationMain/bookInformation.fxml", "book Information Page");
            
        
        
    }
    
    
    
    
    void LoadWindow(String loc,String title){
        
        try{
          
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED );
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
            
            
        }
        catch(IOException e){
            Logger.getLogger(EnterenceWindowController.class.getName()).log(Level.SEVERE,null,e);
            
        }
    }
}
 