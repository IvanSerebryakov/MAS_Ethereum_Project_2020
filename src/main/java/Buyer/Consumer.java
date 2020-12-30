package Buyer;

import jade.core.Agent;

import java.math.BigInteger;

public class Consumer extends Agent {



    @Override
    protected void setup() {
        addBehaviour(new SearchBidding(this, 3000));
        addBehaviour(new ReceivingInfoGen(this, 3000));
    }
}
