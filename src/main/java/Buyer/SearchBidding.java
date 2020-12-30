package Buyer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

public class SearchBidding extends TickerBehaviour {

    public SearchBidding(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Bidding");
        template.addServices(sd);

        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            for (int i =0; i < result.length; i++){
                System.out.println("Buyer found " + result[i].getName().getLocalName());

                String foundAgent = result[i].getName().getLocalName();
                //Consumer send msg to generator - i found you and they start bidding
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                AID aid = new AID(foundAgent,false);
                msg.addReceiver(aid);
                msg.setContent("I found " + foundAgent);
                msg.setProtocol("Hello");
                myAgent.send(msg);
                stop();
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
