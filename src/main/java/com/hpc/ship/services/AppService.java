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

	public Ship addShip(Ship ship);
	
	public void deleteShip(UUID shipId);

	public Ship updateShip(ShipPayloadDto shipPayloadDto);
}
