import com.wusiq.QuickStartApplication;
import com.wusiq.service.RpcService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuickStartApplication.class)
public class Web3Test {

    @Autowired
    private RpcService rpcService;

    @Test
    public void versionTest() throws IOException {
        System.out.println(rpcService.getVersion());
        System.out.println(rpcService.getChainId());
        System.out.println(rpcService.getCoinBase());
    }
}