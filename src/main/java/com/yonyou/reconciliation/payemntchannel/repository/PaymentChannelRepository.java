package com.yonyou.reconciliation.payemntchannel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.yonyou.reconciliation.payemntchannel.entity.PaymentChannel;

@Repository
public interface PaymentChannelRepository extends JpaRepository<PaymentChannel, Long>, JpaSpecificationExecutor<PaymentChannel> {

}
