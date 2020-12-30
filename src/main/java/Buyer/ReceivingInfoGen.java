package Buyer;

import Producer.Generator;
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

/**
 * Consumer received info about energy quantity from generator/
 * Then consumer deploy contract and send address to generator
 */
public class ReceivingInfoGen extends TickerBehaviour {

    private BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private BigInteger GAS_LIMIT = BigInteger.valueOf(6721975);
    private String PRIVATE_KEY = "20e4e3f7639eb956ea39d1d60412e035d4989b5e7f3c568ebd4d5bca667b4f08";
    private BigInteger initialWeiValue = BigInteger.valueOf(0);
    private String contractAddress;
    private SmartContract contract;
    private boolean deployFlag = true;
//    private boolean flagState1 = true;
    private int installMoney;

    private AtomicInteger progressInt = new AtomicInteger(0);
    private long period = 1;
    private double speed = 120;
    private double contractBody = 0.5;
    private double num_Eth = 5.0;// кол-во эфиров контракта
    private double updateProgress;
    private double installWei;

    public ReceivingInfoGen(Agent a, long period) {
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

        MessageTemplate mt = MessageTemplate.MatchProtocol("GeneratorQuantity");
        ACLMessage msgGenInfo = myAgent.receive(mt);

        MessageTemplate deployAgain = MessageTemplate.MatchProtocol("DeployAgain");
        ACLMessage msgDeployAgain = myAgent.receive(deployAgain);

        //TODO: Deploy again and send contract address to generator
        if (msgDeployAgain!=null){
            try {
                contractAddress = deployContract(web3j, credentials, contractGasProvider, initialWeiValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
            contract = SmartContract.load(contractAddress, web3j, credentials, contractGasProvider);
//            System.out.println("Consumer received " + msgDeployAgain.getContent());
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(msgDeployAgain.getSender());
            msg.setProtocol("ContractAddress");
            msg.setContent("Consumer send deployed contract address " + contractAddress);
            myAgent.send(msg);
            System.out.println(msg.getContent());
        }

        try {
            if (msgGenInfo != null  && deployFlag) {
                System.out.println("Consumer received " + msgGenInfo.getContent() + " from " + msgGenInfo.getSender().getLocalName());

                //TODO: Start to deploy contract

                    contractAddress = deployContract(web3j, credentials, contractGasProvider, initialWeiValue);
                    contract = SmartContract.load(contractAddress, web3j, credentials, contractGasProvider);
                    ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                    reply.addReceiver(msgGenInfo.getSender());
                    reply.setProtocol("ContractAddress");
                    reply.setContent("Consumer send deployed contract address " + contractAddress);
                    myAgent.send(reply);
    //                System.out.println("Consumer deployed contract and init state " + contract.state().send());


    //            contract = SmartContract.load(contractAddress, web3j, credentials, contractGasProvider);
    //            stop();
                    deployFlag = false;
                }

                //TODO: Consumer check that generator in contract and change phase contract on 1

            MessageTemplate templateInitGen = MessageTemplate.MatchProtocol("InitGen");
            ACLMessage msgInitGen = myAgent.receive(templateInitGen);

                if (contract != null && contract.getGenAddress().send() != null &&
                        contract.state().send().equals(BigInteger.valueOf(0)) && msgInitGen!=null) {
                    System.out.println("Consumer received " + msgInitGen.getContent());
                    contract.state().send();
                    contract.changeState(BigInteger.valueOf(1)).send();

                    ACLMessage msgChangeStateOn1 = new ACLMessage(ACLMessage.INFORM);
                    msgChangeStateOn1.addReceiver(msgInitGen.getSender());
                    msgChangeStateOn1.setProtocol("ChangeStateOn1");
                    msgChangeStateOn1.setContent("Consumer change contract state on " + contract.state().send());
                    myAgent.send(msgChangeStateOn1);
                    System.out.println(msgChangeStateOn1.getContent());
//                    flagState1 = false;
                }

                MessageTemplate templateEnergy = MessageTemplate.MatchProtocol("SendingEnergy");
                ACLMessage msgEnergy = myAgent.receive(templateEnergy);
                if (msgEnergy!=null && contract.state().send().equals(BigInteger.valueOf(1))){
                    ACLMessage msgState1 = new ACLMessage(ACLMessage.INFORM);
                    msgState1.addReceiver(msgEnergy.getSender());
                    msgState1.setProtocol("GoodState1");
                    int energy = Integer.parseInt(msgEnergy.getContent().split(" ")[3]);
                    //TODO: Consumer received energy
//                    contract.receiveEnergy(BigInteger.valueOf(energy)).send();

                    if (contract.receiveEnergy(BigInteger.valueOf(energy)).send().isStatusOK()) {

                        //updateProgress
                        progressInt.addAndGet((int) (period / (3600 * contractBody / speed) * 100 * 100));
                        updateProgress = progressInt.get() / 100.0;
                        installWei = updateProgress * num_Eth;

                        //TODO: Consumer change state on 2 and transfer money
                        contract.changeState(BigInteger.valueOf(2)).send();
                        contract.transferETH(BigInteger.valueOf((int)installWei), BigInteger.valueOf((int)installWei)).send();
                        msgState1.setContent("Consumer received energy and sent wei " + installWei);
                        myAgent.send(msgState1);

                    }
                }

                MessageTemplate templateFinish = MessageTemplate.MatchProtocol("GoodExecutionContract");
                ACLMessage msgFinish = myAgent.receive(templateFinish);

                if (msgFinish!=null){
                    ACLMessage msgStartAgain = new ACLMessage(ACLMessage.INFORM);
                    msgStartAgain.addReceiver(msgFinish.getSender());
                    msgStartAgain.setProtocol("Again");
                    msgStartAgain.setContent("Consumer received " + msgFinish.getContent());
                    myAgent.send(msgStartAgain);
                    System.out.println(msgStartAgain.getContent());

                    deployFlag = true;
//                    flagState1 = true;
                }

            } catch (Exception e) {
                e.printStackTrace();
        }

    }



    public String deployContract(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) throws Exception {
        return SmartContract.deploy(web3j, credentials, contractGasProvider, initialWeiValue)
                .send()
                .getContractAddress();
    }

    public Credentials getCredentialsFromPrivateKey(){
        return Credentials.create(PRIVATE_KEY);
    }
}
