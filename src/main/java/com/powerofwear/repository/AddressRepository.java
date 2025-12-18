package com.powerofwear.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.powerofwear.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
