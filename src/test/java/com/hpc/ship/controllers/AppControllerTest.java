package com.hpc.ship.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hpc.ship.dto.ShipPayloadDto;
import com.hpc.ship.exceptions.ShipNotFoundException;
import com.hpc.ship.models.Ship;
import com.hpc.ship.services.AppService;

@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {

	private UUID shipId = UUID.fromString("518e4442-08c8-4e6c-9cb1-57bafb8b6a4f");
	private UUID invalidShipId = UUID.fromString("518e4442-08c8-4e6c-9cb1-57bafb8b6666");
	private Ship ship = new Ship(shipId, "AAAA-1111-A1", "Test Ship", 400, 50, LocalDateTime.now(),  LocalDateTime.now());
	private Ship newShip = new Ship(null, "AAAA-1111-A1", "Test Ship", 400, 50, LocalDateTime.now(),  LocalDateTime.now());
	private ShipPayloadDto shipPayloadDto = new ShipPayloadDto(shipId, "AAAA-1111-A1", "Test Ship", 400, 50);
	private ShipPayloadDto invalidShipPayloadDto = new ShipPayloadDto(invalidShipId, "AAAA-1111-A1", "Test Ship", 400, 50);
	private List<Ship> shipEntities = new ArrayList<>(Arrays.asList(ship));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AppService appService;

	@BeforeEach
	public void setUp() {
		Mockito.when(appService.getAllShips()).thenReturn(shipEntities);
		Mockito.when(appService.getShip(shipId)).thenReturn(ship);
		Mockito.when(appService.getShip(invalidShipId)).thenThrow(ShipNotFoundException.class);
		Mockito.when(appService.addShip(Mockito.any())).thenReturn(ship);
		Mockito.when(appService.updateShip(shipPayloadDto)).thenReturn(ship);
		Mockito.when(appService.updateShip(invalidShipPayloadDto)).thenThrow(ShipNotFoundException.class);
		Mockito.doNothing().when(appService).deleteShip(shipId);
		Mockito.doThrow(new ShipNotFoundException()).when(appService).deleteShip(invalidShipId);
	}

	@Test
	void shouldGetTheListOfShips() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/ships")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldGetAShipByAShipId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/ships/" + shipId)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldThrowANotFoundExceptionIfShipIdIsInvalidForGetShip() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/ships/" + invalidShipId)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void shouldThrowAnExceptionIfTheShipPayloadIsInvalid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/ships/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(ship)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void shouldAddAShip() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.post("/ships/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(newShip)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldThrowANotFoundExceptionIfShipIdIsInvalidForDeleteShip() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/ships/" + invalidShipId)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	void shouldDeleteAShip() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/ships/" + shipId)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void shouldThrowANotFoundExceptionIfShipIdIsInvalidForUpdateShip() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.put("/ships/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(invalidShipPayloadDto)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	void shouldUpdateAShip() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.put("/ships/")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(shipPayloadDto)))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
