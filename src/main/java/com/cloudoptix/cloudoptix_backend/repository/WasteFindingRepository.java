package com.cloudoptix.cloudoptix_backend.repository;

import com.cloudoptix.cloudoptix_backend.entity.WasteFinding;
import com.cloudoptix.cloudoptix_backend.entity.CloudAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
    import java.time.LocalDateTime;
import java.util.List;

public interface WasteFindingRepository extends JpaRepository<WasteFinding, Long> {

    List<WasteFinding> findByCloudAccount(CloudAccount cloudAccount);


List<WasteFinding> findByCloudAccountAndDetectedAtBetween(
        CloudAccount cloudAccount,
        LocalDateTime from,
        LocalDateTime to
);

}


