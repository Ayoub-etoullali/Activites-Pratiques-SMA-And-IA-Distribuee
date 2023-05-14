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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuyerAgentGUI extends Application {
    private BuyerAgent buyerAgent;
    Seller1AgentGUI seller1AgentGUI;
    private ObservableList<String> service = FXCollections.observableArrayList();
    private ObservableList<String> message = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        primaryStage.setTitle("Buyer");

        BorderPane root = new BorderPane();
        TextField textFieldSearch = new TextField();
        Button buttonSearch = new Button("Search");
        Button buttonCommand = new Button("command");
        Button buttonBuy = new Button("Buy");

        Label labelServices = new Label("Services");
        ListView<String> services = new ListView<>(service);
        Label labelMsg = new Label("Messages");
        ListView<String> messages = new ListView<>(message);

        HBox hBox = new HBox(textFieldSearch, buttonSearch, buttonCommand,buttonBuy);
        VBox vBox = new VBox(labelServices, services, labelMsg, messages);

        root.setCenter(vBox);
        root.setTop(hBox);

        Scene scene = new Scene(root, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        /**Add in my list**/
//        service=seller1AgentGUI.getService();
        System.out.println(service);
//        System.out.println(seller1AgentGUI.getService());

        buttonSearch.setOnAction(event -> {
            GuiEvent guiEvent = new GuiEvent(this, 1); //créer un Event
            guiEvent.addParameter(textFieldSearch.getText());
            textFieldSearch.setText("");
            /**CFP**/
            buyerAgent.onGuiEvent(guiEvent);
        });

        buttonCommand.setOnAction(event -> {
            /**Propose**/
            buyerAgent.commande();
        });

        buttonBuy.setOnAction(event -> {
            /**Propose**/
            buyerAgent.buy();
        });
    }

    public void setBuyerAgent(BuyerAgent buyerAgent) {
        this.buyerAgent = buyerAgent;
    }

    private void startContainer() throws Exception {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter("host", "localhost");
        AgentContainer container = runtime.createAgentContainer(profile);
        AgentController agentClient = container.createNewAgent("buyer", BuyerAgent.class.getName(), new Object[]{this, "buyer"});
        agentClient.start();
    }

    public void showMessage(String msg) {
        Platform.runLater(() -> {
            //j'autorise l'exécution de cette instruction à partir d'un autre thread à l'extérieur d'un interface graphique
            message.add(msg);
        });
    }

    public void showServices(String s) {
        Platform.runLater(() -> {
            //j'autorise l'exécution de cette instruction à partir d'un autre thread à l'extérieur d'un interface graphique
            service.add(s);
        });
    }
}
