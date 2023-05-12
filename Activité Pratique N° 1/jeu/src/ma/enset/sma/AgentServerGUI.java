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

public class AgentServerGUI extends Application {
    private AgentServer agentServer; //réf de Agent server
    private ObservableList<String> data= FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    } //créer l'interface + implémente la méthode start()
    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        primaryStage.setTitle("Agent Server");

        BorderPane root=new BorderPane();
        Label labelMsg=new Label("Nombre");
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
            agentServer.onGuiEvent(guiEvent); //envoyer cette Event
        });
    }

    private void startContainer() throws Exception {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost"); //préciser quelque param : MainContainer address (local)
        AgentContainer container=runtime.createAgentContainer(profile);
        AgentController agentClient = container.createNewAgent("server", "ma.enset.sma.AgentServer", new Object[]{this,"server"});
        agentClient.start();
    }

    public void setAgentServer(AgentServer agentServer) {
        this.agentServer = agentServer;
    }

    public void showMessage(String message) {
        Platform.runLater(()->{
            //j'autorise l'exécution de cette instruction à partir d'un autre thread à l'extérieur d'un interface graphique
            data.add(message);
        });
    }

}
