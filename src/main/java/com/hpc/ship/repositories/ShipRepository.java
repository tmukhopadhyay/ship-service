package com.hpc.ship.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hpc.ship.models.Ship;

@Repository
public interface ShipRepository extends JpaRepository<Ship, UUID> {

}
