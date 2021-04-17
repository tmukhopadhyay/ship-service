package com.hpc.ship.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipResponseDto {

	private UUID shipId;
	private String code;
	private String name;
	private float length;
	private float width;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
}
