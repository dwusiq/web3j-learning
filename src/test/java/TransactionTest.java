import com.wusiq.QuickStartApplication;
import com.wusiq.comm.utils.JsonUtils;
import com.wusiq.comm.utils.ParamUtils;
import com.wusiq.transction.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuickStartApplication.class)
public class TransactionTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    public void sendTransTest() throws Exception {
        String address = ParamUtils.HELLO_WORLD_ADDRESS;
        String abi = ParamUtils.HELLO_WORLD_ABI_JSON;
        List<Integer> paramList = Arrays.asList(23);
        EthSendTransaction result = transactionService.sendTransaction(address, abi, "set", paramList);

        System.out.println(JsonUtils.objToString(result));
        Assert.isTrue(!result.hasError(), "send transaction not success!");
        System.out.println(result.getRawResponse());
        System.out.println(result.getTransactionHash());
    }


    @Test
    public void callTransTest() throws Exception {
        String address = ParamUtils.HELLO_WORLD_ADDRESS;
        String abi = ParamUtils.HELLO_WORLD_ABI_JSON;
        List<Integer> paramList = Collections.EMPTY_LIST;
        EthCall result = transactionService.callTransaction(address, abi, "get", paramList);

        System.out.println("result:" + JsonUtils.objToString(result));
        Assert.isTrue(!result.hasError(), "call transaction not success!");
        System.out.println(result.getRawResponse());
        System.out.println(result.getValue());
    }

}
