package ma.enset.sma;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
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

public class AgentServerGui extends Application {
    private AgentServer agentServer;
    ObservableList<String> data= FXCollections.observableArrayList();
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        primaryStage.setTitle("Agent Server");
        BorderPane root=new BorderPane();
        Label labelMsg=new Label("Message :");
        TextField textFieldMsg=new TextField();
        Button buttonSend=new Button("Envoyer");
        HBox hBox=new HBox(labelMsg,textFieldMsg,buttonSend);

        ListView<String> listView=new ListView<>(data);
        root.setBottom(hBox);
        root.setCenter(listView);
        Scene scene=new Scene(root,400,200);
        primaryStage.setScene(scene);
        primaryStage.show();
        buttonSend.setOnAction(event -> {
            GuiEvent guiEvent=new GuiEvent(this,1);
            guiEvent.addParameter(textFieldMsg.getText());
            data.add("==>>"+textFieldMsg.getText());
            textFieldMsg.setText("");
            agentServer.onGuiEvent(guiEvent);
        });
    }
    private void startContainer() throws StaleProxyException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(ProfileImpl.MAIN_HOST,"localhost");
        AgentContainer container=runtime.createAgentContainer(profile);
        AgentController agentController=container.createNewAgent("server","ma.enset.sma.AgentServer",new Object[]{this});
        agentController.start();
    }

    public void setAgentServer(AgentServer agentServer) {
        this.agentServer = agentServer;
    }
    public void showMessage(String message){
        Platform.runLater(()->{
            data.add(message);
        });

    }
}
