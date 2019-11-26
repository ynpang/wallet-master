package com.enjoy.core.web3_eth;

import com.enjoy.core.eth.WillContract;
import com.enjoy.core.eth.WillWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.utils.Convert;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

@Slf4j
public class EthWithdraw {

    public static BigInteger nonce = BigInteger.ZERO;
    @Value("${eth.gasLimit}")
    private static int gasLimit;

    /**
     * eth提币
     * @param to
     * @param ether
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String ethTokenTransaction(String to, double ether) throws IOException, ExecutionException, InterruptedException {
        String privateKey = "3d839b297443eae7a95c340f83a819342934619b70fcfda0468f082b365e606d";//系统热钱包

        WillContract contract = new WillContract(Web3Config.web3j);
        BigInteger ethGasPrice = contract.refreshGasPrice();
        BigInteger gasPrice = ethGasPrice.multiply(new BigInteger("110")).divide(new BigInteger("100"));

        WillWallet wallet = new WillWallet(privateKey);
        String address = wallet.getAddress();
        nonce = contract.nonce(address);
        BigInteger amount = Convert.toWei(String.valueOf(ether), Convert.Unit.ETHER).toBigInteger();
        String hexValue = wallet.signOfflineTransaction(nonce,gasPrice, BigInteger.valueOf(21000),to,amount);
        String transactionHash = contract.sendTransaction(hexValue);
        String str="nonce:"+nonce+" gasPrice: "+gasPrice+"   gasLimit:"+21000+"   接受地址："+to+"   总量："+amount+"  Hash:"+transactionHash;
        log.info(str);
        return str;
    }

    /**
     * eth  转账
     * @param privateKey
     * @param to
     * @param ether
     * @return
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static String ethTransactionToAddress(String privateKey, String to, double ether) throws IOException, ExecutionException, InterruptedException {
        //gasPrice 手动设置
        WillContract contract = new WillContract(Web3Config.web3j);
        BigInteger ethGasPrice = contract.refreshGasPrice();
        BigInteger gasPrice = ethGasPrice.multiply(new BigInteger("110")).divide(new BigInteger("100"));
        //gasLimit
        BigInteger gasLimit = BigInteger.valueOf(21000);
        //转账人私钥
        WillWallet wallet = new WillWallet(privateKey);
        String fromAddress = wallet.getAddress();
        BigInteger nonce = contract.nonce(fromAddress);
        //创建交易，这里是转0.5个以太币
        BigInteger amount = Convert.toWei(Double.toString(ether), Convert.Unit.ETHER).toBigInteger();
        String hexValue = wallet.signOfflineTransaction(nonce,gasPrice,gasLimit,to,amount);
        //发送交易
        String transactionHash = contract.sendTransaction(hexValue);
        //获得到transactionHash后就可以到以太坊的网站上查询这笔交易的状态了
        String str="nonce:"+nonce+" gasPrice: "+gasPrice+"   gasLimit:"+gasLimit+"   接受地址："+to+"   总量："+amount+"  Hash:"+transactionHash;
        log.info(str);
        return str;
    }
}
