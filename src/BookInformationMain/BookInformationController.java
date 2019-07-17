/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookInformationMain;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import librarymanagementsystem.EnterenceWindowController;

/**
 * FXML Controller class
 *
 * @author khas
 */
public class BookInformationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadBookInformation(ActionEvent event) {
        LoadWindow("/ListBook/ListBook.fxml", "list book window");
    }

    @FXML
    private void loadIssuedBookInformation(ActionEvent event) {
        LoadWindow("/ListIssuedBook/listIssuedBook.fxml", "list issued book window");
    
        
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
            Logger.getLogger(BookInformationController.class.getName()).log(Level.SEVERE,null,e);
            
        }
    }

}
