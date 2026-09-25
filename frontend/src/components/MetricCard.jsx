import React from 'react';
import './MetricCard.css';

function MetricCard({ metric, isSelected, onClick }) {
  const getStatusColor = (status) => {
    switch (status) {
      case 'CRITICAL':
        return '#ef4444';
      case 'WARNING':
        return '#f59e0b';
      default:
        return '#22c55e';
    }
  };

  const getProgressColor = (value) => {
    if (value >= 90) return '#ef4444';
    if (value >= 70) return '#f59e0b';
    return '#3b82f6';
  };

  const formatValue = (value) => {
    return typeof value === 'number' ? value.toFixed(1) : '0.0';
  };

  return (
    <div
      className={`metric-card ${isSelected ? 'selected' : ''}`}
      onClick={onClick}
    >
      <div className="card-header">
        <div className="server-info">
          <h3>{metric.serverName}</h3>
          <span className="server-id">{metric.serverId}</span>
        </div>
        <div
          className="status-badge"
          style={{ backgroundColor: getStatusColor(metric.status) }}
        >
          {metric.status}
        </div>
      </div>

      <div className="metrics-grid">
        <div className="metric-item">
          <div className="metric-label">CPU</div>
          <div className="metric-value">{formatValue(metric.cpuUsage)}%</div>
          <div className="progress-bar">
            <div
              className="progress-fill"
              style={{
                width: `${metric.cpuUsage}%`,
                backgroundColor: getProgressColor(metric.cpuUsage),
              }}
            />
          </div>
        </div>

        <div className="metric-item">
          <div className="metric-label">Memory</div>
          <div className="metric-value">{formatValue(metric.memoryUsage)}%</div>
          <div className="progress-bar">
            <div
              className="progress-fill"
              style={{
                width: `${metric.memoryUsage}%`,
                backgroundColor: getProgressColor(metric.memoryUsage),
              }}
            />
          </div>
        </div>

        <div className="metric-item">
          <div className="metric-label">Disk</div>
          <div className="metric-value">{formatValue(metric.diskUsage)}%</div>
          <div className="progress-bar">
            <div
              className="progress-fill"
              style={{
                width: `${metric.diskUsage}%`,
                backgroundColor: getProgressColor(metric.diskUsage),
              }}
            />
          </div>
        </div>
      </div>

      <div className="network-stats">
        <div className="network-item">
          <span className="network-label">Net In:</span>
          <span className="network-value">{formatValue(metric.networkIn)} KB/s</span>
        </div>
        <div className="network-item">
          <span className="network-label">Net Out:</span>
          <span className="network-value">{formatValue(metric.networkOut)} KB/s</span>
        </div>
      </div>
    </div>
  );
}

export default MetricCard;
