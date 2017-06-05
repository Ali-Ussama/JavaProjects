package view;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Model;
import javafx.scene.control.*;

public class View {

    private String[] DepList = new String[]{"Teaching", "Stuff", "Workers"};
    private String[] JobList = new String[]{"Head", "Doctor", "Doctor Assistant", "Security", "Clean Worker",};
    private String Name, Age, Address, Phone, ItemID;
    private int Salary, Job, Department, SSN;
    private SubmitEvent submitEvent;
    private SubmitListener mSubmitListener;
    private Stage newStage;
    private Scene sscene;
    private BorderPane root;
    private VBox VB1, VB2;
    private HBox HBLeft, HBBottom;
    private TextField TFName, TFAge, TFAddress, TFPhone, TFSSN, TFSalary;
    private ComboBox<String> DepartmentCobmo, JobCombo;
    private Label LblName, LblAge, LblAddress, LblPhone, LblJob, LblSalary, LblDp, LblSSN;
    private Button SubmitButton;

    public View(SubmitListener s) {
        this.mSubmitListener = s;
    }

    private int SearchForIndex(String value, String[] ar) {
        for (int i = 0; i < ar.length; i++) {
            if (value.matches(ar[i])) {
                return i + 1;
            }
        }
        return 0;
    }

    private String Query(String m) {
        String sql = "";
        if (m == "Add") {
            sql = "INSERT INTO worker (ssn ,name, address, phone, age , salary , job_id , dep_id)"
                    + "VALUES (" + SSN + ",'" + Name + "','" + Address + "','" + Phone + "','" + Age + "'," + Salary
                    + "," + Job + "," + Department + ")";
        } else if (m == "Update") {
            sql = "update worker set name = '" + Name + "', address = '" + Address + "', phone = '" + Phone
                    + "', age = '" + Age + "', salary = " + Salary + ", job_id = " + Job + ", dep_id = " + Department
                    + " where ssn = " + SSN;
        } else {
            sql = "delete from worker where ssn = " + SSN;
        }
        return sql;
    }

    private void Init() {

        newStage = new Stage();
        root = new BorderPane();
        sscene = new Scene(root, 300, 350);
        //ComboBox of Department
        DepartmentCobmo = new ComboBox<>();
        DepartmentCobmo.getItems().addAll(DepList);
        //ComboBox of Job
        JobCombo = new ComboBox<>();
        JobCombo.getItems().addAll(JobList);
        //Layouts
        HBBottom = new HBox(10);
        HBLeft = new HBox(10);
        VB1 = new VBox(19);
        VB2 = new VBox(10);
        //Labels
        LblSSN = new Label("SSN");
        LblName = new Label("Name");
        LblAge = new Label("Age");
        LblAddress = new Label("Address");
        LblPhone = new Label("Phone");
        LblJob = new Label("Job");
        LblDp = new Label("Department");
        LblSalary = new Label("Salary");
        //Text Fields
        TFName = new TextField();
        TFAge = new TextField();
        TFAddress = new TextField();
        TFPhone = new TextField();
        TFSSN = new TextField();
        TFSalary = new TextField();
        //Submit Button.
        SubmitButton = new Button("Submit");
        SubmitButton.setPrefSize(100, 30);
        //Submit Button ActionEvent Class.
        submitEvent = new SubmitEvent();

    }

    public void Start(String id) {
        try {
            //Initialize the variables.
            Init();
            //initialize the id of MenuItem
            ItemID = id;
            if (ItemID == "Delete") {
                TFName.setVisible(false);
                TFAge.setVisible(false);
                TFAddress.setVisible(false);
                TFPhone.setVisible(false);
                TFSalary.setVisible(false);
                JobCombo.setVisible(false);
                DepartmentCobmo.setVisible(false);
                LblName.setVisible(false);
                LblAge.setVisible(false);
                LblAddress.setVisible(false);
                LblPhone.setVisible(false);
                LblSalary.setVisible(false);
                LblJob.setVisible(false);
                LblDp.setVisible(false);
            }

            //Adding Components to Layouts.
            VB1.getChildren().addAll(LblSSN, LblName, LblAge, LblAddress, LblPhone, LblSalary, LblJob, LblDp);
            VB2.getChildren().addAll(TFSSN, TFName, TFAge, TFAddress, TFPhone, TFSalary, JobCombo, DepartmentCobmo);
            HBLeft.getChildren().addAll(VB1, VB2);
            HBLeft.setPadding(new Insets(10, 10, 10, 10));
            HBBottom.getChildren().addAll(SubmitButton);
            HBBottom.setPadding(new Insets(5, 10, 200, 200));

            // Adding Layouts to Pane
            root.setLeft(HBLeft);
            root.setBottom(HBBottom);
            newStage.setScene(sscene);
            newStage.setTitle("Faculty management");
            newStage.show();

            //Button Action
            SubmitButton.setOnAction(submitEvent);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    class SubmitEvent implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            
            
            
            try {
                SSN = Integer.parseInt(TFSSN.getText());
                if (ItemID != "Delete") {
                    Name = TFName.getText();
                    Age = TFAge.getText();
                    Address = TFAddress.getText();
                    Phone = TFPhone.getText();
                    Salary = Integer.parseInt(TFSalary.getText());
                    Department = SearchForIndex(DepartmentCobmo.getValue(), DepList);
                    Job = SearchForIndex(JobCombo.getValue(), JobList);
                }
                Model.statement.executeUpdate(Query(ItemID));
//                JOptionPane.showMessageDialog(null, "Successful");
//                newStage.close();
                //refresh the table.
                mSubmitListener.onSubmitListener();
            } catch (SQLException e) {
//                e.printStackTrace(); 
            } catch (Exception e) {
               // e.printStackTrace();
                JOptionPane.showMessageDialog(null, "ReEnter The Data Correctly... ");
                TFSSN.setText("");
                TFName.setText("");
                TFAge.setText("");
                TFPhone.setText("");
                TFSalary.setText("");
                TFAddress.setText("");
                
            }
        }
    }
}
