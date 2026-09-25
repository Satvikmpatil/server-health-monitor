import React, { useState, useEffect } from 'react';
import { metricsApi } from '../services/api';
import MetricCard from './MetricCard';
import AlertPanel from './AlertPanel';
import HistoryChart from './HistoryChart';
import './Dashboard.css';

function Dashboard() {
  const [metrics, setMetrics] = useState([]);
  const [alerts, setAlerts] = useState([]);
  const [selectedServer, setSelectedServer] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
      const [metricsRes, alertsRes] = await Promise.all([
        metricsApi.getLatestMetrics(),
        metricsApi.getAlerts(),
      ]);

      setMetrics(metricsRes.data);
      setAlerts(alertsRes.data);

      if (!selectedServer && metricsRes.data.length > 0) {
        setSelectedServer(metricsRes.data[0].serverId);
      }

      setError(null);
    } catch (err) {
      setError('Failed to fetch metrics. Is the backend running?');
      console.error('Error fetching metrics:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
    const interval = setInterval(fetchData, 5000);
    return () => clearInterval(interval);
  }, []);

  if (loading) {
    return (
      <div className="dashboard-loading">
        <div className="spinner"></div>
        <p>Loading metrics...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="dashboard-error">
        <p>{error}</p>
        <button onClick={fetchData}>Retry</button>
      </div>
    );
  }

  return (
    <div className="dashboard">
      <div className="dashboard-grid">
        <section className="servers-section">
          <h2>Servers</h2>
          <div className="server-cards">
            {metrics.map((metric) => (
              <MetricCard
                key={metric.serverId}
                metric={metric}
                isSelected={selectedServer === metric.serverId}
                onClick={() => setSelectedServer(metric.serverId)}
              />
            ))}
          </div>
        </section>

        <section className="alerts-section">
          <h2>Active Alerts</h2>
          <AlertPanel alerts={alerts} />
        </section>
      </div>

      {selectedServer && (
        <section className="chart-section">
          <h2>Performance History - {metrics.find(m => m.serverId === selectedServer)?.serverName}</h2>
          <HistoryChart serverId={selectedServer} />
        </section>
      )}
    </div>
  );
}

export default Dashboard;
