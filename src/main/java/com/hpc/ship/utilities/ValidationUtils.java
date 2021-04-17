package com.hpc.ship.utilities;

import org.apache.commons.lang3.StringUtils;

import com.hpc.ship.dto.ShipPayloadDto;
import com.hpc.ship.exceptions.InvalidPayloadException;

public class ValidationUtils {

	private static final String SHIPCODE_PATTERN = "^[A-Z]{4}-[0-9]{4}-[A-Z][0-9]$";

	private ValidationUtils() {}

	public static void validateShipCreationPayload(ShipPayloadDto shipPayloadDto) {
		if(
			shipPayloadDto.getShipId() != null &&
			StringUtils.isNotBlank(shipPayloadDto.getShipId().toString())
		) {
			throw new InvalidPayloadException("A Ship ID is not acceptable while creating ship details");
		}
		
		validateCommonFields(shipPayloadDto);
	}

	public static void validateShipUpdationPayload(ShipPayloadDto shipPayloadDto) {
		if(
			shipPayloadDto.getShipId() == null ||
			StringUtils.isBlank(shipPayloadDto.getShipId().toString())
		) {
			throw new InvalidPayloadException("A Ship ID is required");
		}
		
		validateCommonFields(shipPayloadDto);
	}

	private static void validateCommonFields(ShipPayloadDto shipPayloadDto) {
		if(
			StringUtils.isBlank(shipPayloadDto.getCode()) ||
			!shipPayloadDto.getCode().matches(SHIPCODE_PATTERN)
		) {
			throw new InvalidPayloadException("Invalid ship code, should be in a format of AAAA-1111-A1 where A is any character from the Latin alphabet and 1 is a number from 0 to 9");
		}

		if(StringUtils.isBlank(shipPayloadDto.getName())) {
			throw new InvalidPayloadException("A name is required");
		}

		if(shipPayloadDto.getLength() <= 0) {
			throw new InvalidPayloadException("Invalid ship length");
		}

		if(shipPayloadDto.getWidth() <= 0) {
			throw new InvalidPayloadException("Invalid ship width");
		}
	}
}
