package com.etoullali.mas;

import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MasterAgent extends GuiAgent {
    List<Integer> list = new ArrayList<>();
    public List<String> listAgents = new ArrayList<>();
    public static String path;
    public String agent;

    public static MasterAgent GUI(){
        MasterAgent masterAgent=new MasterAgent();
        masterAgent.path=getPath();
        return masterAgent;
    }
    public void setListAgents(List<String> listAgents) {
        this.listAgents = listAgents;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public List<String> getListAgents() {
        return listAgents;
    }

    public static String getPath() {
        return path;
    }

    public String getAgent() {
        return agent;
    }

    @Override
    protected void setup() {
        DFAgentDescription dfAgentDescription = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription(); //1ère service
        serviceDescription.setName("master");
        serviceDescription.setType("ql");
        dfAgentDescription.addServices(serviceDescription);
        try {
            DFService.register(this, dfAgentDescription);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receiver = receive();
                if (receiver != null) {
                    list.add(Integer.valueOf(receiver.getContent().split("size : ")[1]));
                    System.out.println(receiver.getSender().getName().split("@")[0] + " ** " + receiver.getContent());
                    listAgents.add(receiver.getSender().getName().split("@")[0] + " ** " + receiver.getContent());
                      /*if (list.size()==5){
                          List<Integer> list1=list;
                          Collections.sort(list1);
                          System.out.println("\n***********");
                          System.out.println(" Agent : "+list.(list1.get(0)));
                          System.out.println("***********");
                      }*/
                    if (list.size() == 5) {
                        int minValue = Integer.MAX_VALUE;
                        int minIndex = -1;

                        for (int i = 0; i < list.size(); i++) {
                            int currentValue = list.get(i);
                            if (currentValue < minValue) {
                                minValue = currentValue;
                                minIndex = i;
                            }
                        }
                        System.out.println("\n*************************************");
                        System.out.println("Le chemin optimale : " + minValue);
                        path=String.valueOf(minValue);
                        System.out.println("l'agent qui trouve le plus court chemin  : " + minIndex+1);
                        agent=String.valueOf(minIndex+1);
                        System.out.println("*************************************");
                    }
                }

            }
        });
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {

    }
}
