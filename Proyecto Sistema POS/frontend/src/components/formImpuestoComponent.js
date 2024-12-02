import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ImpuestoService from '../services/ImpuestoService';

const FormImpuestoComponent = () => {
    const [impuesto, setImpuesto] = useState({
        nombreImpuesto: '',
        concepto: '',
        tarifa: '',
    });

    const [conceptos, setConceptos] = useState([]);
    const navigate = useNavigate();
    const { id } = useParams();

    const tiposImpuesto = ['IVA', 'INC', 'ICUI', 'IBUA', 'ICL'];

    useEffect(() => {
        if (id) {
            ImpuestoService.getImpuestoById(id)
                .then(response => {
                    const data = response.data;
                    const tarifaEntero = (parseFloat(data.tarifa) * 100).toFixed(data.tarifa % 1 === 0 ? 0 : 2);
                    setImpuesto({
                        ...data,
                        tarifa: tarifaEntero,
                    });
                    updateConceptos(data.nombreImpuesto);
                })
                .catch(error => console.error('Error al cargar el impuesto:', error));
        }
    }, [id]);

    const updateConceptos = (nombreImpuesto) => {
        let options = [];
        switch (nombreImpuesto) {
            case 'IVA':
                options = [
                    { label: 'Excento 0%', value: '0' },
                    { label: 'Tarifa 5%', value: '5' },
                    { label: 'Tarifa 16%', value: '16' },
                    { label: 'Tarifa 19%', value: '19' },
                ];
                break;
            case 'INC':
                options = [
                    { label: 'Tarifa 2%', value: '2' },
                    { label: 'Tarifa 4%', value: '4' },
                    { label: 'Tarifa 8%', value: '8' },
                    { label: 'Tarifa 16%', value: '16' },
                ];
                break;
            case 'ICUI':
                options = [
                    { label: 'Tarifa 10%', value: '10' },
                    { label: 'Tarifa 15%', value: '15' },
                ];
                break;
            default:
                options = []; // IBUA y ICL tendrán inputs libres
        }
        setConceptos(options);
    };

    const handleNombreImpuestoChange = (e) => {
        const value = e.target.value;
        setImpuesto({ nombreImpuesto: value, concepto: '', tarifa: '' });
        updateConceptos(value);
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setImpuesto({ ...impuesto, [name]: value });
    };

    const handleConceptoChange = (e) => {
        const selectedOption = conceptos.find(option => option.label === e.target.value);
        setImpuesto({ ...impuesto, concepto: e.target.value, tarifa: selectedOption ? selectedOption.value : '' });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const dataToSend = {
            ...impuesto,
            tarifa: (parseFloat(impuesto.tarifa) / 100).toFixed(2), // Convertir tarifa a decimal
        };
        if (id) {
            ImpuestoService.editImpuesto(id, dataToSend)
                .then(() => navigate('/impuestos'))
                .catch(error => console.error('Error al actualizar el impuesto:', error));
        } else {
            ImpuestoService.saveImpuesto(dataToSend)
                .then(() => navigate('/impuestos'))
                .catch(error => console.error('Error al registrar el impuesto:', error));
        }
    };

    const title = id ? 'Editar Impuesto' : 'Registrar Impuesto';

    return (
        <div className='mt-5 mb-4'>
            <div className='container'>
                <div className='row'>
                    <div className='card col-md-6 offset-md-3'>
                        <br />
                        <h2 className='text-center'>{title}</h2>
                        <div className='card-body'>
                            <form onSubmit={handleSubmit}>
                                {renderSelectField(
                                    'Nombre del Impuesto',
                                    'nombreImpuesto',
                                    impuesto.nombreImpuesto,
                                    tiposImpuesto,
                                    handleNombreImpuestoChange
                                )}
                                {conceptos.length > 0
                                    ? renderSelectField(
                                        'Concepto',
                                        'concepto',
                                        impuesto.concepto,
                                        conceptos.map(c => c.label),
                                        handleConceptoChange
                                    )
                                    : renderInputField(
                                        'Concepto',
                                        'concepto',
                                        impuesto.concepto,
                                        handleInputChange,
                                        { type: 'text' }
                                    )}
                                {renderInputField(
                                    'Tarifa (%)',
                                    'tarifa',
                                    impuesto.tarifa,
                                    handleInputChange,
                                    {
                                        type: conceptos.length > 0 ? 'text' : 'number',
                                        readOnly: conceptos.length > 0,
                                        min: 0,
                                        max: 100,
                                        step: 0.01,
                                    }
                                )}
                                <div className='text-right'>
                                    <button type='submit' className='btn btn-success' style={{ marginRight: '20px' }}>
                                        Guardar
                                    </button>
                                    <button
                                        type='button'
                                        className='btn btn-danger'
                                        onClick={() => navigate('/impuestos')}
                                    >
                                        Cancelar
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

const renderInputField = (label, name, value, onChange, options = {}) => (
    <div className='form-group mb-3'>
        <label htmlFor={name}>{label}</label>
        <input
            {...options}
            className='form-control'
            id={name}
            name={name}
            placeholder={label}
            value={value}
            onChange={onChange}
        />
    </div>
);

const renderSelectField = (label, name, value, options, onChange) => (
    <div className='form-group mb-3'>
        <label htmlFor={name}>{label}</label>
        <select
            className='form-control'
            id={name}
            name={name}
            value={value}
            onChange={onChange}
        >
            <option value="">Seleccione {label}</option>
            {options.map(option => (
                <option key={option} value={option}>
                    {option}
                </option>
            ))}
        </select>
    </div>
);

export default FormImpuestoComponent;
