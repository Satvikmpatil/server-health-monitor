package com.monitor.simulator;

import com.monitor.model.ServerMetrics;
import com.monitor.service.MetricsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class MetricsSimulator {

    private final MetricsService metricsService;
    private final Random random = new Random();

    private final Map<String, ServerState> serverStates = new HashMap<>();

    public MetricsSimulator(MetricsService metricsService) {
        this.metricsService = metricsService;
        initializeServers();
    }

    private void initializeServers() {
        serverStates.put("srv-001", new ServerState("Production Web Server", 45, 55, 60));
        serverStates.put("srv-002", new ServerState("Database Server", 30, 70, 45));
        serverStates.put("srv-003", new ServerState("Application Server", 50, 60, 55));
    }

    @Scheduled(fixedRate = 5000)
    public void generateMetrics() {
        for (Map.Entry<String, ServerState> entry : serverStates.entrySet()) {
            String serverId = entry.getKey();
            ServerState state = entry.getValue();

            ServerMetrics metrics = new ServerMetrics();
            metrics.setServerId(serverId);
            metrics.setServerName(state.name);

            metrics.setCpuUsage(generateRealisticMetric(state.baseCpu));
            metrics.setMemoryUsage(generateRealisticMetric(state.baseMemory));
            metrics.setDiskUsage(generateSlowlyChangingMetric(state.baseDisk));
            metrics.setNetworkIn(generateNetworkMetric());
            metrics.setNetworkOut(generateNetworkMetric());

            metricsService.saveMetrics(metrics);
        }
    }

    private double generateRealisticMetric(double base) {
        double spike = random.nextDouble() < 0.1 ? random.nextDouble() * 30 : 0;
        double variation = (random.nextDouble() - 0.5) * 20;
        double value = base + variation + spike;
        return Math.max(0, Math.min(100, value));
    }

    private double generateSlowlyChangingMetric(double base) {
        double variation = (random.nextDouble() - 0.5) * 5;
        double value = base + variation;
        return Math.max(0, Math.min(100, value));
    }

    private double generateNetworkMetric() {
        return Math.round(random.nextDouble() * 1000 * 100.0) / 100.0;
    }

    private static class ServerState {
        String name;
        double baseCpu;
        double baseMemory;
        double baseDisk;

        ServerState(String name, double baseCpu, double baseMemory, double baseDisk) {
            this.name = name;
            this.baseCpu = baseCpu;
            this.baseMemory = baseMemory;
            this.baseDisk = baseDisk;
        }
    }
}
