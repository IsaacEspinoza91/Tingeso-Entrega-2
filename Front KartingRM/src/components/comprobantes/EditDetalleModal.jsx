import React, { useState, useEffect } from 'react';
import { updateDetalle } from '../../services/comprobanteService';
import './EditDetalleModal.css';

const EditDetalleModal = ({ detalle, onClose, onUpdate }) => {
    const [formData, setFormData] = useState({
        descuentoExtra: 0,
        porcentajeDescuentoEspecial: 0
    });

    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
        if (detalle) {
            setFormData({
                descuentoExtra: detalle.descuentoExtra || 0,
                porcentajeDescuentoEspecial: detalle.porcentajeDescuentoEspecial || 0
            });
        }
    }, [detalle]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: Number(value)
        });
    };

    const validateForm = () => {
        const newErrors = {};

        if (formData.descuentoExtra < 0) {
            newErrors.descuentoExtra = 'El descuento extra no puede ser negativo';
        }
        if (formData.porcentajeDescuentoEspecial < 0 || formData.porcentajeDescuentoEspecial > 100) {
            newErrors.porcentajeDescuentoEspecial = 'El porcentaje debe estar entre 0 y 100';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!validateForm()) return;

        setIsSubmitting(true);
        setErrors({});

        try {
            const updatedDetalle = await updateDetalle(detalle.id, formData);
            setSuccessMessage('Detalle actualizado correctamente!');

            setTimeout(() => {
                onUpdate(updatedDetalle);
                onClose();
            }, 1500);
        } catch (error) {
            console.error('Error al actualizar detalle:', error);
            setErrors({
                submit: error.response?.data?.message || 'Error al actualizar detalle. Intente nuevamente.'
            });
        } finally {
            setIsSubmitting(false);
        }
    };

    if (!detalle) return null;

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h3>Editar Detalle para {detalle.cliente.nombre} {detalle.cliente.apellido}</h3>
                    <button
                        onClick={onClose}
                        className="close-button"
                        disabled={isSubmitting}
                    >
                        &times;
                    </button>
                </div>

                <form onSubmit={handleSubmit}>
                    <div className="customer-info">
                        <p><strong>Cliente:</strong> {detalle.cliente.nombre} {detalle.cliente.apellido}</p>
                        <p><strong>RUT:</strong> {detalle.cliente.rut}</p>
                        {detalle.tieneDescuentoCumpleanios && (
                            <p className="discount-badge birthday">Descuento por cumpleaños aplicado</p>
                        )}
                        {detalle.tieneDescuentoClienteFrecuente && (
                            <p className="discount-badge frequent">Cliente frecuente</p>
                        )}
                    </div>

                    <div className="form-row">
                        <div className="form-group">
                            <label>% Descuento Especial:</label>
                            <input
                                type="number"
                                name="porcentajeDescuentoEspecial"
                                value={formData.porcentajeDescuentoEspecial}
                                onChange={handleChange}
                                min="0"
                                max="100"
                                step="1"
                                disabled={isSubmitting}
                            />
                            {errors.porcentajeDescuentoEspecial && (
                                <span className="error">{errors.porcentajeDescuentoEspecial}</span>
                            )}
                        </div>

                        <div className="form-group">
                            <label>Descuento Extra ($):</label>
                            <input
                                type="number"
                                name="descuentoExtra"
                                value={formData.descuentoExtra}
                                onChange={handleChange}
                                min="0"
                                step="100"
                                disabled={isSubmitting}
                            />
                            {errors.descuentoExtra && (
                                <span className="error">{errors.descuentoExtra}</span>
                            )}
                        </div>
                    </div>

                    <div className="readonly-fields">
                        <div className="field-group">
                            <label>Tarifa Base:</label>
                            <span>${detalle.tarifa.toLocaleString()}</span>
                        </div>
                        <div className="field-group">
                            <label>Descuento Grupo ({detalle.porcentajeDescuentoGrupo}%):</label>
                            <span>${detalle.descuentoGrupo.toLocaleString()}</span>
                        </div>
                        <div className="field-group">
                            <label>Monto Final:</label>
                            <span>${detalle.montoFinal.toLocaleString()}</span>
                        </div>
                        <div className="field-group">
                            <label>IVA:</label>
                            <span>${detalle.montoIva.toLocaleString()}</span>
                        </div>
                        <div className="field-group">
                            <label>Total:</label>
                            <span>${detalle.montoTotal.toLocaleString()}</span>
                        </div>
                    </div>

                    {successMessage && <div className="success-message">{successMessage}</div>}
                    {errors.submit && <div className="error-message">{errors.submit}</div>}

                    <div className="modal-actions">
                        <button
                            type="button"
                            onClick={onClose}
                            className="cancel-button"
                            disabled={isSubmitting}
                        >
                            Cancelar
                        </button>
                        <button
                            type="submit"
                            disabled={isSubmitting}
                            className="save-button"
                        >
                            {isSubmitting ? 'Guardando...' : 'Guardar Cambios'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditDetalleModal;