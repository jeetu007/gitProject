package com.urssystems.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.urssystems.DTO.OrderSupplierDTO;
import com.urssystems.model.OrderSupplier;
import com.urssystems.repository.OrderSupplierRepository;

@Service("orderSupplierService")
public class OrderSupplierService {

	@Autowired
	OrderSupplierRepository orderSupplierRepository;

	@Autowired
	ModelMapper modelMapper;

	public int getOrderSupplierSize(int userId, int distributionCenterId, int pageNo, int pageSize) {

		try {
			List<OrderSupplier> orderSupplierList = orderSupplierRepository
					.findByVendorIdAndDistributionCenterIdAndOrderStatusNotContainingIgnoreCase(userId,
							distributionCenterId, "CLOSED");
			int orderSupplierListSize = orderSupplierList.size();
			return orderSupplierListSize;
		} catch (Exception e) {
			return 0;
		}
	}// getOrderSupplierSize()

	@SuppressWarnings("deprecation")
	public List<OrderSupplierDTO> getAllOrders(int userId, int distributionCenterId, int pageNo, int pageSize) {
		try {
			Pageable pageable = new PageRequest(pageNo - 1, pageSize, Direction.DESC, "createdOn");
			List<OrderSupplier> orderSupplierList = orderSupplierRepository
					.findByVendorIdAndDistributionCenterIdAndOrderStatusNotContainingIgnoreCase(userId,
							distributionCenterId, "CLOSED", pageable);
			List<OrderSupplierDTO> orderSupplierDTOList = new ArrayList<OrderSupplierDTO>();
			int orderSize = getOrderSupplierSize(userId, distributionCenterId, pageNo, pageSize);
			orderSupplierList.forEach(orderSupplier -> {
				OrderSupplierDTO orderSupplierDTO = modelMapper.map(orderSupplier, OrderSupplierDTO.class);
				orderSupplierDTO.setTotalNoOfOrdersRecords(orderSize);
				orderSupplierDTOList.add(orderSupplierDTO);
			});

			return orderSupplierDTOList;

		} catch (Exception e) {
			return null;
		}
	}// getAllOrders(-)

	public List<OrderSupplierDTO> getOrders(String[] orderSupplierId) {

		try {
			List<OrderSupplierDTO> orderSupplierDTOList = new ArrayList<OrderSupplierDTO>();

			for (String orderId : orderSupplierId) {
				orderSupplierDTOList
						.add(modelMapper.map(orderSupplierRepository.findByOrderSupplierId(Integer.parseInt(orderId)),
								OrderSupplierDTO.class));
			}
			return orderSupplierDTOList;
		} catch (Exception e) {
			return null;
		}
	}// getOrders(-)

	public boolean updateOrderSupplierStatus(String ordersId, String orderStatus, String appointmentStatus) {
		try {
			String[] orderId = ordersId.split(",");
			List<OrderSupplierDTO> orderSupplierDTOList = getOrders(orderId);

			orderSupplierDTOList.forEach(orderSupplierDTO -> {
				orderSupplierDTO.setAppointmentStatus(appointmentStatus);
				orderSupplierDTO.setOrderStatus(orderStatus);
				orderSupplierDTO.setModifiedOn(new Date(System.currentTimeMillis()));
				orderSupplierRepository.save(modelMapper.map(orderSupplierDTO, OrderSupplier.class));
			});

			return true;
		} catch (Exception e) {
			return false;
		}

	}// updateOrderSupplierStatus(-,-,-)

	public OrderSupplierDTO getOrder(String orderNumber) {
		try {
			OrderSupplier order = orderSupplierRepository.findByOrderNumber(orderNumber);
			OrderSupplierDTO orderDTO = modelMapper.map(order, OrderSupplierDTO.class);
			return orderDTO;
		} catch (Exception e) {
			return null;
		}
	}//getOrder(-)

}// class
