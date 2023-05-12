package com.etoullali.TPs.TP2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class AgentServer extends GuiAgent {
    private AgentServerGUI serverGUI; //point de liaison (réf de Graphique Interface): Server <-> Graphique Interface

    // Méthodes: cycle de vie d'un agent

    //=> exécuter avant le déploiement de l'agent
    @Override
    protected void setup() {
        System.out.println(" *** Server : La méthode setup ***");
        //=> initialiser
        serverGUI = (AgentServerGUI) getArguments()[0];
        serverGUI.setAgentServer(this);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receiveMsg = blockingReceive(); // receive()
                if (receiveMsg != null) {
                    serverGUI.showMessage("<< "+receiveMsg.getContent());
                    System.out.println(receiveMsg.getContent());
                } else {
                    block(); //block le programme jusqu'à reçu un message (Listener)
                }
            }
        });

    }

    @Override
    protected void beforeMove() {
        System.out.println(" *** Server : La méthode beforeMove ***");
    }

    @Override
    protected void afterMove() {
        System.out.println(" *** Server : La méthode afterMove ***");
    }

    //=> avant tuer un agent
    @Override
    protected void takeDown() {
        System.out.println(" *** Server : La méthode takeDown ***");
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        String parameter =(String) guiEvent.getParameter(0);
        System.out.println(parameter); //afficher le message dans AgentClientGUI
        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID("client",AID.ISLOCALNAME));
        message.setContent(parameter); // envoyer 1 param => auto indice 0
        send(message);
    }
}
