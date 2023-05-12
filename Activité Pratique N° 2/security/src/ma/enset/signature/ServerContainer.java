package ma.enset.signature;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class ServerContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);

        String encodedPBK="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIBWhSgdB1TzzXGPTMMAIOaBlKJ/Mc4sDxMSwkrkdfScZe6AFkbpkqwHHyLEtZw+UTKmlzUF8X62iIDtkGf8Fk8CAwEAAQ==";

        AgentController agentServer=agentContainer.createNewAgent("server", ServerAgent.class.getName(),new Object[]{encodedPBK});
        agentServer.start();
    }
}
