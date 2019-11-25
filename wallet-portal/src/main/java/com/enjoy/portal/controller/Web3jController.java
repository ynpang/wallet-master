package com.enjoy.portal.controller;

import com.enjoy.common.api.CommonResult;
import com.enjoy.core.web3_eth.EthWithdraw;
import com.enjoy.portal.domain.Erc20WithdrawVo;
import com.enjoy.portal.domain.EthWithdrawVo;
import com.enjoy.portal.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
@Api(tags = "Web3jController", description = "用户区块链账号")
@RequestMapping("/account")
public class Web3jController {

    @Autowired
    private AccountService accountService;


    @ApiOperation("获取用户以太坊地址")
    @RequestMapping(value = "/getEthAccount", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult getEthAccount() {
        return accountService.findUserAccount();
    }

    @ApiOperation("用户提现 以太币")
    @RequestMapping(value = "/ethWithdraw", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult ethWithdraw(EthWithdrawVo ethWithdrawVo) {
        return accountService.updateUserEthAccount(ethWithdrawVo);
    }

    @ApiOperation("用户提现 erc20代币")
    @RequestMapping(value = "/erc20Withdraw", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult erc20Withdraw(Erc20WithdrawVo erc20WithdrawVo) {
        return accountService.updateUserErc20Account(erc20WithdrawVo);
    }
}
