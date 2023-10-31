import axios from 'axios';
import { useAuthStore } from '@/stores';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL
});

api.interceptors.request.use((config) => {
  const { user } = useAuthStore();
  const token = user?.data.accessToken;

  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
});

function request(method) {
  return (url, body) => {
    const requestOptions = { method }

    if (body) {
      requestOptions.headers = { 'Content-Type': 'application/json' };
      requestOptions.data = body;
    }
    return api(url, requestOptions).then(response => response.data);
  }
}

export const http = {
  get: request('GET'),
  post: request('POST'),
  put: request('PUT'),
  delete: request('DELETE')
};
