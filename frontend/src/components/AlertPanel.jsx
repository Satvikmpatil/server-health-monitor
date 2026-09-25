import React from 'react';
import './AlertPanel.css';

function AlertPanel({ alerts }) {
  const formatTime = (timestamp) => {
    if (!timestamp) return '';
    const date = new Date(timestamp);
    return date.toLocaleTimeString('en-US', {
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit',
    });
  };

  const getAlertMessage = (metric) => {
    const issues = [];
    if (metric.cpuUsage >= 90) issues.push(`CPU at ${metric.cpuUsage.toFixed(1)}%`);
    else if (metric.cpuUsage >= 70) issues.push(`CPU at ${metric.cpuUsage.toFixed(1)}%`);

    if (metric.memoryUsage >= 90) issues.push(`Memory at ${metric.memoryUsage.toFixed(1)}%`);
    else if (metric.memoryUsage >= 75) issues.push(`Memory at ${metric.memoryUsage.toFixed(1)}%`);

    if (metric.diskUsage >= 95) issues.push(`Disk at ${metric.diskUsage.toFixed(1)}%`);
    else if (metric.diskUsage >= 80) issues.push(`Disk at ${metric.diskUsage.toFixed(1)}%`);

    return issues.join(', ') || 'Threshold exceeded';
  };

  if (alerts.length === 0) {
    return (
      <div className="alert-panel empty">
        <div className="no-alerts">
          <span className="check-icon">✓</span>
          <p>All systems healthy</p>
        </div>
      </div>
    );
  }

  const uniqueAlerts = alerts.reduce((acc, alert) => {
    const existing = acc.find(a => a.serverId === alert.serverId);
    if (!existing || new Date(alert.timestamp) > new Date(existing.timestamp)) {
      return [...acc.filter(a => a.serverId !== alert.serverId), alert];
    }
    return acc;
  }, []);

  return (
    <div className="alert-panel">
      {uniqueAlerts.map((alert, index) => (
        <div
          key={`${alert.serverId}-${index}`}
          className={`alert-item ${alert.status.toLowerCase()}`}
        >
          <div className="alert-indicator" />
          <div className="alert-content">
            <div className="alert-header">
              <span className="alert-server">{alert.serverName}</span>
              <span className="alert-time">{formatTime(alert.timestamp)}</span>
            </div>
            <div className="alert-message">{getAlertMessage(alert)}</div>
          </div>
        </div>
      ))}
    </div>
  );
}

export default AlertPanel;
