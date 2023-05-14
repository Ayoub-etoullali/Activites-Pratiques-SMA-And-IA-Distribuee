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
import javafx.collections.ObservableList;

public class BuyerAgent extends GuiAgent {
    BuyerAgentGUI buyerAgentGUI;
    DFAgentDescription[] dfAgentDescriptions; //info about all services
    private AID bestSeller;
    private double bestPrice = Double.MAX_VALUE;
    int cpt = 0;

    @Override
    protected void setup() {
        buyerAgentGUI = (BuyerAgentGUI) getArguments()[0];
        buyerAgentGUI.setBuyerAgent(this);
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {

            }
        });
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receivedMsg = receive();
                if (receivedMsg != null) {
                    switch (receivedMsg.getPerformative()) {
                        case ACLMessage.INFORM: {
                            //add to Services
                            String typeService=receivedMsg.getContent();
                            DFAgentDescription dfAgentDescription = new DFAgentDescription();
                            ServiceDescription serviceDescription = new ServiceDescription(); //1ère service
                            serviceDescription.setType(typeService);
                            dfAgentDescription.addServices(serviceDescription);
                            buyerAgentGUI.showServices("- "+receivedMsg.getSender().getName().split("@")[0] +" : "+typeService);
                            try {
                                dfAgentDescriptions = DFService.search(myAgent, dfAgentDescription);
                                for (DFAgentDescription agentDescription : dfAgentDescriptions) {
                                    AID sellerAID = agentDescription.getName();
                                    ACLMessage aclMessage = new ACLMessage(ACLMessage.CFP); //CFP : Call For Proposer
                                    aclMessage.setContent("Can give the price of PCs");
                                    buyerAgentGUI.showMessage(">> Can give the price of PCs");
                                    aclMessage.addReceiver(sellerAID);
                                    send(aclMessage);
                                }
                            } catch (FIPAException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        case ACLMessage.PROPOSE: {
                            cpt++;
                            double price = Double.parseDouble(receivedMsg.getContent());
                            if (price < bestPrice) {
                                bestPrice = price;
                                bestSeller = receivedMsg.getSender();
                            }
                            buyerAgentGUI.showMessage("<< price : "+receivedMsg.getContent());
                            System.out.println(cpt +" = "+ dfAgentDescriptions.length);
                            if (cpt == dfAgentDescriptions.length) {
//                                ACLMessage aclMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
//                                aclMessage.setContent("I accept your proposed price !!");
//                                aclMessage.addReceiver(bestSeller);
//                                send(aclMessage);
                            }
                            break;
                        }
                        case ACLMessage.AGREE: {
                            buyerAgentGUI.showMessage("<< "+receivedMsg.getContent());
                        }
                        break;
                        case ACLMessage.CONFIRM: {
                            buyerAgentGUI.showMessage("<< "+receivedMsg.getContent());
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
        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(parameter); //les services de type "pc"
        dfAgentDescription.addServices(serviceDescription);
        try {
            dfAgentDescriptions = DFService.search(this, dfAgentDescription);
            for (DFAgentDescription agentDescription : dfAgentDescriptions) {
                AID sellerAID = agentDescription.getName();
                ACLMessage aclMessage = new ACLMessage(ACLMessage.CFP); //CFP : Call For Proposer
                aclMessage.setContent("Can give the price of PCs");
                buyerAgentGUI.showMessage(">> Can give the price of PCs");
                aclMessage.addReceiver(sellerAID);
                send(aclMessage);
            }
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }

    public void commande() {
        //=> ACCEPT_PROPOSAL
        ACLMessage aclMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        aclMessage.setContent("I accept your proposed price !!");
        buyerAgentGUI.showMessage(">> I accept your proposed price !!");
        aclMessage.addReceiver(bestSeller);
        send(aclMessage);
    }

    public void buy() {
        //=> ACCEPT_PROPOSAL
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.setContent("I want to buy the pc");
        buyerAgentGUI.showMessage(">> I want to buy the pc");
        aclMessage.addReceiver(bestSeller);
        send(aclMessage);
    }

}
