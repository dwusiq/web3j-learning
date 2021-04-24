package com.wusiq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3Config {
    @Bean
    public Web3j getWeb3j() {
        Web3j web3 = Web3j.build(new HttpService("http://localhost:8545/"));//HTTP 连接,还支持ipc,WebSocket
        return web3;
    }
}
