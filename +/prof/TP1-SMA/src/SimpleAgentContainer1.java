import jade.wrapper.AgentContainer;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class SimpleAgentContainer1 {

    public static void main(String[] args) throws StaleProxyException {


        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        //graphique user interface
        profile.setParameter("host","localhost");
        AgentContainer container=runtime.createAgentContainer(profile);
        AgentController agentCliemt = container.createNewAgent("client","org.example.AgentClient",new Object[]{});
    }



}
