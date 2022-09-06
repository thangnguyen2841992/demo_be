package com.example.demo.repository;

import com.example.demo.model.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, Long> {

}
