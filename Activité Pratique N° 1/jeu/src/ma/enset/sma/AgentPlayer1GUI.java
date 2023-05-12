package ma.enset.sma;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.application.Platform;
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

public class AgentPlayer1GUI extends Application {
    private AgentPlayer1 agentPlayer1; //réf de Agent client
    private ObservableList<String> data= FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    } //créer l'interface + implémente la méthode start()
    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        primaryStage.setTitle("Agent Player 1");

        BorderPane root=new BorderPane();
        Label labelMsg=new Label("Message");
        TextField textFieldMsg=new TextField();
        Button buttonSend=new Button("Envoyer");

        HBox hBox=new HBox(labelMsg,textFieldMsg,buttonSend);

        ListView<String> listView=new ListView<>(data);

        root.setCenter(listView);
        root.setBottom(hBox);

        Scene scene=new Scene(root,400,200);
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonSend.setOnAction(event -> {
            GuiEvent guiEvent=new GuiEvent(this,1); //créer un Event
            guiEvent.addParameter(textFieldMsg.getText());
            /**Add in my list**/
            data.add(">> "+textFieldMsg.getText());
            textFieldMsg.setText("");
            /**Send**/
            agentPlayer1.onGuiEvent(guiEvent); //envoyer cette Event
        });
    }

    private void startContainer() throws Exception {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost"); //préciser quelque param : MainContainer address (local)
        AgentContainer container=runtime.createAgentContainer(profile);
        AgentController agentClient = container.createNewAgent("player1", "ma.enset.sma.AgentPlayer1", new Object[]{this,"player1"});
        agentClient.start();
    }

    public void setAgentClient(AgentPlayer1 agentPlayer1) {
        this.agentPlayer1 = agentPlayer1;
    }

    public void showMessage(String message) {
        Platform.runLater(()->{
            //j'autorise l'exécution de cette instruction à partir d'un autre thread à l'extérieur d'un interface graphique
            data.add(message);
        });
    }
}
