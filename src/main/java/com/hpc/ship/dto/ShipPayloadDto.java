package com.hpc.ship.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipPayloadDto {

	private UUID shipId;
	private String code;
	private String name;
	private float length;
	private float width;
}
