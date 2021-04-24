package com.wusiq.credentials;


import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

import java.io.IOException;
import java.util.Objects;

public class CredentialsService {

    public static Credentials createCredentials() {
        Credentials credentials = null;
        try {
            credentials = WalletUtils.loadCredentials("123456", "E:\\codes\\Ethereum\\chain55\\keystore\\UTC--2021-04-21T07-10-54.959632900Z--7486f44b09d7a28a56685689973c8231cd2757ab");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    public static String getAddress() {
        Credentials credentials = createCredentials();
        if (Objects.isNull(credentials))
            return null;
        return credentials.getAddress();
    }
}
