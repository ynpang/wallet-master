package com.enjoy.portal.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Erc20WithdrawVo {
    private String acount;
    private Double amount;
    private String ContractAddress;
}
