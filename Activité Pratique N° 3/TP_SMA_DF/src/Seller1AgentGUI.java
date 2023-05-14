import jade.core.AID;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Seller1AgentGUI extends Application {
    private Seller1Agent seller1Agent1;
    private ObservableList<String> service= FXCollections.observableArrayList();
    private ObservableList<String> message= FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        primaryStage.setTitle("Seller 1");

        BorderPane root=new BorderPane();
        TextField textFieldAdd=new TextField();
        Button buttonAdd=new Button("Add Service");
        Button buttonConf=new Button("Confirm");
        Label labelServices=new Label("Services");
        Label labelMessages=new Label("Messages");
        ListView<String> services=new ListView<>(service);
        ListView<String> messages=new ListView<>(message);

        HBox hBox=new HBox(textFieldAdd,buttonAdd,buttonConf);
        VBox vBox=new VBox(labelServices,services,labelMessages,messages);

        root.setCenter(vBox);
        root.setTop(hBox);

        Scene scene=new Scene(root,400,200);
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonAdd.setOnAction(event -> {
            GuiEvent guiEvent=new GuiEvent(this,1); //créer un Event
            guiEvent.addParameter(textFieldAdd.getText());
            /**Add in my list**/
            service.add("- "+textFieldAdd.getText());
            textFieldAdd.setText("");
            /**Send services**/
            seller1Agent1.onGuiEvent(guiEvent); //envoyer cette Event
        });

        buttonConf.setOnAction(event -> {
            textFieldAdd.setText("");
            /**Send**/
            seller1Agent1.conf(); //envoyer cette Event
        });
    }

    public ObservableList<String> getService() {
        return service;
    }

    private void startContainer() throws Exception {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost");
        AgentContainer container=runtime.createAgentContainer(profile);
        AgentController agentSeller1 = container.createNewAgent("seller1", Seller1Agent.class.getName(), new Object[]{this,"seller1","15000"});
        agentSeller1.start();
    }


    public void setSeller1Agent1(Seller1Agent seller1Agent1) {
        this.seller1Agent1 = seller1Agent1;
    }

    public void showMessage(String sender) {
        Platform.runLater(() -> {
            //j'autorise l'exécution de cette instruction à partir d'un autre thread à l'extérieur d'un interface graphique
            message.add(sender);
        });
    }

    public Seller1Agent getSeller1Agent1() {
        return seller1Agent1;
    }
}
