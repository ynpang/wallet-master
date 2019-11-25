package com.enjoy.portal.service.impl;

import com.enjoy.common.api.CommonResult;
import com.enjoy.core.web3_eth.Erc20TokenClient;
import com.enjoy.core.web3_eth.Erc20Withdraw;
import com.enjoy.core.web3_eth.EthWithdraw;
import com.enjoy.model.UmsMember;
import com.enjoy.portal.domain.Erc20WithdrawVo;
import com.enjoy.portal.domain.EthWithdrawVo;
import com.enjoy.portal.service.AccountService;
import com.enjoy.portal.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UmsMemberService memberService;

    @Override
    public CommonResult findUserAccount() {
        UmsMember currentMember =memberService.getCurrentMember();
        Long memberId = currentMember.getId();
        return null;
    }

    @Override
    public CommonResult updateUserEthAccount(EthWithdrawVo ethWithdrawVo) {
        try {
            String hash = EthWithdraw.ethTokenTransaction(ethWithdrawVo.getAccount(),ethWithdrawVo.getAmount());
            //to_do:insertTransaction

            return CommonResult.success(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public CommonResult updateUserErc20Account(Erc20WithdrawVo reqVo) {

        try {
            // 获得代币精度
            int len = Erc20TokenClient.getTokenDecimals(reqVo.getContractAddress());
            String hash = Erc20Withdraw.Erc20TokenTransaction(reqVo.getContractAddress(),reqVo.getAcount(),reqVo.getAmount(),len);

            //to_do:insertTransaction
            return CommonResult.success(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
