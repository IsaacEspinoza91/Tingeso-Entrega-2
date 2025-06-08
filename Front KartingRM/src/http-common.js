import axios from "axios";

const API_URL = import.meta.env.VITE_BACK_URL;

const httpClient = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json"
  },
  withCredentials: false // Importante para CORS
});


// Interceptor para manejar errores globalmente
httpClient.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 403) {
      console.error("Acceso prohibido - Verifique CORS");
    }
    return Promise.reject(error);
  }
);

export default httpClient;