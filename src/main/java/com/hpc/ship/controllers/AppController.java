package com.hpc.ship.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hpc.ship.dto.ShipPayloadDto;
import com.hpc.ship.dto.ShipResponseDto;
import com.hpc.ship.models.Ship;
import com.hpc.ship.services.AppService;
import com.hpc.ship.utilities.TransformUtils;
import com.hpc.ship.utilities.ValidationUtils;

@CrossOrigin
@RestController
public class AppController {

	private final AppService appService;

	@Autowired
	public AppController(AppService appService) {
		this.appService = appService;
	}

	@GetMapping(value = "/ships", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ShipResponseDto>> getAllShips() {
		List<Ship> ships = appService.getAllShips();
		return new ResponseEntity<>(
			ships.parallelStream().map(TransformUtils::transformToShipResponseDto).collect(Collectors.toList()),
			HttpStatus.OK
		);
	}

	@GetMapping(value = "/ships/{shipId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ShipResponseDto> getShip(@PathVariable UUID shipId) {
		Ship ship = appService.getShip(shipId);
		return new ResponseEntity<>(
			TransformUtils.transformToShipResponseDto(ship),
			HttpStatus.OK
		);
	}

	@PostMapping(value = "/ships", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ShipResponseDto>> addShip(@RequestBody ShipPayloadDto shipPayloadDto) {
		ValidationUtils.validateShipCreationPayload(shipPayloadDto);
		List<Ship> ships = appService.addShip(TransformUtils.transformToShipEntity(shipPayloadDto));
		return new ResponseEntity<>(
			ships.parallelStream().map(TransformUtils::transformToShipResponseDto).collect(Collectors.toList()),
			HttpStatus.OK
		);
	}

	@DeleteMapping(value = "/ships/{shipId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ShipResponseDto>> deleteShip(@PathVariable UUID shipId) {
		List<Ship> ships = appService.deleteShip(shipId);
		return new ResponseEntity<>(
			ships.parallelStream().map(TransformUtils::transformToShipResponseDto).collect(Collectors.toList()),
			HttpStatus.OK
		);
	}

	@PutMapping(value = "/ships", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ShipResponseDto>> updateShip(@RequestBody ShipPayloadDto shipPayloadDto) {
		ValidationUtils.validateShipUpdationPayload(shipPayloadDto);
		List<Ship> ships = appService.updateShip(shipPayloadDto);
		return new ResponseEntity<>(
			ships.parallelStream().map(TransformUtils::transformToShipResponseDto).collect(Collectors.toList()),
			HttpStatus.OK
		);
	}
}
