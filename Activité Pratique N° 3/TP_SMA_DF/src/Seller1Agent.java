import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class Seller1Agent extends GuiAgent {
    private Seller1AgentGUI seller1AgentGUI;
    private DFAgentDescription dfAgentDescription=new DFAgentDescription();
    private String type;
    private String price;

    @Override
    protected void setup() {
        seller1AgentGUI = (Seller1AgentGUI) getArguments()[0];
        seller1AgentGUI.setSeller1Agent1(this);
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                price = (String) getArguments()[2];
                ServiceDescription serviceDescription = new ServiceDescription(); //1ère service
                serviceDescription.setType("PC");
                serviceDescription.setName("ACER");
                dfAgentDescription.addServices(serviceDescription);
                try {
                    DFService.register(myAgent, dfAgentDescription); //DFservice : Agent qui va publier le service
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
                            seller1AgentGUI.showMessage("<< "+receivedMsg.getContent());
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.PROPOSE);
                            aclMessage.setContent(price);
                            seller1AgentGUI.showMessage(" >> price : "+price);
                            aclMessage.addReceiver(receivedMsg.getSender());
                            send(aclMessage);
                            break;
                        }
                        case ACLMessage.ACCEPT_PROPOSAL: {
                            seller1AgentGUI.showMessage("<< "+receivedMsg.getContent());
                            ACLMessage aclMessage = new ACLMessage(ACLMessage.AGREE);
                            aclMessage.setContent("I can sell you the pc");
                            seller1AgentGUI.showMessage(">> I can sell you the pc");
                            aclMessage.addReceiver(receivedMsg.getSender());
                            send(aclMessage);
                            break;
                        }
                        case ACLMessage.REQUEST: {
                            seller1AgentGUI.showMessage("<< "+receivedMsg.getContent());
//                            ACLMessage aclMessage = new ACLMessage(ACLMessage.CONFIRM);
//                            aclMessage.setContent("I will send you the pc");
//                            aclMessage.addReceiver(receivedMsg.getSender());
//                            send(aclMessage);
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

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        String parameter = (String) guiEvent.getParameter(0);
        System.out.println(parameter); //afficher le service
        //=> send to buyers
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.addReceiver(new AID("buyer", AID.ISLOCALNAME));
        message.setContent(parameter);
        send(message);
    }

    public void conf() {
        //=> send to buyer
        ACLMessage aclMessage = new ACLMessage(ACLMessage.CONFIRM);
        aclMessage.setContent("I will send you the pc");
        seller1AgentGUI.showMessage(">> I will send you the pc");
        aclMessage.addReceiver(new AID("buyer", AID.ISLOCALNAME));
        send(aclMessage);
    }

    public DFAgentDescription getDfAgentDescription() {
        return dfAgentDescription;
    }
}
