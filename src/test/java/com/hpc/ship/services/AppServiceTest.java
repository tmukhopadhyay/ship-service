package com.hpc.ship.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;

import com.hpc.ship.dto.ShipPayloadDto;
import com.hpc.ship.exceptions.ShipNotFoundException;
import com.hpc.ship.models.Ship;
import com.hpc.ship.repositories.ShipRepository;

@SpringBootTest
public class AppServiceTest {

	private UUID shipId = UUID.fromString("518e4442-08c8-4e6c-9cb1-57bafb8b6a4f");
	private UUID invalidShipId = UUID.fromString("518e4442-08c8-4e6c-9cb1-57bafb8b6666");
	private Ship ship = new Ship(shipId, "AAAA-1111-A1", "Test Ship", 400, 50, LocalDateTime.now(),  LocalDateTime.now());
	private ShipPayloadDto shipPayloadDto = new ShipPayloadDto(shipId, "AAAA-1111-A1", "Test Ship", 400, 50);
	private ShipPayloadDto invalidShipPayloadDto = new ShipPayloadDto(invalidShipId, "AAAA-1111-A1", "Test Ship", 400, 50);
	private List<Ship> shipEntities = new ArrayList<>(Arrays.asList(ship));

	@MockBean
	private ShipRepository shipRepository;

	@TestConfiguration
	class AppServiceImplTestContextConfiguration {

		@Bean
		@Scope("prototype")
		public AppService appService() {
			return new AppServiceImpl(shipRepository);
		}
	}

	@Autowired
	private AppService appService;

	@BeforeEach
	void setUp() {
		Mockito.when(shipRepository.findAll(Sort.by(Sort.Direction.DESC, "updatedDate"))).thenReturn(shipEntities);
		Mockito.when(shipRepository.findById(shipId)).thenReturn(Optional.of(ship));
		Mockito.when(shipRepository.findById(invalidShipId)).thenReturn(Optional.empty());
		Mockito.when(shipRepository.saveAndFlush(ship)).thenReturn(ship);
	}

	@Test
	void shouldGetAllShips() {
		assertThat(appService.getAllShips()).hasSize(shipEntities.size());
	}

	@Test
	void shouldGetAShipByItsId() {
		Ship shipEntity = appService.getShip(shipId);
		assertThat(shipEntity).isNotNull();
		assertEquals(shipEntity.getCode(), ship.getCode());
	}

	@Test
	void shouldThrowAnErrorIfTheShipNotAvailableWhileSearchingAShipByAnId() {
		assertThrows(ShipNotFoundException.class, () -> {
			appService.getShip(invalidShipId);
		});
	}

	@Test
	void shouldThrowAnErrorIfTheShipNotAvailableWhileUpdatingShipDetails() {
		assertThrows(ShipNotFoundException.class, () -> {
			appService.updateShip(invalidShipPayloadDto);
		});
	}

	@Test
	void shouldUpdateShipDetails() {
		assertEquals(appService.updateShip(shipPayloadDto).getShipId(), shipPayloadDto.getShipId());
	}

	@Test
	void shouldThrowAnErrorIfTheShipNotAvailableWhileDeletingAShipById() {
		assertThrows(ShipNotFoundException.class, () -> {
			appService.deleteShip(invalidShipId);
		});
	}
}
