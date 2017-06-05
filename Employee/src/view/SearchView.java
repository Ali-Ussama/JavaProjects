package view;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchView {

    private Stage SearchStage;
    private Scene Ssene;
    private BorderPane root;
    private RadioButton RadioName, RadioSSN, RadioAge, RadioSalary, RadioDepartment, RadioJob, RadioAddress, RadioPhone;
    private TextField TFInput;
    private VBox RadioVBox1, RadioVBox2, BottomVBox;
    private HBox RadioHbox, SubmitBtnHBox, InputTFHBox;
    private Button SubmitButton;
    private ToggleGroup group;
    private SubmitListener mSubmitListener;

    public SearchView(SubmitListener s) {
        this.mSubmitListener = s;
    }

    private void Init() {
        //BorderPane.
        root = new BorderPane();
        //Scene
        Ssene = new Scene(root, 300, 290);
        // Stage
        SearchStage = new Stage();
        SearchStage.setScene(Ssene);
        //Toggle Group
        group = new ToggleGroup();
        // Radio Buttons
        RadioName = new RadioButton("Name");
        RadioSSN = new RadioButton("SSN");
        RadioSalary = new RadioButton("Salary");
        RadioJob = new RadioButton("Job");
        RadioAge = new RadioButton("Age");
        RadioDepartment = new RadioButton("Department");
        RadioAddress = new RadioButton("Address");
        RadioPhone = new RadioButton("Phone");
        //Input text Field
        TFInput = new TextField();
        //Button
        SubmitButton = new Button("Submit");
        //initialize Layouts.
        RadioVBox1 = new VBox(20);
        RadioVBox2 = new VBox(20);
        RadioHbox = new HBox(20);
        BottomVBox = new VBox(20);
        SubmitBtnHBox = new HBox(20);
        InputTFHBox = new HBox(20);
        //Layouts BottomVBox , RadioVBox1,2 and RadioHBox Padding.
        BottomVBox.setPadding(new Insets(10, 0, 130, 0));
        InputTFHBox.setPadding(new Insets(20, 10, 10, 75));
        SubmitBtnHBox.setPadding(new Insets(0, 0, 0, 230));
        RadioVBox1.setPadding(new Insets(20, 20, 20, 20));
        RadioVBox2.setPadding(new Insets(20, 20, 20, 70));
        RadioHbox.setPadding(new Insets(0, 0, 0, 10));
    }

    private void RadioToggleGroup() {
        RadioSSN.setToggleGroup(group);
        RadioName.setToggleGroup(group);
        RadioAge.setToggleGroup(group);
        RadioPhone.setToggleGroup(group);
        RadioAddress.setToggleGroup(group);
        RadioSalary.setToggleGroup(group);
        RadioJob.setToggleGroup(group);
        RadioDepartment.setToggleGroup(group);
    }

    private void AddComponents() {
        RadioVBox1.getChildren().addAll(RadioSSN, RadioName, RadioAge, RadioPhone);
        RadioVBox2.getChildren().addAll(RadioAddress, RadioSalary, RadioJob, RadioDepartment);
        RadioHbox.getChildren().addAll(RadioVBox1, RadioVBox2);
        SubmitBtnHBox.getChildren().add(SubmitButton);
        InputTFHBox.getChildren().add(TFInput);
        BottomVBox.getChildren().addAll(InputTFHBox, SubmitBtnHBox);

    }

    private String CreateQuery() {
        String StrInput;
        int IntInput;
        
        try{
        if (RadioSSN.isSelected()) {
            try{
            IntInput = Integer.parseInt(TFInput.getText());
            return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
                    + "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id and worker.ssn = " + IntInput;
        
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null,"Re-Enter SSN Correctly ");
            }
            } else if (RadioName.isSelected()) {
            StrInput = TFInput.getText();
            return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
                    + "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id and worker.name = '" + StrInput + "'";
        } else if (RadioPhone.isSelected()) {
            try{
            StrInput = TFInput.getText();
            return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
                    + "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id and worker.phone ='" + StrInput + "'";
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null,"Re-Enter Phone Correctly ");
            }
            
            } else if (RadioAddress.isSelected()) {
            StrInput = TFInput.getText();
            return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
                    + "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id and worker.Address = '" + StrInput + "'";
        } else if (RadioJob.isSelected()) {
            StrInput = TFInput.getText();
            return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
                    + "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id and job.name = '" + StrInput + "'";
        } else if (RadioDepartment.isSelected()) {
            StrInput = TFInput.getText();
            return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
                    + "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id and department.name = '" + StrInput + "'";
        } else if (RadioAge.isSelected()) {
            StrInput = TFInput.getText();
            return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
                    + "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id and worker.age = '" + StrInput + "'";
        } else if (RadioSalary.isSelected()) {
            try{
            IntInput = Integer.parseInt(TFInput.getText());
            return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
                    + "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id and worker.salary = " + IntInput;
        
            }catch(Exception e){
                  JOptionPane.showMessageDialog(null,"Re-Enter Salary Correctly ");
            }
            }else{
            	 JOptionPane.showMessageDialog(null,"Re-Enter Data Correctly ");
            	return "select worker.id ,worker.ssn,worker.name,worker.address,worker.phone,worker.age,worker.salary,job.name ,department.name "
        				+ "from worker,department,job where worker.dep_id = department.dep_id and worker.job_id = job.job_id order by worker.ssn";
            }
        
        }catch(Exception e){
            
            JOptionPane.showMessageDialog(null,"Re-Enter Data Correctly ");
        }
        return null;
    }

    public void start() {

        //Initialize the Components.
        Init();
        //Toggle Group.
        RadioToggleGroup();
        // Add Components on Layouts.
        AddComponents();

        root.setCenter(RadioHbox);
        root.setBottom(BottomVBox);
        SearchStage.show();
        SubmitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
            	
                mSubmitListener.onSubmitListener(CreateQuery());
                JOptionPane.showMessageDialog(null, "Successful");
                SearchStage.close();
            }
        });
    }

}
