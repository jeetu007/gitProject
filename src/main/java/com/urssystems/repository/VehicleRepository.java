package com.urssystems.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urssystems.model.Vehicle;

@Repository("vehicleRepository")
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	public Vehicle findByVehicleId(int vehicleId);
}
