package com.urssystems.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urssystems.model.OrderSupplier;

@Repository("orderSupplierRepository")
@Transactional
public interface OrderSupplierRepository extends JpaRepository<OrderSupplier, Integer> {

	public OrderSupplier findByOrderSupplierId(int orderSupplierId);
	
	public OrderSupplier findByOrderNumber(String orderNumber);

	public List<OrderSupplier> findByVendorIdAndDistributionCenterIdAndOrderStatusNotContainingIgnoreCase(int vendorId,int distributionCenterId,String orderStatus);
	
	public List<OrderSupplier> findByVendorIdAndDistributionCenterIdAndOrderStatusNotContainingIgnoreCase(int vendorId,int distributionCenterId,String orderStatus,Pageable pageable);
}
