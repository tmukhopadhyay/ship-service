package com.hpc.ship.models;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ship")
public class Ship {

	@Id
	@GeneratedValue
	@Type(type="uuid-char")
	@Column(name = "ShipID", nullable = false)
	private UUID shipId;

	@Column(name = "Code", nullable = false, unique = true)
	private String code;

	@Column(name = "Name", nullable = false)
	private String name;

	@Column(name = "Length", nullable = false)
	private float length;

	@Column(name = "Width", nullable = false)
	private float width;

	@Column(name = "CreatedDate", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "UpdatedDate", nullable = false)
	private LocalDateTime updatedDate;
}
