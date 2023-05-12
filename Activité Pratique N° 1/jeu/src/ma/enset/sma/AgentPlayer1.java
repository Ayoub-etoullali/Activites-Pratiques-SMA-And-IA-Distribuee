package ma.enset.sma;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class AgentPlayer1 extends GuiAgent { // Agent a une GI
    private AgentPlayer1GUI agentPlayer1GUI; //point de liaison (réf de Graphique Interface): User <-> Graphique Interface

    // Méthodes: cycle de vie d'un agent

    //=> exécuter avant le déploiement de l'agent
    @Override
    protected void setup() {
        System.out.println(" *** Client : La méthode setup ***");
        //=> initialiser
        agentPlayer1GUI = (AgentPlayer1GUI) getArguments()[0];
        agentPlayer1GUI.setAgentClient(this);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                /**Receive**/
                ACLMessage receiveMsg = blockingReceive(); // receive()
                    if (receiveMsg != null) {
                        agentPlayer1GUI.showMessage("<< "+receiveMsg.getContent());
                        System.out.println(receiveMsg.getContent());
                    } else {
                        block(); //block le programme jusqu'à reçu un message (Listener)
                    }
            }
        });

        /**ACL Mesage **/
        /*
        ACLMessage message=new ACLMessage(ACLMessage.REQUEST); // ACT DE COMMUNICATION (Speech Act): donne un sens vers le message (objectif)
        message.setContent("Bonjour Serveur, j'ai besoin d'un service svp :)");
        message.addReceiver(new AID("server",AID.ISLOCALNAME)); //AID : Agent Identifier & AID.ISLOCALNAME : c'est server (Container-2)
        send(message);
         */

        /** Behaviour **/
        /*
        //=> Generic Behaviour
        addBehaviour(new Behaviour() {
            private  int conteur;
            @Override
            public void action() {
                System.out.println("***** Conteur "+conteur+" *****");
                conteur++;
            }

            @Override
            public boolean done() {
                return conteur==100?true:false;
            }
        });
         */

        /*
        //=> OneShotBehaviour : exécuté une seule fois
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("***** One Shot Behaviour *****");
            }
        });
         */

        /*
        //=> CyclicBehaviour : exécuté d'une manière infini
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("***** Cyclic Behaviour *****");
            }
        });
         */

        /*
        //=> WakerBehaviour : exécuter juste une période et terminé
        addBehaviour(new WakerBehaviour(this,10000) { //this: actual agent

            @Override
            protected void onWake() {
                System.out.println("***** Waker Behaviour *****");
            }
        });
         */

        /*
        //=> TickerBehaviour : Waker + cyclic
        addBehaviour(new TickerBehaviour(this,10000) { //this: actual agent
            @Override
            protected void onTick() {
                System.out.println("***** Ticker Behaviour *****");
            }
        });
         */

        /*
        //=> ParallelBehaviour :
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("***** Cyclic Behaviour 1 *****");
            }
        });
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("***** Cyclic Behaviour 2 *****");
            }
        });
        addBehaviour(parallelBehaviour);
         */
    }

    @Override
    protected void beforeMove() {
        System.out.println(" *** Client : La méthode beforeMove ***");
    }

    @Override
    protected void afterMove() {
        System.out.println(" *** Client : La méthode afterMove ***");
    }

    //=> avant tuer un agent
    @Override
    protected void takeDown() {
        System.out.println(" *** Client : La méthode takeDown ***");
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        /*
        onGuiEvent() : va faire le traitement qui répondre à un évènement déclencher au niveau de l'interface graphique
        guiEvent : les données qui va envoyer au niveau de l'interface graphique (données de l'évènement)
         */
        String parameter =(String) guiEvent.getParameter(0);
        System.out.println(parameter); //afficher le message dans AgentClientGUI
        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID("server",AID.ISLOCALNAME));
        message.setContent(parameter); // envoyer 1 param => auto indice 0
        send(message);
    }
}
