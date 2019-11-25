package com.enjoy.core.web3_eth;

import com.enjoy.core.eth.WillContract;
import com.enjoy.core.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ChainId;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Erc20Withdraw {

    private static Web3j web3j = Web3j.build(new HttpService(Web3Config.web3j));

    /**
     *
     * @param contractAddress 合约地址
     * @param toAddress 目标地址
     * @param amount
     * @param decimals
     * @return
     * @throws Exception
     */
    public static String Erc20TokenTransaction(String contractAddress, String toAddress, double amount, int decimals) throws Exception {
        //获得keysotre文件
        String keystore = FileUtil.readToString(Web3Config.hotWallet);
        String privateKey = DecryptWallet.decryptWallet(keystore,"123456" );//  你自己设置的密码
        //gasPrice 手动设置
        WillContract contract = new WillContract(Web3Config.web3j);
        BigInteger ethGasPrice = contract.refreshGasPrice();
        BigInteger gasPrice = ethGasPrice.multiply(new BigInteger("110")).divide(new BigInteger("100"));
        BigInteger gasLimit = BigInteger.valueOf(80000);
        BigInteger value = BigInteger.ZERO;
        //token转账参数
        BigInteger tokenValue = BigDecimal.valueOf(amount).multiply(BigDecimal.TEN.pow(decimals)).toBigInteger();
        Function function = WillContract.transfer(toAddress,tokenValue);
        Credentials credentials = Credentials.create(privateKey);
        return contract.execute(credentials,function,contractAddress,gasPrice,gasLimit, value);

    }

    /**
     *
     * @param privateKey       私钥
     * @param contractAddress 合约地址
     * @param toAddress        目标地址
     * @param amount
     * @param decimals
     * @return
     * @throws Exception
     */
    public static String Erc20TokenTransaction(String privateKey, String contractAddress, String toAddress, double amount, int decimals) throws Exception {
        //gasPrice 手动设置
        WillContract contract = new WillContract(Web3Config.web3j);
        BigInteger ethGasPrice = contract.refreshGasPrice();
        BigInteger gasPrice = ethGasPrice.multiply(new BigInteger("110")).divide(new BigInteger("100"));
        BigInteger gasLimit = BigInteger.valueOf(80000);
        BigInteger value = BigInteger.ZERO;
        //token转账参数
        BigInteger tokenValue = BigDecimal.valueOf(amount).multiply(BigDecimal.TEN.pow(decimals)).toBigInteger();
        Function function = WillContract.transfer(toAddress,tokenValue);
        Credentials credentials = Credentials.create(privateKey);
        return contract.execute(credentials,function,contractAddress,gasPrice,gasLimit, value);

    }
}
