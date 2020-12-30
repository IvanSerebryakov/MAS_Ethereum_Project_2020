package Producer;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;


public class Generator extends Agent {



    @Override
    protected void setup() {
        //TODO: Register the bidding in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(new Generator().getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Bidding");
        sd.setName(getLocalName()+"-Bidding");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new FinishSearch(this, 2000));
        addBehaviour(new InfoFromGen(this, 2000));
    }
}
