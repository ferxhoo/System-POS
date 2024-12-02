import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import unidadServiceInstance from '../services/UnidadService';

const FormUnidadComponent = () => {
    const [unidad, setUnidad] = useState({
        nombreUnidad: '',
    });

    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            unidadServiceInstance.getUnidadById(id)
                .then(response => {
                    setUnidad(response.data);
                })
                .catch(error => console.error('Error al cargar la unidad:', error));
        }
    }, [id]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUnidad({ ...unidad, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (id) {
            unidadServiceInstance.editUnidad(id, unidad)
                .then(() => navigate('/unidades'))
                .catch(error => console.error('Error al actualizar la unidad:', error));
        } else {
            unidadServiceInstance.saveUnidad(unidad)
                .then(() => navigate('/unidades'))
                .catch(error => console.error('Error al registrar la unidad:', error));
        }
    };

    const title = id ? 'Editar Unidad' : 'Registrar Unidad';

    return (
        <div className='mt-5 mb-4'>
            <div className='container'>
                <div className='row'>
                    <div className='card col-md-6 offset-md-3'>
                        <br />
                        <h2 className='text-center'>{title}</h2>
                        <div className='card-body'>
                            <form onSubmit={handleSubmit}>
                                {renderInputField(
                                    'Nombre de Unidad',
                                    'nombreUnidad',
                                    unidad.nombreUnidad,
                                    handleInputChange
                                )}
                                <div className='text-right'>
                                    <button type='submit' className='btn btn-success' style={{ marginRight: '20px' }}>
                                        Guardar
                                    </button>
                                    <button
                                        type='button'
                                        className='btn btn-danger'
                                        onClick={() => navigate('/unidades')}
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

const renderInputField = (label, name, value, onChange) => (
    <div className='form-group mb-3'>
        <label htmlFor={name}>{label}</label>
        <input
            type='text'
            className='form-control'
            id={name}
            name={name}
            placeholder={label}
            value={value}
            onChange={onChange}
        />
    </div>
);

export default FormUnidadComponent;
