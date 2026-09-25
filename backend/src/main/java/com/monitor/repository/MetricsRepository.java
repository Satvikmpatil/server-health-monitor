package com.monitor.repository;

import com.monitor.model.ServerMetrics;
import com.monitor.model.ServerMetrics.ServerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MetricsRepository extends JpaRepository<ServerMetrics, Long> {

    // Find latest metrics for each server
    @Query("SELECT m FROM ServerMetrics m WHERE m.timestamp = " +
           "(SELECT MAX(m2.timestamp) FROM ServerMetrics m2 WHERE m2.serverId = m.serverId)")
    List<ServerMetrics> findLatestMetricsForAllServers();

    // Find latest metrics for a specific server
    Optional<ServerMetrics> findTopByServerIdOrderByTimestampDesc(String serverId);

    // Find metrics history for a server within a time range
    List<ServerMetrics> findByServerIdAndTimestampAfterOrderByTimestampAsc(
            String serverId, LocalDateTime since);

    // Find all metrics with WARNING or CRITICAL status
    List<ServerMetrics> findByStatusInAndTimestampAfter(
            List<ServerStatus> statuses, LocalDateTime since);

    // Find distinct server IDs
    @Query("SELECT DISTINCT m.serverId FROM ServerMetrics m")
    List<String> findDistinctServerIds();

    // Delete old metrics (for cleanup)
    void deleteByTimestampBefore(LocalDateTime before);
}
