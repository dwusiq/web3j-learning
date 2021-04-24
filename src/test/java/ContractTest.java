import com.wusiq.QuickStartApplication;
import com.wusiq.contract.ContractService;
import com.wusiq.transction.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.security.SecureRandom;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuickStartApplication.class)
public class ContractTest {
    @Autowired
    private ContractService contractService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private Web3j web3j;

    @Test
    public void contractTest() throws Exception {
        System.out.println(contractService.deployByContractClass());
    }


    @Test
    public void sendTransTest() throws Exception {
        TransactionReceipt result = contractService.sendTrans(new SecureRandom().nextInt(256));
        System.out.println(result);
        System.out.println(result.getBlockNumber());
        System.out.println(result.getBlockHash());
    }

    @Test
    public void getFromContractTest() throws Exception {
        System.out.println(contractService.getFromContract());
    }

//
//    @Test
//    public void decodeFuncInputTest() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, IOException {
//        Function function = buildFunction();
//        String functionEncode = FunctionEncoder.encode(function);
//        RawTransaction rawTransaction = RawTransaction.createTransaction(transactionService.getTransactionCount(), DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, com.wusiq.comm.utils.ParamUtils.HELLO_WORLD_ADDRESS, functionEncode);
//
//        RawTransactionManager transactionManager = new RawTransactionManager(web3j, CredentialsService.createCredentials());
//        EthSendTransaction ethSendTransaction = transactionManager.signAndSend(rawTransaction);
//        String hash = ethSendTransaction.getTransactionHash();
//        String response = ethSendTransaction.getRawResponse();
//        System.out.println("hash:" + hash);
//        System.out.println("response:" + response);
//    }
//
//
//    public static Function buildFunction() {
//        List<Type> inputParamTypeList = buildInputTypeList();
//        List<org.web3j.abi.TypeReference<?>> outputParamTypeList = buildOutTypeList();
//        Function function = new Function("set", inputParamTypeList, outputParamTypeList);
//        return function;
//    }
//
//    public static List<Type> buildInputTypeList() {
//        Integer value = 12;
//        List<Object> paramList = Arrays.asList(value);
//        String abiStr = "[{\"constant\":false,\"inputs\":[{\"name\":\"val\",\"type\":\"uint8\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}];\n";
//        List<MethodAbi> abiList = JsonUtils.stringToObj(abiStr, new TypeReference<List<MethodAbi>>() {
//        });
//
//
//        List<Type> types = new ArrayList<>();
//        for (MethodAbi methodAbi : abiList) {
//            if (!"set".equalsIgnoreCase(methodAbi.getName()))
//                continue;
//
//            List<MethodAbi.InputOutput> inputList = methodAbi.getInputs();
//            if (CollectionUtils.isEmpty(inputList))
//                return types;
//
//            for (int i = 0; i < inputList.size(); i++) {
//                Type type = JsonUtils.objToJavaBean(paramList.get(i), AbiTypes.getType(inputList.get(i).getType()));
//                types.add(type);
//            }
//        }
//
//        return types;
//    }
//
//    public static List<org.web3j.abi.TypeReference<?>> buildOutTypeList() {
//        String abiStr = "[{\"constant\":false,\"inputs\":[{\"name\":\"val\",\"type\":\"uint8\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}];\n";
//        List<MethodAbi> abiList = JsonUtils.stringToObj(abiStr, new TypeReference<List<MethodAbi>>() {
//        });
//
//
//        List<org.web3j.abi.TypeReference<?>> types = new ArrayList<>();
//        for (MethodAbi methodAbi : abiList) {
//            if (!"set".equalsIgnoreCase(methodAbi.getName()))
//                continue;
//
//            List<MethodAbi.InputOutput> outputList = methodAbi.getInputs();
//            if (CollectionUtils.isEmpty(outputList))
//                return types;
//
//            outputList.stream().forEach(o -> types.add(org.web3j.abi.TypeReference.create(AbiTypes.getType(o.getType()))));
//        }
//
//        return types;
//    }
}
