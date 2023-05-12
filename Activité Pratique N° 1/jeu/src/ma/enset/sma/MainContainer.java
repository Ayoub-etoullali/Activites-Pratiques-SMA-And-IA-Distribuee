package ma.enset.sma;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;

public class MainContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance(); //récupérer l'instance de jade pour démarer un Container
        ProfileImpl profile=new ProfileImpl(); //préciser quelque info concernant config de Container
        profile.setParameter("gui","true"); //préciser quelque param : Graphique User Interface
        AgentContainer agentContainer=runtime.createMainContainer(profile); // démarrer MainContainer & N.B: package jade.wrapper
        agentContainer.start();
    }
}
