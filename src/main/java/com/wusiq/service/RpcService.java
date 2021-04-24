package com.wusiq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import java.io.IOException;

@Service
public class RpcService {
    @Autowired
    private Web3j web3j;

    public String getVersion() throws IOException {
        return web3j.web3ClientVersion().send().getWeb3ClientVersion();
    }

    public long getChainId() throws IOException {
        return web3j.ethChainId().send().getId();
    }

    public String getCoinBase() throws IOException {
        return web3j.ethCoinbase().send().getAddress();
    }
}
