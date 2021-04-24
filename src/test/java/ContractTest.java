import com.fasterxml.jackson.core.type.TypeReference;
import com.wusiq.QuickStartApplication;
import com.wusiq.comm.pojo.contract.ContractAbi;
import com.wusiq.comm.utils.JsonUtils;
import com.wusiq.contract.ContractService;
import com.wusiq.credentials.CredentialsService;
import com.wusiq.transction.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    @Test
    public void decodeFuncInputTest() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, IOException {
        Function function = buildFunction();
        String functionEncode = FunctionEncoder.encode(function);
        RawTransaction rawTransaction = RawTransaction.createTransaction(transactionService.getTransactionCount(), DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, ContractService.ADDRESS, functionEncode);

        RawTransactionManager transactionManager = new RawTransactionManager(web3j, CredentialsService.createCredentials());
        EthSendTransaction ethSendTransaction = transactionManager.signAndSend(rawTransaction);
        String hash = ethSendTransaction.getTransactionHash();
        String response = ethSendTransaction.getRawResponse();
        System.out.println("hash:" + hash);
        System.out.println("response:" + response);
    }

    @Test
    public void getFromContract1Test() throws Exception {
        Integer myValue = 12;
//        boolean b = myValue instanceof Number;
//        Class clazz = AbiTypes.getType("uint8");
//        Constructor[] constructorList = clazz.getConstructors();
//        for(Constructor constructor:constructorList){
//            if(constructor.getParameterCount()!=1)
//                continue;
//            Class[] str = constructor.getParameterTypes();
//            System.out.println(str);
//        }

//        List st = Utils.typeMap(Arrays.asList(BigInteger.valueOf(11L)),AbiTypes.getType("uint8"));

//        Constructor<? extends Type> constructor1 = AbiTypes.getType("uint8").getDeclaredConstructor(Number.class);
//
//
//        constructor1.setAccessible(true);//允许访问
//        Type p1 = constructor1.newInstance(BigInteger.valueOf(11));//成功，通过私有的受保护的构造方法创建了对象

    }

    public static Function buildFunction() {
        List<Type> inputParamTypeList = buildInputTypeList();
        List<org.web3j.abi.TypeReference<?>> outputParamTypeList = buildOutTypeList();
        Function function = new Function("set", inputParamTypeList, outputParamTypeList);
        return function;
    }

    public static List<Type> buildInputTypeList() {
        Integer value = 12;
        List<Object> paramList = Arrays.asList(value);
        String abiStr = "[{\"constant\":false,\"inputs\":[{\"name\":\"val\",\"type\":\"uint8\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}];\n";
        List<ContractAbi> abiList = JsonUtils.stringToObj(abiStr, new TypeReference<List<ContractAbi>>() {
        });


        List<Type> types = new ArrayList<>();
        for (ContractAbi contractAbi : abiList) {
            if (!"set".equalsIgnoreCase(contractAbi.getName()))
                continue;

            List<ContractAbi.InputOutput> inputList = contractAbi.getInputs();
            if (CollectionUtils.isEmpty(inputList))
                return types;

            for (int i = 0; i < inputList.size(); i++) {
                Type type = JsonUtils.objToJavaBean(paramList.get(i), AbiTypes.getType(inputList.get(i).getType()));
                types.add(type);
            }
        }

        return types;
    }

    public static List<org.web3j.abi.TypeReference<?>> buildOutTypeList() {
        String abiStr = "[{\"constant\":false,\"inputs\":[{\"name\":\"val\",\"type\":\"uint8\"}],\"name\":\"set\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"uint8\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"}];\n";
        List<ContractAbi> abiList = JsonUtils.stringToObj(abiStr, new TypeReference<List<ContractAbi>>() {
        });


        List<org.web3j.abi.TypeReference<?>> types = new ArrayList<>();
        for (ContractAbi contractAbi : abiList) {
            if (!"set".equalsIgnoreCase(contractAbi.getName()))
                continue;

            List<ContractAbi.InputOutput> outputList = contractAbi.getInputs();
            if (CollectionUtils.isEmpty(outputList))
                return types;

            outputList.stream().forEach(o -> types.add(org.web3j.abi.TypeReference.create(AbiTypes.getType(o.getType()))));
        }

        return types;
    }
}
