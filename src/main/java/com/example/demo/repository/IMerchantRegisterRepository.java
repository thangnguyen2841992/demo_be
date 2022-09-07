package com.example.demo.repository;

import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.merchant.MerchantRegisterRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMerchantRegisterRepository extends JpaRepository<MerchantRegisterRequest, Long> {
    Optional<MerchantRegisterRequest> findByUserAndReviewed(User user, boolean reviewed);

    Iterable<MerchantRegisterRequest> findMerchantByReviewed(boolean reviewed);


}
