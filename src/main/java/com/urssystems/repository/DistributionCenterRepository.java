package com.urssystems.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urssystems.model.DistributionCenter;

@Repository("distributionCenterRepository")
@Transactional
public interface DistributionCenterRepository extends JpaRepository<DistributionCenter, Integer> {

	public DistributionCenter findByDistributionCenterId(int distributionCenterId);
}
