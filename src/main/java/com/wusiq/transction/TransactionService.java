package com.wusiq.transction;

import com.fasterxml.jackson.core.type.TypeReference;
import com.wusiq.comm.pojo.contract.ContractAbi;
import com.wusiq.comm.utils.JsonUtils;
import com.wusiq.contract.ContractService;
import com.wusiq.credentials.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.tx.RawTransactionManager;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private Web3j web3j;

//    public String send() throws IOException {
//
//        RawTransactionManager transactionManager = new RawTransactionManager(web3j, CredentialsService.createCredentials());
//        Function function = new Function(
//                FUNC_SET,
//                Arrays.<Type>asList(new Uint8(val)),
//                Collections.<TypeReference<?>>emptyList());
//
//        //获取nonce
//        BigInteger nonce = getTransactionCount();
//
//        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
//        BigInteger gasLimit = new BigInteger("900000");
//        //生成RawTransaction交易对象
//        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, ContractService.ADDRESS, value, "abcde123");//可以额外带数据
//        //使用Credentials对象对RawTransaction对象进行签名
//        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
//        String hexValue = Numeric.toHexString(signedMessage);
//        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
//        String transactionHash = ethSendTransaction.getTransactionHash();
//
//        if (ethSendTransaction.hasError()) {
//            String message = ethSendTransaction.getError().getMessage();
//            System.out.println("transaction failed,info:" + message);
//
//        } else {
//
//            EthGetTransactionReceipt send = web3j.ethGetTransactionReceipt(transactionHash).send();
//            if (send != null) {
//                System.out.println("交易成功");
//            }
//        }
//
//
//    }
//
//
//    private Function buildFunction(List<Object> abi, String functionName, List<Object> inputParam) {
////[{"constant":false,"inputs":[{"name":"val","type":"uint8"}],"name":"set","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"get","outputs":[{"name":"","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"}]
//        List<ContractAbi> abiList = JsonUtils.stringToObj(JsonUtils.objToString(abi), new TypeReference<List<ContractAbi>>() {
//        });
//        Function function = new Function();
//    }
//
//    private List<Type> convertType(List<ContractAbi.InputOutput> inputOutputList, List<Object> param) throws IllegalAccessException, InstantiationException {
//        List<Type> types = new ArrayList<>();
//        for (ContractAbi.InputOutput inputOutput : inputOutputList) {
//            Type type = AbiTypes.getType(inputOutput.getType()).getDeclaredConstructor(inputOutput.getName());
//            type.
//        }
//    }

    public BigInteger getTransactionCount() throws IOException {
        //加载本地KeyStore文件生成Credentials对象
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                CredentialsService.getAddress(), DefaultBlockParameterName.LATEST).send();
        return ethGetTransactionCount.getTransactionCount();
    }
}
