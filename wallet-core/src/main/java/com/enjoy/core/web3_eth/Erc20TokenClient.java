package com.enjoy.core.web3_eth;

import com.enjoy.core.eth.Erc20CommonContract;
import com.enjoy.core.eth.WillContract;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;


public class Erc20TokenClient {

    private static Web3j web3j = Web3j.build(new HttpService(Web3Config.web3j));

    @Value("${erc20.gasLimit}")
    private int gasLimitErc;

    /**
     * 查询代币精度
     *
     * @param contractAddress
     * @return
     */
    public static int getTokenDecimals(String contractAddress) throws Exception {
        Erc20CommonContract erc20CommonContract = new Erc20CommonContract(contractAddress,web3j);
        RemoteCall<BigInteger> result = erc20CommonContract.decimals();
        return result.send().intValue();
    }

    /**
     * 代币转账
     * @param privateKey       私钥
     * @param contractAddress  合约地址
     * @param toAddress         目标地址
     * @param amount
     * @param decimals
     * @return
     * @throws Exception
     */
    public String erc20TokenTransaction(String privateKey, String contractAddress, String toAddress, double amount, int decimals) throws Exception {
        WillContract contract = new WillContract(Web3Config.web3j);
        BigInteger ethGasPrice = contract.refreshGasPrice();
        BigInteger gasPrice = ethGasPrice.multiply(new BigInteger("110")).divide(new BigInteger("100"));
        BigInteger gasLimit = BigInteger.valueOf(this.gasLimitErc);
        BigInteger value = BigInteger.ZERO;
        //token转账参数
        BigInteger tokenValue = BigDecimal.valueOf(amount).multiply(BigDecimal.TEN.pow(decimals)).toBigInteger();
        Function function = WillContract.transfer(toAddress,tokenValue);
        Credentials credentials = Credentials.create(privateKey);
        return contract.execute(credentials,function,contractAddress,gasPrice,gasLimit, value);
    }

}
