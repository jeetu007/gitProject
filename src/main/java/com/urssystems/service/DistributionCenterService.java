package com.urssystems.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urssystems.DTO.AppointmentDTO;
import com.urssystems.DTO.DistributionCenterDTO;
import com.urssystems.model.DistributionCenter;
import com.urssystems.repository.DistributionCenterRepository;

@Service("distributionCenterService")
public class DistributionCenterService {

	@Autowired
	DistributionCenterRepository distributionCenterRepository;

	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	ModelMapper modelMapper;

	public DistributionCenterDTO getDistributionCenter(int distributionCenterId) {
		try {
			DistributionCenterDTO distributionCenterDTO = modelMapper.map(
					distributionCenterRepository.findByDistributionCenterId(distributionCenterId),
					DistributionCenterDTO.class);
			return distributionCenterDTO;
		} catch (Exception e) {
			return null;
		}
	}// getDistributionCenter(-)

	public List<DistributionCenterDTO> getDistributionCenterList() {
		try {
			List<DistributionCenterDTO> distributionCenterDTOList = new ArrayList<DistributionCenterDTO>();
			List<DistributionCenter> distributionCenterList = distributionCenterRepository.findAll();

			distributionCenterList.forEach(distributionCenter -> {
				distributionCenterDTOList.add(modelMapper.map(distributionCenter, DistributionCenterDTO.class));
			});
			return distributionCenterDTOList;
		} catch (Exception e) {
			return null;
		}
	}// getDistributionCenterList()

	public DistributionCenterDTO getDistributionCenterForSecurity(int securityId, int pageNo, int pageSize) {

		try {
			boolean dcMatch = false;
			DistributionCenterDTO distributionCenterDTO = null;

			List<DistributionCenter> distributionCenterList = distributionCenterRepository.findAll();

			for (DistributionCenter distributionCenter : distributionCenterList) {

				String[] securityIds = distributionCenter.getAssignedSecurity().split(",");

				for (String str : securityIds) {
					if (Integer.parseInt(str) == securityId) {
						dcMatch = true;
					}
				}

				if (dcMatch) {

					List<AppointmentDTO> appointmentDTOList = appointmentService
							.getAppointmentForDC_DOC(distributionCenter.getDistributionCenterId(), pageNo, pageSize);

					distributionCenterDTO = modelMapper.map(distributionCenter, DistributionCenterDTO.class);
					distributionCenterDTO.setAppointmentDTOList(appointmentDTOList);
					break;
				}

			}
			return distributionCenterDTO;
		} catch (Exception e) {
			return null;
		}

	}// getDistributionCenterForSecurity(-)

}
