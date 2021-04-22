package com.hpc.ship.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hpc.ship.constants.AppConstants;
import com.hpc.ship.dto.ShipPayloadDto;
import com.hpc.ship.exceptions.ShipNotFoundException;
import com.hpc.ship.models.Ship;
import com.hpc.ship.repositories.ShipRepository;
import com.hpc.ship.utilities.TransformUtils;

@Service
public class AppServiceImpl implements AppService {
	
	private final ShipRepository shipRepository;

	@Autowired
	public AppServiceImpl(ShipRepository shipRepository) {
		this.shipRepository = shipRepository;
	}

	@Override
	public List<Ship> getAllShips() {
		return shipRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedDate"));
	}

	@Override
	public Ship getShip(UUID shipId) {
		return shipRepository
				.findById(shipId)
				.orElseThrow(() -> new ShipNotFoundException(AppConstants.SHIP_NOT_FOUND_ERR_MESSAGE + shipId));
	}

	@Override
	@Transactional
	public Ship addShip(Ship ship) {
		return shipRepository.saveAndFlush(ship);
	}

	@Override
	@Transactional
	public void deleteShip(UUID shipId) {
		if(!shipRepository.existsById(shipId)) {
			throw new ShipNotFoundException(AppConstants.SHIP_NOT_FOUND_ERR_MESSAGE + shipId);
		}
		
		shipRepository.deleteById(shipId);
	}

	@Override
	@Transactional
	public Ship updateShip(ShipPayloadDto shipPayloadDto) {
		UUID shipId = shipPayloadDto.getShipId();
		Ship ship = shipRepository
				.findById(shipId)
				.orElseThrow(() -> new ShipNotFoundException(AppConstants.SHIP_NOT_FOUND_ERR_MESSAGE + shipId));

		return shipRepository.saveAndFlush(TransformUtils.transformToShipEntity(ship, shipPayloadDto));
	}
}
