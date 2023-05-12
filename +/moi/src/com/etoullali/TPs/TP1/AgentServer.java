package com.etoullali.TPs.TP1;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentServer extends Agent {

    // Méthodes: cycle de vie d'un agent

    //=> exécuter avant le déploiement de l'agent
    @Override
    protected void setup() {
        System.out.println(" *** Server : La méthode setup ***");
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receive = blockingReceive(); // receive()
                boolean termine = true;
                while (termine) {
                    if (receive != null) {
                        System.out.println("Message de " + receive.getSender().getName() + " : " + receive.getContent());
                        termine = false;
                    } else {
                        block(); //block le programme jusqu'à reçu un message (Listener)
                    }
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
}
