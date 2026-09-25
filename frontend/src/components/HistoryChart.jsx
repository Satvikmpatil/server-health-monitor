import React, { useState, useEffect } from 'react';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';
import { metricsApi } from '../services/api';
import './HistoryChart.css';

function HistoryChart({ serverId }) {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchHistory = async () => {
      try {
        const response = await metricsApi.getMetricsHistory(serverId, 1);
        const formattedData = response.data.map((item) => ({
          time: new Date(item.timestamp).toLocaleTimeString('en-US', {
            hour: '2-digit',
            minute: '2-digit',
          }),
          cpu: parseFloat(item.cpuUsage.toFixed(1)),
          memory: parseFloat(item.memoryUsage.toFixed(1)),
          disk: parseFloat(item.diskUsage.toFixed(1)),
        }));
        setHistory(formattedData);
      } catch (err) {
        console.error('Error fetching history:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchHistory();
    const interval = setInterval(fetchHistory, 10000);
    return () => clearInterval(interval);
  }, [serverId]);

  if (loading) {
    return <div className="chart-loading">Loading chart...</div>;
  }

  if (history.length === 0) {
    return (
      <div className="chart-empty">
        <p>Collecting data... Chart will appear shortly.</p>
      </div>
    );
  }

  return (
    <div className="history-chart">
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={history} margin={{ top: 10, right: 30, left: 0, bottom: 0 }}>
          <CartesianGrid strokeDasharray="3 3" stroke="rgba(255,255,255,0.1)" />
          <XAxis
            dataKey="time"
            stroke="#71717a"
            tick={{ fill: '#a1a1aa', fontSize: 12 }}
            tickLine={{ stroke: '#71717a' }}
          />
          <YAxis
            domain={[0, 100]}
            stroke="#71717a"
            tick={{ fill: '#a1a1aa', fontSize: 12 }}
            tickLine={{ stroke: '#71717a' }}
            tickFormatter={(value) => `${value}%`}
          />
          <Tooltip
            contentStyle={{
              backgroundColor: '#1a1a2e',
              border: '1px solid rgba(255,255,255,0.1)',
              borderRadius: '8px',
              color: '#e4e4e7',
            }}
            labelStyle={{ color: '#fff', fontWeight: 600 }}
          />
          <Legend
            wrapperStyle={{ paddingTop: '20px' }}
            iconType="circle"
          />
          <Line
            type="monotone"
            dataKey="cpu"
            name="CPU"
            stroke="#3b82f6"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 4 }}
          />
          <Line
            type="monotone"
            dataKey="memory"
            name="Memory"
            stroke="#8b5cf6"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 4 }}
          />
          <Line
            type="monotone"
            dataKey="disk"
            name="Disk"
            stroke="#22c55e"
            strokeWidth={2}
            dot={false}
            activeDot={{ r: 4 }}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}

export default HistoryChart;
