package com.enjoy.portal.service;

import com.enjoy.common.api.CommonResult;
import com.enjoy.portal.domain.Erc20WithdrawVo;
import com.enjoy.portal.domain.EthWithdrawVo;

public interface AccountService {
    /**
     * 获得用户以太坊地址
     * @return
     */
    CommonResult findUserAccount();

    CommonResult updateUserEthAccount(EthWithdrawVo ethWithdrawVo);

    CommonResult updateUserErc20Account(Erc20WithdrawVo erc20WithdrawVo);
}
