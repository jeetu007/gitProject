package com.urssystems.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.urssystems.model.DistributionCenterDOC;

@Repository("distributionCenterDOCRepository")
@Transactional
public interface DistributionCenterDOCRepository extends JpaRepository<DistributionCenterDOC, Integer> {

}
