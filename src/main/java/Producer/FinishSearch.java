package Producer;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

//Try OneShortBehaviour
public class FinishSearch extends TickerBehaviour {

    private double energyQuantity = 1110;//then change numbers in other project
    private double energyPrice = 33;

    public FinishSearch(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
        MessageTemplate mt = MessageTemplate.MatchProtocol("Hello");
        ACLMessage msg = myAgent.receive(mt);

        //TODO:Generator received info: Consumer found Generator
        if(msg != null){
            System.out.println("Generator received " + msg.getContent() + " from " + msg.getSender().getLocalName());

            //reply -> Generator send info to Consumer about his energy quantity and energy price
            ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
            reply.addReceiver(msg.getSender());
            reply.setProtocol("GeneratorQuantity");
            reply.setContent("Energy Quantity " + energyQuantity + " energy price " + energyPrice);
            myAgent.send(reply);
            stop();
        }
    }
}
