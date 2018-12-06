/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.system.application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import user.database.DatabaseHandler;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import javafx.beans.value.*;
import javafx.collections.*;
import javafx.collections.transformation.*;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author christianbaiza
 */
public class SchedulingSystemApplication extends Application {
    private UserAccount newUser;
    DatabaseHandler databaseHandler;
    
    // List of appointment table properties
    private String[] propertyName = {"title",
       "date", "location"};
    private String[] propertyLabel = {"Appointment Name",
       "Date", "Location"};
    private ApptDAO appt = new ApptDAO();
    private final GridPane gridPane = new GridPane();
    private final Label lblName = new Label("Search by Name");
    private final TextField searchField = new TextField();
    private ObservableList<Appointment> observableNames;
    private FilteredList<Appointment> filteredData;
    private SortedList<Appointment> sortedData;
    private final ListView<Appointment> listView;
    TableView<Appointment> apptTableView =
       new TableView<>();
    public SchedulingSystemApplication() {
       lblName.setTextFill(Color.web("#0076a3"));
       observableNames = FXCollections.observableArrayList
          (appt.getAppts());
       filteredData = new FilteredList<>
          (observableNames, p -> true);
       sortedData = new SortedList<>(filteredData);
       listView = new ListView<>(sortedData);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Scheduling System Application");
        //primaryStage.setScene(new Scene(root, 1000, 700));
        databaseHandler = new DatabaseHandler();
        /*
         * Add GridPane for login form
         */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene scene = new Scene(grid, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
       
        
        /*
         * Set scene title
         */
        Text scenetitle = new Text("Login");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 1, 0, 2, 1);

        /*
         * Add labels to each row
         */
        Label userName = new Label("Enter username:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        /*
         * Set PasswordField
         */
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        /*
         * Sign in button
         */
        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        
        /*
         * Set action target and create EventHandler for Sign in button
         */
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        btn.setOnAction((ActionEvent e) -> {
            //actiontarget.setFill(Color.FIREBRICK);
            //actiontarget.setText("Denied, LOL.");
            
            // If username and password are from existing account in database
            // Open the Main Stage
            showMainStage();
            // Else show error message
        });
        
        /*
         * Create account button
         */
        Button btn2 = new Button("Create account");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 1, 5);
        
        /*
         * Set action target for Create account button and set EventHandler
         */
        btn2.setOnAction((ActionEvent e) -> {
            showCreateAccountForm();
        });
    }
    

    
    void addUser() {
    	String first = newUser.getFirstName();
    	String last = newUser.getLastName();
    	String mail = newUser.getEmail();
    	String user = newUser.getUsername();
    	String pass = newUser.getPassword();
    	
    	if (first.isEmpty()||last.isEmpty()||mail.isEmpty()||user.isEmpty()||pass.isEmpty()) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill out all fields");
    		alert.showAndWait();
    		return;
    	}
//    	stmt.execute("CREATE TABLE " + TABLE_NAME + "("
//				+ "		username varchar(200) primary key,\n"
//				+ "		password varchar(200),\n"
//				+ "		email varchar(200),\n"
//				+ "		firstname varchar(200),\n"
//				+ "		lastname varchar(200),\n"
//				+ "		isAvail boolean default true"
//				+ "  )");
    	String qu = "INSERT INTO users VALUES ("+
    			"'" + user + "',"+
    			"'" + pass + "',"+
    			"'" + mail + "',"+
    			"'" + first + "',"+
    			"'" + last + "'"+
    			")";
    	System.out.println(qu);
    	if(databaseHandler.execAction(qu)) {
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setHeaderText(null);
    		alert.setContentText("Success");
    		alert.showAndWait();
    	}
    	else {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("Failed");
    		alert.showAndWait();
    	}
    }
    public void DayView()
    {
    	Stage stageOne = new Stage();
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene sceneOne = new Scene(grid, 700, 500);
        stageOne.setScene(sceneOne);
        stageOne.show();
        
        String currDayOfWeek = LocalDate.now().getDayOfWeek().toString();
        Text scenetitleOne = new Text(LocalDate.now().toString() + "\t" + currDayOfWeek);
        Text appt = new Text("Appointment List:");
        
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitleOne, 1, 0);
        
        appt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid.add(appt, 1, 1);
        
    }
    
    public void WeekView()
    {
    	Stage stageOne = new Stage();
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene sceneOne = new Scene(grid, 700, 500);
        stageOne.setScene(sceneOne);
        stageOne.show();
        
        int currMonth = LocalDate.now().getMonthValue();
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        Text scenetitleOne = new Text(months[currMonth-1]);
       
        int currDay = LocalDate.now().getDayOfMonth();
        String currDayOfWeek = LocalDate.now().getDayOfWeek().toString();
        Text days = new Text(currDay + "\t\t\t" + (currDay+1) + "\t\t\t" + (currDay + 2) + "\t\t\t"
        		+ (currDay+3) + "\t\t\t" + (currDay+4) + "\t\t\t" + (currDay+5) + "\t\t\t" + (currDay+6));
        
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 25));
        grid.add(scenetitleOne, 1, 0);
        
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(days, 1, 1);
        
    }
    
    public void changeUserPass()
    {
    	Stage stageOne = new Stage();
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene sceneOne = new Scene(grid, 700, 500);
        stageOne.setScene(sceneOne);
        stageOne.show();
        
        Text scenetitleOne = new Text("Change username/password");
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitleOne, 1, 0, 2, 1);
        
    	Label username = new Label("New Username:");
        grid.add(username, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        
        Label pw = new Label("New Password:");
        grid.add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Button updateAccountBtn = new Button("Update Username/Password");
        HBox hbUpdateAccountBtn = new HBox(10);
        hbUpdateAccountBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbUpdateAccountBtn.getChildren().add(updateAccountBtn);
        grid.add(hbUpdateAccountBtn, 1, 3);
        
        final Text actiontarget_2 = new Text();
        grid.add(actiontarget_2, 1, 4);
        updateAccountBtn.setOnAction((ActionEvent e) -> {
            //            	newUser = new UserAccount();
            newUser.setUsername(userTextField.getText());
            newUser.setPassword(pwBox.getText());
            actiontarget_2.setFill(Color.FIREBRICK);
            actiontarget_2.setText("User Account updated! New Username: " + newUser.getUsername());
            });
    }
    
    public void modifyAccount()
    {
    	Stage stageOne = new Stage();
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene sceneOne = new Scene(grid, 700, 500);
        stageOne.setScene(sceneOne);
        stageOne.show();
        
        Text scenetitleOne = new Text("Modify Account");
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitleOne, 1, 0, 2, 1);
        
        
    	Label fname = new Label("New First Name:");
        grid.add(fname, 0, 1);
        TextField firstTextField = new TextField();
        grid.add(firstTextField, 1, 1);
        
        Label lname = new Label("New Last Name:");
        grid.add(lname, 0, 2);
        TextField lastTextField = new TextField();
        grid.add(lastTextField, 1, 2);
        
        Label email = new Label("New Email:");
        grid.add(email, 0, 3);
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 3);
        
        
        Button updateBtn = new Button("Update Account");
        HBox hbUpdateBtn = new HBox(10);
        hbUpdateBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbUpdateBtn.getChildren().add(updateBtn);
        grid.add(hbUpdateBtn, 1, 4);
        
        final Text actiontarget_2 = new Text();
        grid.add(actiontarget_2, 1, 5);
        updateBtn.setOnAction((ActionEvent e) -> {
            //            	newUser = new UserAccount();
            newUser.setFirstName(firstTextField.getText());
            newUser.setLastName(lastTextField.getText());
            newUser.setEmail(emailTextField.getText());
            actiontarget_2.setFill(Color.FIREBRICK);
            actiontarget_2.setText("User Account updated! Hello, " + newUser.getFirstName() + " " + newUser.getLastName());
            });
    }
    
    public void makeAppointment() {
        /*
        Make Appointment
        - Show month view of calendar
        - Have user select day of appointment by clicking on calendar day
        - Set action to transfer selected calendar date to appointment date
        - Have user choose time of appointment, or time frame
        */
        Stage stageOne = new Stage();
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene sceneOne = new Scene(grid, 700, 500);
        stageOne.setScene(sceneOne);
        stageOne.show();
        
        Text scenetitleOne = new Text("Make Appointment");
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitleOne, 1, 0, 2, 1);

        /*
         * Add labels to each row
         */
        Label title = new Label("Title:");
        grid.add(title, 0, 1);
        TextField titleTextField = new TextField();
        grid.add(titleTextField, 1, 1);
        
        Label date = new Label("Date:");
        grid.add(date, 0, 2);
        TextField dateTextField = new TextField();
        grid.add(dateTextField, 1, 2);
        
        Label loc = new Label("Location:");
        grid.add(loc, 0, 3);
        TextField locTextField = new TextField();
        grid.add(locTextField, 1, 3);
        
        // Add button
        Button createAccountBtn = new Button("Make Appointment");
        HBox hbCreateAccountBtn = new HBox(10);
        hbCreateAccountBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbCreateAccountBtn.getChildren().add(createAccountBtn);
        grid.add(hbCreateAccountBtn, 1, 4);
        
        //Event Handler for CreateAccount button
        final Text actiontarget_2 = new Text();
        grid.add(actiontarget_2, 1, 5);
        createAccountBtn.setOnAction((ActionEvent e) -> {
            Appointment a = new Appointment();
            a.setTitle(titleTextField.getText());
            a.setDate(dateTextField.getText());
            a.setLocation(loc.getText());
            appt.createAppt(a);
            actiontarget_2.setFill(Color.FIREBRICK);
            actiontarget_2.setText("Appointment created!");
            });
    }
    
    public void cancelAppointment() {
        /*
        Cancel Appointment
        - Delete appointment from 'database'
        */
        Stage stageOne = new Stage();
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene sceneOne = new Scene(grid, 700, 500);
        stageOne.setScene(sceneOne);
        stageOne.show();
        
        Text scenetitleOne = new Text("Cancel Appointment: ");
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitleOne, 1, 0, 2, 1);
        
    	Label title = new Label("Title of Appointment to Cancel:");
        grid.add(title, 0, 1);
        TextField titleTextField = new TextField();
        grid.add(titleTextField, 1, 1);
        
        Label date = new Label("Date of Appointment to Cancel:");
        grid.add(date, 0, 2);
        TextField dateTextField = new TextField();
        grid.add(dateTextField, 1, 2);
      
        
        Button cancelBtn = new Button("Cancel Appointment");
        HBox hbCancelBtn = new HBox(10);
        hbCancelBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbCancelBtn.getChildren().add(cancelBtn);
        grid.add(hbCancelBtn, 1, 3);
        
        final Text actiontarget_2 = new Text();
        grid.add(actiontarget_2, 1, 4);
        cancelBtn.setOnAction((ActionEvent e) -> {
            //            	newUser = new UserAccount();
            String ttl = titleTextField.getText();
            String dte = dateTextField.getText();
            appt.deleteAppt(ttl, dte);
            actiontarget_2.setFill(Color.FIREBRICK);
            actiontarget_2.setText("Appointment cancelled!");
            });
    }
    
    public void changeAppointment() {
        /*
        Change Appointment
        - Modify date and time/timeframe of selected appointment
        */
        
        Stage stageOne = new Stage();
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene sceneOne = new Scene(grid, 700, 500);
        stageOne.setScene(sceneOne);
        stageOne.show();
        
        Text scenetitleOne = new Text("Modify Appointment: ");
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitleOne, 1, 0, 2, 1);
        
    	Label title = new Label("Title of Appointment to Modify:");
        grid.add(title, 0, 1);
        TextField titleTextField = new TextField();
        grid.add(titleTextField, 1, 1);
        
        Label date = new Label("Date of Appointment to Modify:");
        grid.add(date, 0, 2);
        TextField dateTextField = new TextField();
        grid.add(dateTextField, 1, 2);
      
        Label ndate = new Label("New date:");
        grid.add(ndate, 0, 3);
        TextField ndateTextField = new TextField();
        grid.add(ndateTextField, 1, 3);
        
        Label newloc = new Label("New location:");
        grid.add(newloc, 0, 4);
        TextField newlocTextField = new TextField();
        grid.add(newlocTextField, 1, 4);
        
        Button updateBtn = new Button("Update Appointment");
        HBox hbUpdateBtn = new HBox(10);
        hbUpdateBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbUpdateBtn.getChildren().add(updateBtn);
        grid.add(hbUpdateBtn, 1, 5);
        
        final Text actiontarget_2 = new Text();
        grid.add(actiontarget_2, 1, 6);
        updateBtn.setOnAction((ActionEvent e) -> {
            String ttl = titleTextField.getText();
            String oldDate = dateTextField.getText();
            String newDate = ndateTextField.getText();
            String loc = newlocTextField.getText();
            appt.modifyAppt(ttl, oldDate, newDate, loc);
            
            actiontarget_2.setFill(Color.FIREBRICK);
            actiontarget_2.setText("Appointment updated!");
        });
    
    }

    
    public static void exportSchedule(List<Appointment> exportedApptList) throws IOException{
        /*
        Export user schedule to a file containing ?List? of user's appointments
        - Text file?
        - CSV file?
        */
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("exported_schedule.txt"));
            if (exportedApptList.isEmpty()) {
                String empty_string = "";
                bw.write(empty_string);
                bw.close();
            }
            else {
                for (int i = 0; i < exportedApptList.size(); i++) {
                    bw.write(exportedApptList.get(i) + "\n");
                }
            }
        }
        catch (IOException e)
        {
        }
    }
    
    public void importSchedule() {
        /*
        Import another user's schedule via a file
        */
        URL url = getClass().getResource("imported_schedule.txt");
        File file = new File(url.getPath());
        try {
            InputStream input = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SchedulingSystemApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> importedApptList = new ArrayList<String>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
            importedApptList.add(sc.nextLine());
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SchedulingSystemApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void showCreateAccountForm() {
    	Stage stageOne = new Stage();
    	GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Scene sceneOne = new Scene(grid, 700, 500);
        stageOne.setScene(sceneOne);
        stageOne.show();
        
        Text scenetitleOne = new Text("Create User Account");
        scenetitleOne.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitleOne, 1, 0, 2, 1);

        /*
         * Add labels to each row
         */
        Label username = new Label("Username:");
        grid.add(username, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        Label email = new Label("Email:");
        grid.add(email, 0, 3);
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 3);
        
        Label fname = new Label("First name:");
        grid.add(fname, 0, 4);
        TextField firstTextField = new TextField();
        grid.add(firstTextField, 1, 4);
        
        Label lname = new Label("Last name:");
        grid.add(lname, 0, 5);
        TextField lastTextField = new TextField();
        grid.add(lastTextField, 1, 5);
        
        // Add button
        Button createAccountBtn = new Button("Create Account");
        HBox hbCreateAccountBtn = new HBox(10);
        hbCreateAccountBtn.setAlignment(Pos.BOTTOM_LEFT);
        hbCreateAccountBtn.getChildren().add(createAccountBtn);
        grid.add(hbCreateAccountBtn, 1, 6);
        
        //Event Handler for CreateAccount button
        final Text actiontarget_2 = new Text();
        grid.add(actiontarget_2, 1, 7);
        createAccountBtn.setOnAction((ActionEvent e) -> {
            newUser = new UserAccount();
            newUser.setUsername(userTextField.getText());
            newUser.setPassword(pwBox.getText());
            newUser.setEmail(emailTextField.getText());
            newUser.setFirstName(firstTextField.getText());
            newUser.setLastName(lastTextField.getText());
            addUser();
            actiontarget_2.setFill(Color.FIREBRICK);
            actiontarget_2.setText("User Account created!");
            });
    }
    
    /*
     * Place Menu Bar here in Main Stage
     * Might move later
     * - Calendar view will probably be here too
     */
    public void showMainStage() {
    	Stage mainStage = new Stage();
    	mainStage.setTitle("Scheduling System Application");
//    	StackPane root = new StackPane();
    
    	// create a menu 
        Menu m = new Menu("Menu"); 
  
        // create menuitems 
        Menu acc = new Menu("Account"); 
        Menu appt = new Menu("Appointment"); 
        Menu cal = new Menu("Calendar"); 
        MenuItem clr = new MenuItem("Set Color"); 
        Menu sch = new Menu("Schedule");
        
        MenuItem createAcc = new MenuItem("Create Account");
        MenuItem changeUserPass = new MenuItem("Changer Username/Password");
        MenuItem modifyAcc = new MenuItem("Modify Account");
        acc.getItems().add(createAcc);
        acc.getItems().add(changeUserPass);
        acc.getItems().add(modifyAcc);
        
        MenuItem makeAppt = new MenuItem("Make Appointment");
        MenuItem cancelAppt = new MenuItem("Cancel Appointment");
        MenuItem changeAppt = new MenuItem("Change Appointment");
        appt.getItems().add(makeAppt);
        appt.getItems().add(cancelAppt);
        appt.getItems().add(changeAppt);
        
        Menu setCalRange = new Menu("Set Calendar Range");
        MenuItem day = new MenuItem("Day View");
        MenuItem week = new MenuItem("Week View");
        MenuItem month = new MenuItem("Month View");
        setCalRange.getItems().add(day);
        setCalRange.getItems().add(week);
        setCalRange.getItems().add(month);
        cal.getItems().add(setCalRange);
        
        MenuItem exportSchedule = new MenuItem("Export Schedule");
        MenuItem importSchedule = new MenuItem("Import Schedule");
        sch.getItems().add(exportSchedule);
        sch.getItems().add(importSchedule);

  
        // add menu items to menu 
        m.getItems().add(acc); 
        m.getItems().add(appt); 
        m.getItems().add(cal); 
        m.getItems().add(clr); 
        m.getItems().add(sch);
  
        createAcc.setOnAction(e -> {
            showCreateAccountForm();
        });
        
        changeUserPass.setOnAction(e -> {
            changeUserPass();
        });
        
        modifyAcc.setOnAction(e -> {
            modifyAccount();
        });
        
        
        day.setOnAction(e -> {
            DayView();
        });
        
        week.setOnAction(e -> {
            WeekView();
        });
        
        month.setOnAction(e -> { // month view
            MonthView();
        });
        
        makeAppt.setOnAction(e -> {
            makeAppointment();
        });
        
        cancelAppt.setOnAction(e -> {
            cancelAppointment();
        });
        
        changeAppt.setOnAction(e -> {
            changeAppointment();
        });
        
        exportSchedule.setOnAction((ActionEvent e) -> {
            //exportSchedule(appt.g);
        });
        
        importSchedule.setOnAction(e -> {
            importSchedule();
        });
  
        
        // create a menubar 
        MenuBar mb = new MenuBar(); 
  
        // add menu to menubar 
        mb.getMenus().add(m); 
  
        // create a VBox 
        VBox vb = new VBox(mb); 
 
        // set the scene 
//    	Scene mainScene = new Scene(root, 1500, 1000);
        Scene mainScene = new Scene(vb, 1500, 1000);
    	mainStage.setScene(mainScene);
    	mainStage.show();
    	
    	
    }
    public void MonthView() {
    	Stage calendarStage = new Stage();
    	calendarStage.setScene(new Scene(new FullCalendarView(YearMonth.now()).getView()));
    	calendarStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
