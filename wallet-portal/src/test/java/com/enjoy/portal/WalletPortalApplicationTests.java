package com.enjoy.portal;

import com.enjoy.core.web3_eth.EthWithdraw;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletPortalApplicationTests {

	@Autowired
	private EthWithdraw ethWithdraw;

	@Test
	public void contextLoads() throws InterruptedException, ExecutionException, IOException {
		ethWithdraw.ethTokenTransaction("0x9Ae269f41b3922948877905731c9D25c070b1bfd",10 );
	}


}
