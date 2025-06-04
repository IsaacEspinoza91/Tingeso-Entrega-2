const HolidayConfig = ({ onBack }) => {
    return (
        <div className="config-container">
            <button className="back-button" onClick={onBack}>
                ←
            </button>
            <h2>Definición Días Feriados y especiales</h2>
            {/* Aquí irá el contenido específico de esta configuración */}
            <div className="config-content">
                Contenido de configuración de días feriados...
            </div>
        </div>
    );
};

export default HolidayConfig;