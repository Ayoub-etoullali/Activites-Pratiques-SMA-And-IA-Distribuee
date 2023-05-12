package ma.enset.rsa;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class ClientContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);

        /** Advanced Encryption Standard **/
        // Longueur(s) de la clé : 128, 192, 256 bits
//        String password="1234567812345678";
        String encodedPBK="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIBWhSgdB1TzzXGPTMMAIOaBlKJ/Mc4sDxMSwkrkdfScZe6AFkbpkqwHHyLEtZw+UTKmlzUF8X62iIDtkGf8Fk8CAwEAAQ==";

        AgentController agentClient=agentContainer.createNewAgent("client", ClientAgent.class.getName(),new Object[]{encodedPBK});
        agentClient.start();
    }
}
