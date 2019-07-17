


package BookReservation;
import java.util.ArrayList;
import ListReservation.ListReservationController;
import databaseConnection.database;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
/**
 * FXML Controller class
 *
 * @author khas
 */
public class BookReservationController implements Initializable {
   
    int BookId;
    Boolean editMode= false;
    int enteruserId;
    int enterBookId;
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private TextField userId;
    @FXML
    private TextField userName;
    //private ComboBox<String> bookIdBox;
    @FXML
    private ComboBox<String> bookNameBox;
    @FXML
    private TextField userSurname;
    @FXML
    private DatePicker startingDate;
    @FXML
    private DatePicker dueDate;
    @FXML
    private Button cancelButton;
    @FXML
    private Button recordBookButton;
    
    
     Connection con=null; 
    Statement st=null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    

    ObservableList<String> bookIdList = FXCollections.observableArrayList(); //bood ıdsini combo box a yazdırmak için kullandım
    ObservableList<String> bookNameList = FXCollections.observableArrayList();//book ADLARINI TUTTUK
    String usernNameList= new String();//user ADLARINI TUTTUK
    String usernSurnameList= new String(); //user SOYADLARINI TUTTUK
    
    
    
    @FXML
    private TextField bookIdTextField;
    @FXML
    private Button book;
    @FXML
    private TextField reservationIdtextField;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
        
        
        try {
            printBookIdIntoComboBox();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookReservationController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BookReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    

    @FXML
    private void userIdControl(KeyEvent event) {
        
        try{
            int number = Integer.parseInt(userId.getText()); // user ıd text file ından değeri aldık 
            userId.setStyle("-fx-control-inner-background: #ffffff;"); // data input control line , if we can wrong type input it will show white line
   
        }
    
        catch(NumberFormatException e){
                
           
            userId.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
        
    }

    private void bookSelection(ActionEvent event) throws ClassNotFoundException, SQLException {
      
          
        /*
       Class.forName("oracle.jdbc.driver.OracleDriver"); 
       con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       String Sql = " select * from BOOK WHERE  BOOKID= '"+bookIdBox.getSelectionModel().getSelectedItem()+"'"; //book ıd butonuna bastığımız zaman gelen numaraların bulunduğu combo box
      //System.out.println(Sql);
       ps = con.prepareStatement(Sql);
       rs =ps.executeQuery(); 
       bookNameBox.getItems().removeAll(bookNameList); // combo box ın içindeki değerleri sildik eğer tekrar NAME seçmek istiyorsak 
       while(rs.next()) { //book name box da kitap isimlerini görüyoruz
                  
                  String BookName = rs.getString("BOOKNAME"); // result setten dönen değeri aldık ve combo box ın içine yolladık
                  bookNameList.add(BookName);
                  
                  bookNameBox.setItems(bookNameList);
                }
   */     
    }
   
    
    private void printBookIdIntoComboBox() throws ClassNotFoundException, SQLException{
        /*
       Class.forName("oracle.jdbc.driver.OracleDriver"); 
       con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       String Sql = " select BOOKID from BOOK "; //book ıd butonuna bastığımız zaman gelen numaraların bulunduğu combo box
      //System.out.println(Sql);
       ps = con.prepareStatement(Sql);   
       rs =ps.executeQuery();  
       
        
       while(rs.next()) {
                  
                  String BookId = rs.getString("BOOKID"); // result setten dönen değeri aldık ve combo box ın içine yolladık
                  bookIdList.add(BookId);
                  
                  bookIdBox.setItems(bookIdList);
                }
       
       
        
       */
    } 


    @FXML
    private void cancelButton(ActionEvent event) {
    
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
        
    }
    
 
    @FXML
    private void recordBook(ActionEvent event) throws ClassNotFoundException, SQLException {
        
       
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       
        
        //int bookIdnumber = Integer.parseInt(bookIdBox.getSelectionModel().getSelectedItem());
         int bookIdnumber = Integer.parseInt(bookIdTextField.getText());
        
         String Bookname = bookNameBox.getValue();
        
        int userIdnumber = Integer.parseInt(userId.getText());
        
        String userNam= userName.getText(); 
        
        String usersur= userSurname.getText();  
        
        startingDate.getValue(); // we took the date value from adding patient panel
        
        dueDate.getValue(); 
        int reservationumber = Integer.parseInt(reservationIdtextField.getText());
        
         if (Bookname.isEmpty() || userNam.isEmpty()|| usersur.isEmpty()){ // we control the all data enterence 
        
             Alert alert = new  Alert(Alert.AlertType.ERROR); // we catch the error
             alert.setHeaderText("catched the error"); 
             alert.setContentText("please enter the all fields");
             alert.showAndWait();
        }
        
         else{  
                
                if(editMode){ // edit butonunun tıklanıp tıklanmadığını kontorl ettik 
                updateBookReservation();
             
                } 
                else{
                    try{
                        String Sql =  " insert into BOOKRESERVATION values (?,?,?,?,?,?,?,?)";
                        ps = con.prepareStatement(Sql);
                        
                        ps.setInt(1,bookIdnumber);
                        ps.setString(2,Bookname);
                        ps.setInt(3,userIdnumber);
                        ps.setString(4,userNam);
                        ps.setString(5,usersur);
                        ps.setDate(6,Date.valueOf(startingDate.getValue()));
                        ps.setDate(7,Date.valueOf(dueDate.getValue()));
                        ps.setInt(8,reservationumber);
                        ps.executeQuery(); 
                    } 
                    catch (SQLException e){
                        e.printStackTrace();
                    }
        
             Alert alert = new  Alert(Alert.AlertType.INFORMATION); // we catch the error
             alert.setHeaderText("The process of adding a book reservation is complete");
             alert.setContentText(" please make new operation"); 
             alert.showAndWait();
             Stage stage = (Stage) mainPanel.getScene().getWindow();
             stage.close(); // we close the window when we clicked the cancel button  
            }
        
    
       
        

    
        }
                
     
        
        
        
    }
    
    public Integer getBookId() {
        return Integer.valueOf(bookIdTextField.getText());
    }  

    
    
    @FXML
    private void bookIdSaveButton(ActionEvent event) throws ClassNotFoundException, SQLException {
       Class.forName("oracle.jdbc.driver.OracleDriver"); 
       con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       
       int enterBookId = Integer.parseInt(bookIdTextField.getText()); 
       
       
     // String Sql = " select * from BOOK WHERE  BOOKID= '"+bookIdTextField.getText()+"'"; //book ıd butonuna bastığımız zaman gelen numaraların bulunduğu combo box
      String Sql1 = " select * from BOOK WHERE  BOOKID= "+enterBookId; //book ıd butonuna bastığımız zaman gelen numaraların bulunduğu combo box
      
    //System.out.println(Sql);
      ps = con.prepareStatement(Sql1);
       rs =ps.executeQuery(); 
       bookNameBox.getItems().removeAll(bookNameList); // combo box ın içindeki değerleri sildik eğer tekrar NAME seçmek istiyorsak 
       while(rs.next()) { //book name box da kitap isimlerini görüyoruz
                  
                  String BookName = rs.getString("BOOKNAME"); // result setten dönen değeri aldık ve combo box ın içine yolladık
                  bookNameList.add(BookName);
                  
                  bookNameBox.setItems(bookNameList);
                }
    }

    @FXML
    private void bookIdControl(KeyEvent event) {
         try{
            int number = Integer.parseInt(bookIdTextField.getText()); // book ıd text file ından değeri aldık 
            bookIdTextField.setStyle("-fx-control-inner-background: #ffffff;"); // data input control line , if we can wrong type input it will show white line
   
        }
    
        catch(NumberFormatException e){
                
           
            bookIdTextField.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
    
    }
    
    public void getTextForUpdate(ListReservationController.BookReservation reservation){
        reservationIdtextField.setText(reservation.getReservationId());
        
        bookIdTextField.setText(reservation.getBookId());
        
        // bookIdBox.setValue(reservation.getBookId());
        bookNameBox.setValue(reservation.getBookName());
        userId.setText(reservation.getUserId());
        userName.setText(reservation.getUserName());
        userSurname.setText(reservation.getUserSurname());
        startingDate.setValue(LocalDate.now());
        dueDate.setValue(LocalDate.now());
       
        editMode = true;
        
    }
  
   
    
    public void updateBookReservation() throws SQLException, ClassNotFoundException{ // BOOK nesnesi oluşturdum txt fiedlddan alınan yeni değeri burda tuttuk
     
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
        con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
        ListReservationController.BookReservation reservation = new ListReservationController.BookReservation(
               //bookIdBox.getValue(),
                bookIdTextField.getText(),
                bookNameBox.getValue(),
               userId.getText(),
               userName.getText(),
               userSurname.getText(),
               startingDate.getValue().toString(),
               dueDate.getValue().toString(),
                reservationIdtextField.getText());
               
      
       String sql = "UPDATE BOOKRESERVATION SET BOOKID=?, BOOKNAME=?,USERID=?,USERNAME=?,USERSURNAME=?,STARTINGDATE=?,DUEDATE=?  where RESERVATIONID=? ";
        
       ps = con.prepareStatement(sql);
       // eğer kullanıcıdan almayıp değer girmemiz istenseydi brand yerine degiştirilecek değer yazılacaktı
   
       ps.setInt(1,Integer.valueOf(reservation.getBookId()));
       ps.setString(2,reservation.getBookName());
       ps.setInt(3,Integer.valueOf(reservation.getUserId()));
       ps.setString(4,reservation.getUserName());
       ps.setString(5,reservation.getUserSurname());
       ps.setDate(6,Date.valueOf(reservation.getStartingDate()));
       ps.setDate(7,Date.valueOf(reservation.getDueDate()));
       ps.setInt(8,Integer.valueOf(reservation.getReservationId()));
       ps.executeUpdate(); 
      
       
       
        Stage stage = (Stage) mainPanel.getScene().getWindow();
        stage.close(); // we close the window when we clicked the cancel button
    
      
   }

    @FXML
    private void savetheUserInformation(ActionEvent event) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver"); 
       con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
       
       int enteruserId = Integer.parseInt(userId.getText()); 
       
       
     // String Sql = " select * from BOOK WHERE  BOOKID= '"+bookIdTextField.getText()+"'"; //book ıd butonuna bastığımız zaman gelen numaraların bulunduğu combo box
      String Sql1 = " select USERNAME,USERSURNAME from USERINFORMATION WHERE  USERID= "+enteruserId; //book ıd butonuna bastığımız zaman gelen numaraların bulunduğu combo box
      
    //System.out.println(Sql);
      ps = con.prepareStatement(Sql1);
       rs =ps.executeQuery(); 
       userName.setText(""); // text field ın içindeki değerleri sildik eğer tekrar NAME seçmek istiyorsak 
       userSurname.setText("");// text field ın içindeki değerleri sildik eğer tekrar NAME seçmek istiyorsak 
       while(rs.next()) { //book name box da kitap isimlerini görüyoruz
                  try{
                  String username = rs.getString("USERNAME"); // result setten dönen değeri aldık ve combo box ın içine yolladık
                  
                  userName.appendText(username);
                  String usersurname = rs.getString("USERSURNAME");
                  
                  userSurname.appendText(usersurname);;
                }
                  catch (Exception e){
                      
                      System.out.println(e.toString());
                }
                  
                }
       
       
       
    }

        
        
        
    

    @FXML
    private void reservationIdControl(KeyEvent event) {
          try{
            int number = Integer.parseInt(reservationIdtextField.getText()); // user ıd text file ından değeri aldık 
            reservationIdtextField.setStyle("-fx-control-inner-background: #ffffff;"); // data input control line , if we can wrong type input it will show white line
   
        }
    
        catch(NumberFormatException e){
                
           
            reservationIdtextField.setStyle("-fx-control-inner-background: #ff1a1a;"); // data input control line if we can wrong type input it will show red line
          } 
        
    }
        
 
}


    
    


