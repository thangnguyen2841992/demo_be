package com.example.demo.repository;

import com.example.demo.model.entity.merchant.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, Long> {
    @Query(value = "select * from merchants where user_id = ?1", nativeQuery = true)
    Optional<Merchant> findMerchantByUserId(Long userId);

}
