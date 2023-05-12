package ma.enset.rsa;

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

        /** Advanced Encryption Standard **/
        // Longueur(s) de la clé : 128, 192, 256 bits
//        String password="1234567812345678";
        String encodedPRK="MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAgFaFKB0HVPPNcY9MwwAg5oGUon8xziwPExLCSuR19Jxl7oAWRumSrAcfIsS1nD5RMqaXNQXxfraIgO2QZ/wWTwIDAQABAkAS32ZOe1JYMlAcaoPRy0OLUjPu34CN/pmq5t/OjqEv+tbJRFwLQM3wCZrsQ7u/Ug7x83/GCozomSffFS818uRBAiEA6kh3Gp/VTnqhOQqWgBfpJwMaa3UE35ZH1hoZu2fflUECIQCMO/qT4bUgUu69GRsmXIG69AxRUnoJ6/15KmHQ9Zb3jwIgV+0iZieeUnnLkDDdEu3mXvHXNIUScydfhob9KUxuqQECIEdMo42Wv4mswNeCMUyi0g7k0HJ0dbofs2Nsigk9JMG/AiAdVeLBH3MrObwtu+7ou4zZ/ui44VnWIQ5YJT3EKsX6iA==";

        AgentController agentServer=agentContainer.createNewAgent("server", ServerAgent.class.getName(),new Object[]{encodedPRK});
        agentServer.start();
    }
}
