import httpClient from '../http-common';
import axios from 'axios';


const URL_LOCAL = '/api/plan/planes';
const API_URL = 'http://localhost:8080/api/plan/planes/';

// Peticion GET de todos los planes
export const getPlanes = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error al obtener planes:', error);
    throw error;
  }
};

/// Peticion GET de plan segun id
export const getPlanById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error al obtener plan con ID ${idPlan}:`, error);
    throw error;
  }
};

// Peticion POST para crear plan
export const createPlan = async (planData) => {
  try {
    const response = await axios.post(API_URL, planData);
    return response.data;
  } catch (error) {
    console.error('Error al crear plan:', error);
    throw error;
  }
};

// Peticion PUT para update de plan, segun id y body
export const updatePlan = async (id, planData) => {
  try {
    const response = await axios.put(`${API_URL}${id}`, planData);
    return response.data;
  } catch (error) {
    console.error(`Error al actualizar plan con ID ${idPlan}:`, error);
    throw error;
  }
};

// Peticion DELETE para eliminar plan
export const deletePlan = async (id) => {
  try {
    const response = await axios.delete(`${API_URL}${id}`);
    return response.data;
  } catch (error) {
    console.error(`Error al eliminar plan con ID ${idPlan}:`, error);
    throw error;
  }
};