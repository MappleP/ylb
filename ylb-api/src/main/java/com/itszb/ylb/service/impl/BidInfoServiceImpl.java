package com.itszb.ylb.service.impl;

import com.itszb.ylb.beans.*;
import com.itszb.ylb.exception.MsgTipException;
import com.itszb.ylb.mapper.AccountMapper;
import com.itszb.ylb.mapper.BidInfoMapper;
import com.itszb.ylb.mapper.IncomeMapper;
import com.itszb.ylb.mapper.ProductMapper;
import com.itszb.ylb.service.BidInfoService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @projectName: ylb
 * @package: com.itszb.ylb.service.impl
 * @className: BIdInfoServiceImpl
 * @author: 彭
 * @description: TODO
 * @date: 2024/1/22 14:28
 * @version: 1.0
 */
@Service
public class BidInfoServiceImpl implements BidInfoService {
    @Resource
    private BidInfoMapper bidInfoMapper;
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private IncomeMapper incomeMapper;

    @Override
    public List<Map> top3() {
        return bidInfoMapper.top3();
    }

    @Override
    public List<RecordOfProd> recordsOfProd(Long id) {
        return bidInfoMapper.recordsOfProd(id);
    }

    @Override
    @Transactional// 事务处理
    public void bid(Double bidMoney, Long pid, Long uid) {
        // 扣减账户余额
        if (accountMapper.deductMoney(uid, bidMoney) == 0) {//这里表示修改的行数为0，即余额不足
            throw new MsgTipException("账户余额不足！");
        }
        // 扣减产品余额
        if (productMapper.deductMoney(uid, bidMoney) == 0) {//跟上面一样
            throw new MsgTipException("产品余额不足！");
        }
        // 添加投资记录
        Date now = new Date();
        BidInfo bidInfo = new BidInfo();//投资记录
        bidInfo.setProdId(pid);//设置产品id
        bidInfo.setUid(uid);//设置用户id
        bidInfo.setBidMoney(bidMoney);//设置投资金额
        bidInfo.setBidTime(now);//设置投资时间
        bidInfoMapper.add(bidInfo);//加入表格中
        // 判断产品是否“满标”，更新产品的状态为满标，和满标时间，并生成收益计划
        Product product = productMapper.getById(pid);
        if (product.getLeftProductMoney() < product.getBidMinLimit()) {
            // 更新产品的状态为满标，和满标时间
            productMapper.setFull(pid, now);
            // 根据当前产品的所有投资记录生成收益计划
            List<BidInfo> bidInfos = bidInfoMapper.getByProdId(pid);
            for (BidInfo info : bidInfos) {
                Income income = new Income();
                income.setBidMoney(info.getBidMoney());
                income.setUid(info.getUid());
                income.setProdId(info.getProdId());
                income.setBidId(info.getId());
                // 收益金额=投资金额*产品利率/100/12*产品的周期
                Double incomeMoney = info.getBidMoney() * product.getRate() / 100 / 12 * product.getCycle();
                income.setIncomeMoney(incomeMoney);

                long time = info.getBidTime().getTime();
                long shiftingTime = 1000L * 60 * 60 * 24 * 30 * product.getCycle();
                Date incomeDate = new Date(time + shiftingTime);
                income.setIncomeDate(incomeDate);
                income.setIncomeStatus(0);

                incomeMapper.add(income);
            }
        }
    }

    @Override
    public List<RecordOfUser> recordsOfUser(Long id) {
        return bidInfoMapper.recordsOfUser(id);
    }
}
