package com.etoullali.mas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MasterAgentGUI extends Application {
    MasterAgent masterAgent= new MasterAgent();
    String grid="                {0,  -1,  0,  0,  0},\n" +
                "                {0,   0,  0, -1,  0},\n" +
                "                {-1, -1, -1,  0,  0},\n" +
                "                {-1,  1, -1,  0, -1},\n" +
                "                {-1,  0,  0,  0, -1}";
    String listAgents="agent1 *** (0,0)\t[[Bas, Droit, Droit, Haut, Droit, Droit, Bas, Bas, Gauche, Bas, Bas, Gauche, Gauche, Haut]]\tsize : 14\n" +
            "agent2 *** (1,1)\t[[Droit, Haut, Droit, Droit, Bas, Bas, Gauche, Bas, Bas, Gauche, Gauche, Haut]]\tsize : 12\n" +
            "agent3 *** (1,0)\t[[Droit, Droit, Haut, Droit, Droit, Bas, Bas, Gauche, Bas, Bas, Gauche, Gauche, Haut]]\tsize : 13\n" +
            "agent4 *** (1,0)\t[[Droit, Droit, Haut, Droit, Droit, Bas, Bas, Gauche, Bas, Bas, Gauche, Gauche, Haut]]\tsize : 13\n" +
            "agent5 *** (1,2)\t[[Haut, Droit, Droit, Bas, Bas, Gauche, Bas, Bas, Gauche, Gauche, Haut]]\tsize : 11\n";

    String path="11";
    String agent="5";
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Master Agent");

        BorderPane root=new BorderPane();

        Label labelGrid=new Label("Grid");
        TextArea textAreaGrid=new TextArea();
        Label labelAgents=new Label("Agents");
        TextArea textAreaAgents=new TextArea();
        Label labelPath=new Label("Optimal Path");
        TextField textFieldPath=new TextField();
        Label labelAgent=new Label("Agent");
        TextField textFieldAgent=new TextField();

        textAreaGrid.setText(grid);
        textAreaAgents.setText(listAgents);
        textFieldPath.setText(path);
        textFieldAgent.setText(agent);


        VBox vBox=new VBox(labelGrid,textAreaGrid,labelAgents,textAreaAgents,labelPath,textFieldPath,labelAgent,textFieldAgent);

        root.setCenter(vBox);

        Scene scene=new Scene(root,600,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
