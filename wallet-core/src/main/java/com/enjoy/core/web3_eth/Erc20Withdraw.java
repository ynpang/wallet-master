package com.enjoy.core.web3_eth;

import com.enjoy.core.eth.WillContract;
import com.enjoy.core.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Erc20Withdraw {

    private static Web3j web3j = Web3j.build(new HttpService(Web3Config.web3j));

    @Value("${erc20.gasLimit}")
    private static int gasLimitErc;

    /**
     * 代币提币
     * @param contractAddress 合约地址
     * @param toAddress 目标地址
     * @param amount
     * @param decimals
     * @return
     * @throws Exception
     */
    public  static String Erc20TokenTransaction(String contractAddress, String toAddress, double amount, int decimals) throws Exception {
        //获得keysotre文件
        String keystore = FileUtil.readToString(Web3Config.hotWallet);
        String privateKey = DecryptWallet.decryptWallet(keystore,"123456" );//  你自己设置的密码
        //gasPrice 手动设置
        WillContract contract = new WillContract(Web3Config.web3j);
        BigInteger ethGasPrice = contract.refreshGasPrice();
        BigInteger gasPrice = ethGasPrice.multiply(new BigInteger("110")).divide(new BigInteger("100"));
        BigInteger gasLimit = BigInteger.valueOf(gasLimitErc);
        BigInteger value = BigInteger.ZERO;
        //token转账参数
        BigInteger tokenValue = BigDecimal.valueOf(amount).multiply(BigDecimal.TEN.pow(decimals)).toBigInteger();
        Function function = WillContract.transfer(toAddress,tokenValue);
        Credentials credentials = Credentials.create(privateKey);
        return contract.execute(credentials,function,contractAddress,gasPrice,gasLimit, value);

    }
}
