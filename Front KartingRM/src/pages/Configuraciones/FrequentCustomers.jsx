const FrequentCustomers = ({ onBack }) => {
    return (
        <div className="config-container">
            <button className="back-button" onClick={onBack}>
                ←
            </button>
            <h2>Configuración de descuentos de clientes frecuentes</h2>
            <div className="config-content">
                Contenido de configuración de clientes frecuentes...
            </div>
        </div>
    );
};

export default FrequentCustomers;