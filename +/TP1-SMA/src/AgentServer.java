import jade.core.Agent;

public class AgentServer extends Agent {


    @Override
    protected void setup() {
        System.out.println("*** setup method ***");
    }

    @Override
    protected void beforeMove() {
        System.out.println("*** Before move ***");
    }

    @Override
    protected void afterMove() {
        System.out.println("*** after move ***");
    }

    @Override
    protected void takeDown() {
        System.out.println("*** take down ***");
    }
}
