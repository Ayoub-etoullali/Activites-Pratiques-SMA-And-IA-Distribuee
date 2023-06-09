package com.etoullali.mas;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class SimpleContainer {
    public static void main(String[] args) throws ControllerException, InterruptedException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);

        AgentController master=agentContainer.createNewAgent("master", MasterAgent.class.getName(),new Object[]{});
        master.start();

        for (int i = 1; i <= 5; i++) {
            AgentController agent=agentContainer.createNewAgent("agent"+i, Agent.class.getName(),new Object[]{});
            Thread.sleep(1000);
            agent.start();
        }
    }
}
