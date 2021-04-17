package com.hpc.ship.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.hpc.ship.dto.ShipPayloadDto;
import com.hpc.ship.models.Ship;

@Service
public interface AppService {

	public List<Ship> getAllShips();

	public Ship getShip(UUID shipId);

	public List<Ship> addShip(Ship ship);
	
	public List<Ship> deleteShip(UUID shipId);

	public List<Ship> updateShip(ShipPayloadDto shipPayloadDto);
}
