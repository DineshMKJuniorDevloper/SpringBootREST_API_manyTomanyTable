package com.example.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
