import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

public class SimpleAgentContainer2 {

    public static void main(String[] args) throws StaleProxyException {


        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        //graphique user interface
        profile.setParameter("host","localhost");
        AgentContainer container=runtime.createAgentContainer(profile);
          container.createNewAgent("server","org.example.AgentServer",new Object[]{} );
    }



}
