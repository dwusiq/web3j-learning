package com.wusiq.transction;

import com.wusiq.comm.pojo.contract.MethodAbi;
import com.wusiq.comm.utils.AbiUtils;
import com.wusiq.comm.utils.JsonUtils;
import com.wusiq.credentials.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 交易服务（TODO 实际生产中将Credentials相关逻辑改为接口入参形式即可）
 */
@Service
public class TransactionService {

    @Autowired
    private Web3j web3j;

    /**
     * 发送交易调用solidity函数
     *
     * @param contractAddress   合约地址
     * @param abiJson           abi的json字符串
     * @param methodName        被调用的solidity函数名称
     * @param functionParamList 函数入参列表
     * @return
     * @throws IOException
     */
    public EthSendTransaction sendTransaction(String contractAddress, String abiJson, String methodName, List<?> functionParamList) throws IOException {
        //初始化Function的对象
        Function function = buildFunction(abiJson, methodName, functionParamList);
        //生成hash
        String functionEncode = FunctionEncoder.encode(function);
        //获取下一个nonce
        BigInteger nonce = getNextTransactionCount();
        //初始化RawTransaction对象
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT, contractAddress, functionEncode);
        //签名，并发送交易
        RawTransactionManager transactionManager = new RawTransactionManager(web3j, CredentialsService.createCredentials());
        EthSendTransaction ethSendTransaction = transactionManager.signAndSend(rawTransaction);
        return ethSendTransaction;
    }


    /**
     * @param contractAddress
     * @param abiJson
     * @param methodName
     * @param functionParamList
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public EthCall callTransaction(String contractAddress, String abiJson, String methodName, List<?> functionParamList) throws ExecutionException, InterruptedException {
        //初始化Function的对象
        Function function = buildFunction(abiJson, methodName, functionParamList);
        //生成hash
        String functionEncode = FunctionEncoder.encode(function);
        EthCall response = web3j.ethCall(
                Transaction.createEthCallTransaction(CredentialsService.createCredentials().getAddress(), contractAddress, functionEncode),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        return response;
    }

    /**
     * 构建被调用函数的Function对象
     *
     * @param abiJson           abi的json字符串
     * @param methodName        被调用的solidity函数名称
     * @param functionParamList 函数入参列表
     * @return
     */
    private Function buildFunction(String abiJson, String methodName, List<?> functionParamList) {
        MethodAbi methodAbi = AbiUtils.getMethodAbiFromJsonByMethodName(abiJson, methodName);
        List<Type> inputParamTypeList = AbiUtils.buildInputParamTypeList(methodAbi, functionParamList);
        List<TypeReference<?>> outputParamTypeList = AbiUtils.buildOutParamTypeList(methodAbi);
        Function function = new Function(methodName, inputParamTypeList, outputParamTypeList);
        return function;
    }


    /**
     * 获取用户的nonce
     *
     * @return
     * @throws IOException
     */
    private synchronized BigInteger getNextTransactionCount() throws IOException {
        //加载本地KeyStore文件生成Credentials对象
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                CredentialsService.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigInteger currentNonce = ethGetTransactionCount.getTransactionCount();
//        System.out.println("currentNonce:" + currentNonce);
//        BigInteger nextNonce = currentNonce.add(BigInteger.ONE);
//        System.out.println("nextNonce:" + nextNonce);
        return currentNonce;
    }
}
