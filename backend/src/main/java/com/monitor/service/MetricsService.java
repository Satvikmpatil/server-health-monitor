package com.monitor.service;

import com.monitor.model.ServerMetrics;
import com.monitor.model.ServerMetrics.ServerStatus;
import com.monitor.repository.MetricsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MetricsService {

    private static final double CPU_WARNING_THRESHOLD = 70.0;
    private static final double CPU_CRITICAL_THRESHOLD = 90.0;
    private static final double MEMORY_WARNING_THRESHOLD = 75.0;
    private static final double MEMORY_CRITICAL_THRESHOLD = 90.0;
    private static final double DISK_WARNING_THRESHOLD = 80.0;
    private static final double DISK_CRITICAL_THRESHOLD = 95.0;

    private final MetricsRepository metricsRepository;

    public MetricsService(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    public List<ServerMetrics> getLatestMetrics() {
        return metricsRepository.findLatestMetricsForAllServers();
    }

    public Optional<ServerMetrics> getLatestMetricsForServer(String serverId) {
        return metricsRepository.findTopByServerIdOrderByTimestampDesc(serverId);
    }

    public List<ServerMetrics> getMetricsHistory(String serverId, int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return metricsRepository.findByServerIdAndTimestampAfterOrderByTimestampAsc(serverId, since);
    }

    public List<ServerMetrics> getActiveAlerts() {
        LocalDateTime since = LocalDateTime.now().minusMinutes(5);
        List<ServerStatus> alertStatuses = Arrays.asList(ServerStatus.WARNING, ServerStatus.CRITICAL);
        return metricsRepository.findByStatusInAndTimestampAfter(alertStatuses, since);
    }

    public ServerMetrics saveMetrics(ServerMetrics metrics) {
        metrics.setStatus(calculateStatus(metrics));
        metrics.setTimestamp(LocalDateTime.now());
        return metricsRepository.save(metrics);
    }

    public ServerStatus calculateStatus(ServerMetrics metrics) {
        if (metrics.getCpuUsage() >= CPU_CRITICAL_THRESHOLD ||
            metrics.getMemoryUsage() >= MEMORY_CRITICAL_THRESHOLD ||
            metrics.getDiskUsage() >= DISK_CRITICAL_THRESHOLD) {
            return ServerStatus.CRITICAL;
        }

        if (metrics.getCpuUsage() >= CPU_WARNING_THRESHOLD ||
            metrics.getMemoryUsage() >= MEMORY_WARNING_THRESHOLD ||
            metrics.getDiskUsage() >= DISK_WARNING_THRESHOLD) {
            return ServerStatus.WARNING;
        }

        return ServerStatus.HEALTHY;
    }

    public List<String> getAllServerIds() {
        return metricsRepository.findDistinctServerIds();
    }

    public void cleanupOldMetrics(int daysToKeep) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(daysToKeep);
        metricsRepository.deleteByTimestampBefore(cutoff);
    }
}
