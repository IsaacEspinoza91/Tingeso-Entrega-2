import React, { useState } from 'react';
import { getComprobanteById, createComprobante } from '../../services/comprobanteService';
import ComprobanteDetails from './ComprobanteDetails';
import PDFDownloadButton from './ComprobantePDF/PDFDownloadButton';
import CreateComprobanteModal from './CreateComprobanteModal';
import './ComprobanteSearch.css';

const ComprobanteSearch = () => {
    const [searchValue, setSearchValue] = useState('');
    const [searchType, setSearchType] = useState('id'); // 'id' o 'reserva'
    const [isSearching, setIsSearching] = useState(false);
    const [error, setError] = useState('');
    const [comprobante, setComprobante] = useState(null);
    const [showCreateModal, setShowCreateModal] = useState(false);

    const handleSearch = async (e) => {
        e.preventDefault();

        if (!searchValue.trim()) {
            setError(`Por favor ingrese un ID de ${searchType === 'id' ? 'comprobante' : 'reserva'}`);
            return;
        }

        setIsSearching(true);
        setError('');
        setComprobante(null);

        try {
            // Por ahora solo implementamos búsqueda por ID de comprobante
            if (searchType === 'id') {
                const result = await getComprobanteById(searchValue);
                setComprobante(result);
            } else {
                // Aquí iría la lógica para búsqueda por ID de reserva
                // Por ahora mostramos un mensaje
                setError('Búsqueda por reserva no implementada aún');
            }
        } catch (err) {
            setError('No se encontró un comprobante con ese ID');
            console.error('Error al buscar comprobante:', err);
        } finally {
            setIsSearching(false);
        }
    };

    const handleReset = () => {
        setSearchValue('');
        setError('');
        setComprobante(null);
    };

    const handleComprobanteCreated = (newComprobante) => {
        setComprobante(newComprobante);
        setShowCreateModal(false);
        setSearchValue(newComprobante.id.toString());
    };

    return (
        <div className="comprobante-search-container">
            <div className="search-header">
                <h3>Comprobantes</h3>
                <div className="header-actions">
                    <button
                        onClick={() => setShowCreateModal(true)}
                        className="create-button"
                    >
                        Crear Nuevo Comprobante
                    </button>
                </div>
            </div>

            <div className="search-section">
                <h4>Buscar Comprobante</h4>
                <form onSubmit={handleSearch}>
                    <div className="search-options">
                        <label>
                            <input
                                type="radio"
                                value="id"
                                checked={searchType === 'id'}
                                onChange={() => setSearchType('id')}
                            />
                            Por ID de Comprobante
                        </label>
                        <label>
                            <input
                                type="radio"
                                value="reserva"
                                checked={searchType === 'reserva'}
                                onChange={() => setSearchType('reserva')}
                            />
                            Por ID de Reserva
                        </label>
                    </div>
                    <div className="search-controls">
                        <div className="search-input">
                            <input
                                type="number"
                                value={searchValue}
                                onChange={(e) => {
                                    const value = e.target.value;
                                    if (value === '' || /^[0-9]*$/.test(value)) {
                                        setSearchValue(value);
                                    }
                                }}
                                placeholder={`Ingrese ID de ${searchType === 'id' ? 'comprobante' : 'reserva'}`}
                                min="1"
                            />
                            <button
                                type="submit"
                                disabled={isSearching || !searchValue.trim()}
                                className="search-button"
                            >
                                {isSearching ? 'Buscando...' : 'Buscar'}
                            </button>
                            <button
                                type="button"
                                onClick={handleReset}
                                className="reset-button"
                            >
                                Limpiar
                            </button>
                        </div>
                    </div>
                </form>

                {error && <div className="error-message">{error}</div>}
            </div>

            {isSearching && <div className="loading">Buscando comprobante...</div>}

            {comprobante && (
                <div className="comprobante-result">
                    <div className="comprobante-header">
                        <h4>Comprobante #{comprobante.id}</h4>
                        <div>
                            <PDFDownloadButton comprobante={comprobante} />
                        </div>
                    </div>

                    <div className="comprobante-summary">
                        <div className="summary-item">
                            <span className="summary-label">Estado:</span>
                            <span className={`status-badge ${comprobante.pagado ? 'pagado' : 'pendiente'}`}>
                                {comprobante.pagado ? 'Pagado' : 'Pendiente'}
                            </span>
                        </div>
                        <div className="summary-item">
                            <span className="summary-label">Total:</span>
                            <span>${comprobante.total.toLocaleString()}</span>
                        </div>
                        <div className="summary-item">
                            <span className="summary-label">Reserva ID:</span>
                            <span>{comprobante.reserva.id}</span>
                        </div>
                        <div className="summary-item">
                            <span className="summary-label">Fecha:</span>
                            <span>{new Date(comprobante.reserva.fecha + "T00:00:00").toLocaleDateString()}</span>
                        </div>
                    </div>

                    <ComprobanteDetails comprobante={comprobante} />
                </div>
            )}

            {showCreateModal && (
                <CreateComprobanteModal
                    onClose={() => setShowCreateModal(false)}
                    onComprobanteCreated={handleComprobanteCreated}
                />
            )}
        </div>
    );
};

export default ComprobanteSearch;