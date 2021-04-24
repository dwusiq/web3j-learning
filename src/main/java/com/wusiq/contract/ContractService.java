package com.wusiq.contract;

import com.wusiq.credentials.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

import static com.wusiq.comm.utils.ParamUtils.HELLO_WORLD_ADDRESS;

/**
 *
 */
@Service
public class ContractService {
    @Autowired
    private Web3j web3j;

    public String deployByContractClass() throws Exception {
        HelloWorld contract = HelloWorld.deploy(
                web3j, CredentialsService.createCredentials(), new DefaultGasProvider()).send();
        return contract.getContractAddress();
    }

    public TransactionReceipt sendTrans(Integer value) throws Exception {
        HelloWorld contract = new HelloWorld(HELLO_WORLD_ADDRESS, web3j, CredentialsService.createCredentials(), new DefaultGasProvider());
        TransactionReceipt result = contract.set(BigInteger.valueOf(value)).send();
        return result;
    }

    public Object getFromContract() throws Exception {
        HelloWorld contract = new HelloWorld(HELLO_WORLD_ADDRESS, web3j, CredentialsService.createCredentials(), new DefaultGasProvider());
        return contract.get().send();
    }

}
