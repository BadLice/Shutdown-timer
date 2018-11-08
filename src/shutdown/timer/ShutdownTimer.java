
package shutdown.timer;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ShutdownTimer extends Application {
    
    static Countdown timer;
    static String startStr="start.png";
    static String stopStr="stop.png";
    static boolean state = true;
    static Timer t;
    static TimerTask updateTask;
    static ComboBox option;
    
    
    @Override
    public void start(Stage primaryStage) 
    {
        timer = new Countdown();
        
        Spinner hourSpin = new Spinner();
        hourSpin.setLayoutX(10);hourSpin.setLayoutY(10);
        hourSpin.setPrefWidth(100);
        hourSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,Integer.MAX_VALUE,0));
        
        
        Spinner minSpin = new Spinner();
        minSpin.setLayoutX(120);minSpin.setLayoutY(10);
        minSpin.setPrefWidth(100);
        minSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60,0));
        
        Spinner secSpin = new Spinner();
        secSpin.setLayoutX(230);secSpin.setLayoutY(10);
        secSpin.setPrefWidth(100);
        secSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,60,0));
        
        option = new ComboBox();
        option.setLayoutX(400);option.setLayoutY(10);
        option.getItems().addAll("Shutdown","Logoff","Sleep","Lock","Hibernate","Reboot");
        option.getSelectionModel().select(0);
        
        Label hourLabel = new Label("Hours");
        hourLabel.setLayoutX(35);hourLabel.setLayoutY(40);
        
        Label minLabel = new Label("Minutes");
        minLabel.setLayoutX(135);minLabel.setLayoutY(40);
        
        Label secLabel = new Label("Seconds");
        secLabel.setLayoutX(245);secLabel.setLayoutY(40);
        
        Button timerBtn = new Button();
        timerBtn.setPadding(Insets.EMPTY);
        ImageView img = new ImageView(new Image(startStr));
        img.setFitWidth(30);
        img.setFitHeight(30);
        timerBtn.setGraphic(img);
        timerBtn.setLayoutX(340);timerBtn.setLayoutY(10);
        timerBtn.setOnAction(e ->
        {
            Button btn = (Button) e.getSource();
            
            
            if (state)
            {
                timer.start(Countdown.calcInterval(""+hourSpin.getValue(),""+minSpin.getValue(),""+secSpin.getValue()));
                t = new Timer();
                updateTask = new TimerTask()
                {
                    public synchronized void run()
                    {
                        
                      hourSpin.getValueFactory().setValue(timer.getHours());
                      minSpin.getValueFactory().setValue(timer.getMinutes());
                      secSpin.getValueFactory().setValue(timer.getSeconds());
                    }
                };
                t.scheduleAtFixedRate(updateTask, 0, 200);
                hourSpin.setDisable(true);
                minSpin.setDisable(true);
                secSpin.setDisable(true);
            }
            else
            {
                synchronized(this)
                {
                    timer.stop();
                    t.cancel();
                    updateTask.cancel();
                    hourSpin.setDisable(false);
                    minSpin.setDisable(false);
                    secSpin.setDisable(false);
                }
                
            }
            
            if(state)
                img.setImage(new Image(stopStr));
            else
                  img.setImage(new Image(startStr));
            state = !state;
        });
        
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #aaaaaa;");
        root.getChildren().addAll(hourSpin,minSpin,secSpin,hourLabel,minLabel,secLabel,timerBtn,option);
        
        Scene scene = new Scene(root, 530, 70);
        scene.getStylesheets().add("style.css");
        
        primaryStage.setTitle("Shutdown Timer");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("start.png"));
        primaryStage.setOnCloseRequest(e ->
        {
            
            timer.stop();
            if (!state)
            {
                t.cancel();
                updateTask.cancel();
            }
                
        });
        primaryStage.show();
        
        
    }
    
    static void exec() 
    {
        switch(option.getSelectionModel().getSelectedIndex())
        {
            case 0: Session.shutdown();
                break;
            case 1: Session.logoff();
                break;
            case 2: Session.sleep();
                break;
            case 3: Session.lock();
                break;
            case 4: Session.hibernate();
                break;
            case 5: Session.reboot();
                break;
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
    

}
