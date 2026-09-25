package com.monitor.controller;

import com.monitor.model.ServerMetrics;
import com.monitor.service.MetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
@CrossOrigin(origins = "http://localhost:5173")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ServerMetrics>> getLatestMetrics() {
        List<ServerMetrics> metrics = metricsService.getLatestMetrics();
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/server/{serverId}")
    public ResponseEntity<ServerMetrics> getServerMetrics(@PathVariable String serverId) {
        return metricsService.getLatestMetricsForServer(serverId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/history/{serverId}")
    public ResponseEntity<List<ServerMetrics>> getMetricsHistory(
            @PathVariable String serverId,
            @RequestParam(defaultValue = "1") int hours) {
        List<ServerMetrics> history = metricsService.getMetricsHistory(serverId, hours);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<ServerMetrics>> getActiveAlerts() {
        List<ServerMetrics> alerts = metricsService.getActiveAlerts();
        return ResponseEntity.ok(alerts);
    }

    @PostMapping
    public ResponseEntity<ServerMetrics> ingestMetrics(@RequestBody ServerMetrics metrics) {
        ServerMetrics saved = metricsService.saveMetrics(metrics);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/servers")
    public ResponseEntity<List<String>> getAllServers() {
        List<String> serverIds = metricsService.getAllServerIds();
        return ResponseEntity.ok(serverIds);
    }
}
