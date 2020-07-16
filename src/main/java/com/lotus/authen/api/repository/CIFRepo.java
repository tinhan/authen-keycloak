package com.lotus.authen.api.repository;

import com.lotus.authen.api.repository.models.CustInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CIFRepo extends JpaRepository<CustInfo, Integer>{
    CustInfo findByMobileNumber(String mobileNumber);
}
