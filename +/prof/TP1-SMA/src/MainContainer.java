import jade.wrapper.AgentContainer;
import jade.core.Runtime;
import jade.core.ProfileImpl;
import jade.wrapper.ControllerException;

public class MainContainer {

    public static void main(String[] args) throws ControllerException {


        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        //graphique user interface
        profile.setParameter("gui","true");
        AgentContainer agentContainer=runtime.createMainContainer(profile);

        agentContainer.start();


    }



}
