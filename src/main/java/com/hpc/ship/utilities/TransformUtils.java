package com.hpc.ship.utilities;

import java.time.LocalDateTime;

import com.hpc.ship.dto.ShipPayloadDto;
import com.hpc.ship.dto.ShipResponseDto;
import com.hpc.ship.models.Ship;

public class TransformUtils {

	private TransformUtils() {}

	public static Ship transformToShipEntity(ShipPayloadDto shipPayloadDto) {
		return new Ship(
			shipPayloadDto.getShipId(),
			shipPayloadDto.getCode(),
			shipPayloadDto.getName(),
			shipPayloadDto.getLength(),
			shipPayloadDto.getWidth(),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}

	public static Ship transformToShipEntity(Ship ship, ShipPayloadDto shipPayloadDto) {
		ship.setCode(shipPayloadDto.getCode());
		ship.setLength(shipPayloadDto.getLength());
		ship.setName(shipPayloadDto.getName());
		ship.setUpdatedDate(LocalDateTime.now());
		ship.setWidth(shipPayloadDto.getWidth());
		return ship;
	}

	public static ShipResponseDto transformToShipResponseDto(Ship ship) {
		return new ShipResponseDto(
			ship.getShipId(),
			ship.getCode(),
			ship.getName(),
			ship.getLength(),
			ship.getWidth(),
			ship.getCreatedDate(),
			ship.getUpdatedDate()
		);
	}
}
