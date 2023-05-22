import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class BuyerAgent extends Agent {
    DFAgentDescription[] dfAgentDescriptions; //info about all services
    private AID bestSeller;
    private double bestPrice = Double.MAX_VALUE;
    int cpt = 0;

    @Override
    protected void setup() {
//        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                DFAgentDescription dfAgentDescription = new DFAgentDescription();
                ServiceDescription serviceDescription = new ServiceDescription();
                serviceDescription.setType("pc"); //les services de type "pc"
                dfAgentDescription.addServices(serviceDescription);
                try {
                    dfAgentDescriptions = DFService.search(myAgent, dfAgentDescription);
                    System.out.println(dfAgentDescriptions.length);
                    for (DFAgentDescription agentDescription : dfAgentDescriptions) {
                        AID sellerAID = agentDescription.getName();
                        ACLMessage aclMessage = new ACLMessage(ACLMessage.CFP); //CFP : Call For Proposer
                        aclMessage.setContent("Can give the price of PCs");
                        aclMessage.addReceiver(sellerAID);
                        send(aclMessage);
                    }
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMsg = receive();
                if (receivedMsg != null) {
                    switch (receivedMsg.getPerformative()) {
                        case ACLMessage.PROPOSE: {
                            cpt++;
                            double price = Double.parseDouble(receivedMsg.getContent());
                            if (price < bestPrice) {
                                bestPrice = price;
                                bestSeller = receivedMsg.getSender();
                            }
                            if (cpt == dfAgentDescriptions.length) {
                                ACLMessage aclMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                                aclMessage.setContent("I accept your proposed price !!");
                                aclMessage.addReceiver(bestSeller);
                                send(aclMessage);
                            }
                            break;
                        }
                        case ACLMessage.AGREE: {
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
                            aclMessage.setContent("I want to buy the pc");
                            aclMessage.addReceiver(receivedMsg.getSender());
                            send(aclMessage);
                        }
                        break;
                        case ACLMessage.CONFIRM: {
                            System.out.println(receivedMsg.getSender().getName().split("@")[0] + " : " + receivedMsg.getContent());
                            break;
                        }
                    }
                } else {
                    block();
                }
            }
        });
//        addBehaviour(parallelBehaviour);
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }
}
