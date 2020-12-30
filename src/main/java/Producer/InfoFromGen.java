package Producer;

import SmartContracts.SmartContract;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicInteger;

public class InfoFromGen extends TickerBehaviour {

    private SmartContract contract;
    private String generatorAddress = "0x95a78c93eFFAFcDC51ae7B2b34f91B9BA8a29ef8";
    private BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private BigInteger GAS_LIMIT = BigInteger.valueOf(6721975);
    private String PRIVATE_KEY = "20e4e3f7639eb956ea39d1d60412e035d4989b5e7f3c568ebd4d5bca667b4f08";
//    private String PRIVATE_KEY = "60fde667331d2131421a70d0f53ac6a78623d31b5b51f1824ff4eb40397d21d9";
    private BigInteger initialWeiValue = BigInteger.valueOf(0);
    private boolean flagState1 = true;

    private AtomicInteger progressInt = new AtomicInteger(0);
    private long period = 1;
    private double speed = 120;
    private double contractBody = 0.5;
    private double updateProgress;
    private double number_Eth = 5; // кол-во эфиров контракта
    private double kwth_Cost = 0.3;// стоимость за 1кВт ЭЭ

    public InfoFromGen(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {

        Web3j web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
        Credentials credentials = getCredentialsFromPrivateKey();

        ContractGasProvider contractGasProvider  = new ContractGasProvider() {
            @Override
            public BigInteger getGasPrice(String s) {
                return GAS_PRICE;
            }

            @Override
            public BigInteger getGasPrice() {
                return GAS_PRICE;
            }

            @Override
            public BigInteger getGasLimit(String s) {
                return GAS_LIMIT;
            }

            @Override
            public BigInteger getGasLimit() {
                return GAS_LIMIT;
            }
        };

        MessageTemplate mt = MessageTemplate.MatchProtocol("ContractAddress");
        ACLMessage msg = myAgent.receive(mt);
        try {
            if (msg!=null){
                String contractAddress = msg.getContent().split(" ")[5];
                System.out.println("Generator received " + contractAddress + " from " + msg.getSender().getLocalName());

                //TODO: Generator write himself to contract
                contract = SmartContract.load(contractAddress, web3j, credentials, contractGasProvider);

                    contract.initGenAddress(generatorAddress).send();

                    ACLMessage msgInitGen = new ACLMessage(ACLMessage.INFORM);
                    msgInitGen.addReceiver(msg.getSender());
                    msgInitGen.setProtocol("InitGen");
                    msgInitGen.setContent("Generator write him to contract " + contract._toGenerator().send());
                    myAgent.send(msgInitGen);
                System.out.println(msgInitGen.getContent());

    //            stop();
            }

            MessageTemplate templateChangeStateOn1 = MessageTemplate.MatchProtocol("ChangeStateOn1");
            ACLMessage msgChangeOn1 = myAgent.receive(templateChangeStateOn1);

            if (contract!=null && contract.state().send().equals(BigInteger.valueOf(1)) && msgChangeOn1!=null){

                //Update Progress
                progressInt.addAndGet((int) (period / (3600 * contractBody / speed) * 100 * 100));
                updateProgress = progressInt.get() / 100.0;
                double energy = (updateProgress * number_Eth) / kwth_Cost;

                System.out.println("Generator received " + msgChangeOn1.getContent());

                ACLMessage msgEnergy = new ACLMessage(ACLMessage.INFORM);
//                assert msg != null;
                msgEnergy.addReceiver(msgChangeOn1.getSender());
                msgEnergy.setContent("Generator sent energy " + (int)energy);
                msgEnergy.setProtocol("SendingEnergy");
                myAgent.send(msgEnergy);
                System.out.println(msgEnergy.getContent());
//                flagState1 = false;
            }

            MessageTemplate templateGoodState1 = MessageTemplate.MatchProtocol("GoodState1");
            ACLMessage msgGoodState1 = myAgent.receive(templateGoodState1);


            if (msgGoodState1 != null) {
                System.out.println("Generator received " + msgGoodState1.getContent());

                //TODO: Generator check his balance and send that -> OK
                if (contract != null && contract.state().send().equals(BigInteger.valueOf(2))){
                    ACLMessage msgFinish = new ACLMessage(ACLMessage.INFORM);
                    msgFinish.addReceiver(msgGoodState1.getSender());
                    msgFinish.setProtocol("GoodExecutionContract");
                    msgFinish.setContent("Generator check his balance " + contract.checkGenBalance().send());
                    myAgent.send(msgFinish);

                    flagState1 = true;
                }

            }

            MessageTemplate templateAgain = MessageTemplate.MatchProtocol("Again");
            ACLMessage msgAgain = myAgent.receive(templateAgain);

            if (msgAgain!=null){
                //TODO: Send msg to Consumer with Protocol as Deploy in order to start contract execution again
                ACLMessage msgToDeploy = new ACLMessage(ACLMessage.INFORM);
                msgToDeploy.addReceiver(msgAgain.getSender());
                msgToDeploy.setProtocol("DeployAgain");
                msgToDeploy.setContent("Generator received " + msgAgain.getContent());
                myAgent.send(msgToDeploy);
                System.out.println(msgToDeploy.getContent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Credentials getCredentialsFromPrivateKey(){
        return Credentials.create(PRIVATE_KEY);
    }
}
