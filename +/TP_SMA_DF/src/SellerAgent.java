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

public class SellerAgent extends Agent {
    private String price;

    @Override
    protected void setup() {
//        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                price = (String) getArguments()[0];
                DFAgentDescription dfAgentDescriptions = new DFAgentDescription(); // Directory Facilitator (Annuaire) : use for save services
                ServiceDescription serviceDescription = new ServiceDescription(); //1ère service
                serviceDescription.setType("PC");
                serviceDescription.setName("ACER");
                dfAgentDescriptions.addServices(serviceDescription);
                try {
                    DFService.register(myAgent, dfAgentDescriptions); //DFservice : Agent qui va publier le service
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
                        case ACLMessage.CFP: {
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.PROPOSE);
                            aclMessage.setContent(price);
                            aclMessage.addReceiver(receivedMsg.getSender());
                            send(aclMessage);
                            break;
                        }
                        case ACLMessage.ACCEPT_PROPOSAL: {
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.AGREE);
                            aclMessage.setContent("I can sell you the pc");
                            aclMessage.addReceiver(receivedMsg.getSender());
                            send(aclMessage);
                            break;
                        }
                        case ACLMessage.REQUEST: {
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.CONFIRM);
                            aclMessage.setContent("I will send you the pc");
                            aclMessage.addReceiver(receivedMsg.getSender());
                            send(aclMessage);
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
