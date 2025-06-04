const GroupDiscounts = ({ onBack }) => {
    return (
        <div className="config-container">
            <button className="back-button" onClick={onBack}>
                ←
            </button>
            <h2>Configuración Descuentos de grupo</h2>
            <div className="config-content">
                Contenido de configuración de descuentos de grupo...
            </div>
        </div>
    );
};

export default GroupDiscounts;