package SmartContracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.0.1.
 */
public class SmartContract extends Contract {

    private static final String BINARY = "6080604052606f6000556021600155600060025560006003556000600560146101000a81548160ff0219169083600281111561003757fe5b021790555033600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506109d68061008c6000396000f3fe60806040526004361061009c5760003560e01c80635b390915116100645780635b390915146101de5780636696a48e14610235578063b7d042d314610260578063c19d93fb146102b7578063e9649e55146102f0578063f11db5c11461031b5761009c565b806320d57282146100a1578063268f1153146100cc57806327ed1dc91461010a5780633dae35ce146101595780634329db46146101b0575b600080fd5b3480156100ad57600080fd5b506100b661036c565b6040518082815260200191505060405180910390f35b3480156100d857600080fd5b50610108600480360360208110156100ef57600080fd5b81019080803560ff1690602001909291905050506103b2565b005b34801561011657600080fd5b506101436004803603602081101561012d57600080fd5b8101908080359060200190929190505050610536565b6040518082815260200191505060405180910390f35b34801561016557600080fd5b5061016e610667565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101dc600480360360208110156101c657600080fd5b810190808035906020019092919050505061068d565b005b3480156101ea57600080fd5b506101f36108c9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561024157600080fd5b5061024a6108f3565b6040518082815260200191505060405180910390f35b34801561026c57600080fd5b506102756108f9565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102c357600080fd5b506102cc61091f565b604051808260028111156102dc57fe5b60ff16815260200191505060405180910390f35b3480156102fc57600080fd5b50610305610932565b6040518082815260200191505060405180910390f35b34801561032757600080fd5b5061036a6004803603602081101561033e57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610938565b005b600080600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163190508091505090565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610475576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600b8152602001807f4e6f742063726561746f7200000000000000000000000000000000000000000081525060200191505060405180910390fd5b600560149054906101000a900460ff16600281111561049057fe5b81600281111561049c57fe5b1015610510576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600f8152602001807f4e6f742076616c6964205068617365000000000000000000000000000000000081525060200191505060405180910390fd5b80600560146101000a81548160ff0219169083600281111561052e57fe5b021790555050565b6000600180600281111561054657fe5b600560149054906101000a900460ff16600281111561056157fe5b146105d4576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600f8152602001807f4e6f742076616c6964205068617365000000000000000000000000000000000081525060200191505060405180910390fd5b60005460026000828254019250508190555060025483101561065e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260178152602001807f476f74206c65737320696e7374616c6c20656e6572677900000000000000000081525060200191505060405180910390fd5b82915050919050565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600280600281111561069b57fe5b600560149054906101000a900460ff1660028111156106b657fe5b14610729576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600f8152602001807f4e6f742076616c6964205068617365000000000000000000000000000000000081525060200191505060405180910390fd5b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146107ec576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600b8152602001807f4e6f742063726561746f7200000000000000000000000000000000000000000081525060200191505060405180910390fd5b600154600360008282540192505081905550600354821015610859576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602581526020018061097d6025913960400191505060405180910390fd5b349150600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc839081150290604051600060405180830381858888f193505050501580156108c4573d6000803e3d6000fd5b505050565b6000600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b60025481565b600460009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600560149054906101000a900460ff1681565b60035481565b80600460006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505056fe5472616e736665726564206574686572206c657373207468616e20696e7374616c6c455448a265627a7a72315820dd7e16ed1d59cc6f594f197f7acfaf5c7d023644f5dd75a2c798d1041d74711764736f6c63430005110032";

    public static final String FUNC_CHANGESTATE = "changeState";

    public static final String FUNC_INITGENADDRESS = "initGenAddress";

    public static final String FUNC_RECEIVEENERGY = "receiveEnergy";

    public static final String FUNC_TRANSFERETH = "transferETH";

    public static final String FUNC__TOGENERATOR = "_toGenerator";

    public static final String FUNC_CHECKGENBALANCE = "checkGenBalance";

    public static final String FUNC_CREATORCONTRACT = "creatorContract";

    public static final String FUNC_GETGENADDRESS = "getGenAddress";

    public static final String FUNC_STATE = "state";

    public static final String FUNC_TRANSMITTEDENERGY = "transmittedEnergy";

    public static final String FUNC_TRANSMITTEDWEI = "transmittedWei";


    protected SmartContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }


    protected SmartContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> changeState(BigInteger x) {
        final Function function = new Function(
                FUNC_CHANGESTATE, 
                Arrays.<Type>asList(new Uint8(x)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> initGenAddress(String _generator) {
        final Function function = new Function(
                FUNC_INITGENADDRESS, 
                Arrays.<Type>asList(new Address(_generator)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> receiveEnergy(BigInteger energy) {
        final Function function = new Function(
                FUNC_RECEIVEENERGY, 
                Arrays.<Type>asList(new Uint256(energy)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferETH(BigInteger _amount, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_TRANSFERETH, 
                Arrays.<Type>asList(new Uint256(_amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> _toGenerator() {
        final Function function = new Function(FUNC__TOGENERATOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> checkGenBalance() {
        final Function function = new Function(FUNC_CHECKGENBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> creatorContract() {
        final Function function = new Function(FUNC_CREATORCONTRACT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getGenAddress() {
        final Function function = new Function(FUNC_GETGENADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> state() {
        final Function function = new Function(FUNC_STATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> transmittedEnergy() {
        final Function function = new Function(FUNC_TRANSMITTEDENERGY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> transmittedWei() {
        final Function function = new Function(FUNC_TRANSMITTEDWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }


    public static SmartContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmartContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmartContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmartContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmartContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(SmartContract.class, web3j, credentials, contractGasProvider, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<SmartContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue) {
        return deployRemoteCall(SmartContract.class, web3j, transactionManager, contractGasProvider, BINARY, "", initialWeiValue);
    }

}
