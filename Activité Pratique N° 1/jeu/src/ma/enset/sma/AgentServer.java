package ma.enset.sma;

import jade.core.AID;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class AgentServer extends GuiAgent {
    private AgentServerGUI serverGUI; //point de liaison (réf de Graphique Interface): Server <-> Graphique Interface

    // Méthodes: cycle de vie d'un agent

    //=> exécuter avant le déploiement de l'agent
    @Override
    protected void setup() {

        System.out.println(" *** Server : La méthode setup ***");

        int secret = (int) (Math.random() * 100);
        System.out.println("Secret number = " + secret);
        //=> initialiser
        serverGUI = (AgentServerGUI) getArguments()[0];
        serverGUI.setAgentServer(this);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receiveMsg = blockingReceive(); // receive()
                /**Receive**/
                String messageRecu = receiveMsg.getContent();
//                String player=(String) getArguments()[1];
                String player= String.valueOf(receiveMsg.getSender().getName().split("@")[0]);
                System.out.println("Player : "+player);
                int nbr=Integer.parseInt(messageRecu);
                if (receiveMsg != null) {
                    if (nbr==secret) {
                        serverGUI.showMessage("<< "+"bravo "+player+" !!");
                        System.out.println("The number secret exist : "+nbr);
                        /**Send to player**/
                        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
                        message.addReceiver(new AID("player1",AID.ISLOCALNAME));
                        message.addReceiver(new AID("player2",AID.ISLOCALNAME));
                        message.setContent("bravo "+player+" !!");
                        send(message);
                        // I can add : your win & game over ...
                        // stop program !!
                    } else if (nbr > secret) {
                        System.out.println(nbr);
                        /**Send to player**/
                        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
                        message.addReceiver(new AID(player,AID.ISLOCALNAME));
                        message.setContent("your number greater than the secret number");
                        send(message);
                    } else if (nbr < secret) {
                        System.out.println(nbr);
                        /**Send to player**/
                        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
                        message.addReceiver(new AID(player,AID.ISLOCALNAME));
                        message.setContent("your number less than the secret number");
                        send(message);
                    } else {
//                        serverGUI.showMessage("<< "+"write a number please");
                        System.out.println(nbr);
                        /**Send to player**/
                        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
                        message.addReceiver(new AID(player,AID.ISLOCALNAME));
                        message.setContent("write a number please");
                        send(message);
                    }
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
        String parameter = (String) guiEvent.getParameter(0);
        System.out.println(parameter); //afficher le message dans AgentClientGUI
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        //=> send to players
        message.addReceiver(new AID("player1", AID.ISLOCALNAME));
        message.addReceiver(new AID("player2", AID.ISLOCALNAME));
        message.setContent(parameter);
        send(message);
    }
}
