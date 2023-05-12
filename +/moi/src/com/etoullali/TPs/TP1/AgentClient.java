package com.etoullali.TPs.TP1;

import com.etoullali.TPs.TP2.AgentClientGUI;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentClient extends Agent {

    // Méthodes: cycle de vie d'un agent

    //=> exécuter avant le déploiement de l'agent
    @Override
    protected void setup() {
        System.out.println(" *** Client : La méthode setup ***");
        String arg1 = (String) getArguments()[0];
        String arg2 = (String) getArguments()[1];
        System.out.println(arg1 + " " + arg2);

        /**ACL Mesage **/
        ACLMessage message=new ACLMessage(ACLMessage.REQUEST); // ACT DE COMMUNICATION (Speech Act): donne un sens vers le message (objectif)
        message.setContent("Bonjour Serveur, j'ai besoin d'un service svp :)");
        message.addReceiver(new AID("server",AID.ISLOCALNAME)); //AID : Agent Identifier & AID.ISLOCALNAME : cad "server" est une nom local
        send(message);

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
}
