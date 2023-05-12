package com.etoullali.TPs.TP1;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class SimpleAgentContainer1 {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter("host","localhost"); //préciser quelque param : MainContainer address (local)
        AgentContainer container=runtime.createAgentContainer(profile);
        AgentController agentClient = container.createNewAgent("client", "com.etoullali.TPs.TP1.AgentClient", new Object[]{"Ayoub","ETOULLALI"});
        // Object[]{} : tableau d'objets pour passer les args -> il peut récupérer dans la méthode setup()
        agentClient.start();
    }
}
