package com.monitor.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "server_metrics")
public class ServerMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "server_id", nullable = false)
    private String serverId;

    @Column(name = "server_name", nullable = false)
    private String serverName;

    @Column(name = "cpu_usage", nullable = false)
    private Double cpuUsage;

    @Column(name = "memory_usage", nullable = false)
    private Double memoryUsage;

    @Column(name = "disk_usage", nullable = false)
    private Double diskUsage;

    @Column(name = "network_in")
    private Double networkIn;

    @Column(name = "network_out")
    private Double networkOut;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ServerStatus status;

    public enum ServerStatus {
        HEALTHY, WARNING, CRITICAL
    }

    // Default constructor
    public ServerMetrics() {
    }

    // Constructor with fields
    public ServerMetrics(String serverId, String serverName, Double cpuUsage,
                         Double memoryUsage, Double diskUsage, Double networkIn,
                         Double networkOut, LocalDateTime timestamp, ServerStatus status) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.diskUsage = diskUsage;
        this.networkIn = networkIn;
        this.networkOut = networkOut;
        this.timestamp = timestamp;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public Double getNetworkIn() {
        return networkIn;
    }

    public void setNetworkIn(Double networkIn) {
        this.networkIn = networkIn;
    }

    public Double getNetworkOut() {
        return networkOut;
    }

    public void setNetworkOut(Double networkOut) {
        this.networkOut = networkOut;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public ServerStatus getStatus() {
        return status;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
    }
}
