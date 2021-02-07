package com.urssystems.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urssystems.DTO.VehicleDTO;
import com.urssystems.model.Vehicle;
import com.urssystems.repository.VehicleRepository;

@Service("vehicleService")
public class VehicleService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	VehicleRepository vehicleRepository;

	public VehicleDTO getVehicleDetail(int vehicleId) {
		try {
			VehicleDTO vehicleDTO = modelMapper.map(vehicleRepository.findByVehicleId(vehicleId), VehicleDTO.class);
			return vehicleDTO;
		} catch (Exception e) {
			return null;
		}
	}// getVehicleDetail(-)

	public boolean updateVehicleStatus(int vehicleId, String vehicleStatus) {
		try {
			Vehicle vehicle = vehicleRepository.findByVehicleId(vehicleId);
			vehicle.setVehicleStatus(vehicleStatus);
			vehicle.setModifiedOn(new Date(System.currentTimeMillis()));
			Vehicle updatedVehicle = vehicleRepository.save(vehicle);
			if (updatedVehicle == null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}//updateVehicleStatus(-,-)
	
}// class
