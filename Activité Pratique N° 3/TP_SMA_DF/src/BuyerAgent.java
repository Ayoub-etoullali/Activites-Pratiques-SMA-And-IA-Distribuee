import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
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

public class BuyerAgent extends GuiAgent {

    DFAgentDescription[] dfAgentDescriptions;
    @Override
    protected void setup() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                DFAgentDescription dfAgentDescription=new DFAgentDescription();
                ServiceDescription serviceDescription=new ServiceDescription();
                serviceDescription.setType("pc"); //les services de type "pc"
                dfAgentDescription.addServices(serviceDescription);
                try {
                    dfAgentDescriptions = DFService.search(myAgent, dfAgentDescription);
                    //CFP : Call For Proposer
                } catch (FIPAException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {

            }
        });
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
