import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class SimpleContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime=Runtime.instance();
        ProfileImpl profile=new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST,"localhost");
        AgentContainer agentContainer=runtime.createAgentContainer(profile);
        AgentController buyer=agentContainer.createNewAgent("buyer", BuyerAgent.class.getName(),new Object[]{});
        AgentController seller1=agentContainer.createNewAgent("seller1", SellerAgent.class.getName(),new Object[]{"13000"});
        AgentController seller2=agentContainer  .createNewAgent("seller2", SellerAgent.class.getName(),new Object[]{"15000"});
        AgentController seller3=agentContainer  .createNewAgent("seller3", SellerAgent.class.getName(),new Object[]{"10000"});
        seller1.start();
        seller2.start();
        seller3.start();
        buyer.start();
    }
}
