import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const metricsApi = {
  getLatestMetrics: () => api.get('/metrics/latest'),

  getServerMetrics: (serverId) => api.get(`/metrics/server/${serverId}`),

  getMetricsHistory: (serverId, hours = 1) =>
    api.get(`/metrics/history/${serverId}`, { params: { hours } }),

  getAlerts: () => api.get('/metrics/alerts'),

  getAllServers: () => api.get('/metrics/servers'),
};

export default api;
