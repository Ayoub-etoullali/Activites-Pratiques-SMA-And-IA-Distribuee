import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Seller1AgentGUI extends Application {
    private Seller1Agent seller1Agent1;
    private ObservableList<String> buyers= FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        primaryStage.setTitle("Seller 1");

        BorderPane root=new BorderPane();
        Label labelDesc=new Label("Description");
        TextField textFieldDesc=new TextField();
        Button buttonConf=new Button("Confirm");

        HBox hBox=new HBox(labelDesc,textFieldDesc,buttonConf);

        ListView<String> listView=new ListView<>(buyers);

        root.setCenter(listView);
        root.setTop(hBox);

        Scene scene=new Scene(root,400,200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startContainer() throws Exception {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container=runtime.createAgentContainer(profile);
        AgentController agentSeller1 = container.createNewAgent("seller1", Seller1Agent.class.getName(), new Object[]{this,"seller1"});
        agentSeller1.start();
    }
}
