import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import clienteServiceInstance from '../services/ClienteService';
import axios from 'axios';

const FormClienteComponent = () => {
    const [cliente, setCliente] = useState({
        tipoDocumento: '',
        numeroDocumento: '',
        nombreCompleto: '',
        primerApellido: '',
        segundoApellido: '',
        email: '',
        telefono: '',
        direccion: '',
        pais: '',
        departamento: '',
        ciudad: '',
    });

    const [paises, setPaises] = useState([]);
    const [departamentos, setDepartamentos] = useState([]);
    const [ciudades, setCiudades] = useState([]);
    const [tiposDocumento] = useState(['DNI', 'Pasaporte', 'Cédula']); 

    const navigate = useNavigate();
    const { id } = useParams(); 

    useEffect(() => {
        axios.get('https://restcountries.com/v3.1/all')
            .then(response => {
                const paisesNombres = response.data.map(pais => pais.name.common);
                setPaises(paisesNombres.sort());
            })
            .catch(error => console.error('Error al cargar países:', error));
    }, []);

    useEffect(() => {
        if (id) {
            clienteServiceInstance.getClienteById(id)
                .then(response => {
                    const clienteData = response.data;
                    setCliente(clienteData);

                    if (clienteData.pais) {
                        const departamentos = getMockDepartamentos(clienteData.pais);
                        setDepartamentos(departamentos);

                        if (clienteData.departamento) {
                            const ciudades = getMockCiudades(clienteData.departamento);
                            setCiudades(ciudades);
                        }
                    }
                })
                .catch(error => console.error('Error al cargar cliente:', error));
        }
    }, [id]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setCliente({ ...cliente, [name]: value });

        if (name === 'pais') {
            const departamentos = getMockDepartamentos(value);
            setDepartamentos(departamentos);
            setCiudades([]);
        } else if (name === 'departamento') {
            const ciudades = getMockCiudades(value);
            setCiudades(ciudades);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (id) {
            clienteServiceInstance.editCliente(id, cliente)
                .then(() => navigate('/clientes'))
                .catch(error => console.error('Error al editar cliente:', error));
        } else {
            clienteServiceInstance.saveCliente(cliente)
                .then(() => navigate('/clientes'))
                .catch(error => console.error('Error al registrar cliente:', error));
        }
    };

    const title = id ? 'Editar Cliente' : 'Registrar Cliente';

    return (
        <div className='mt-5 mb-4'>
            <div className='container'>
            <div className='row'>
                <div className='card col-md-6 offset-md-3'>
                    <br/>
                    <h2 className='text-center'>{title}</h2>
                    <div className='card-body'>
                        <form onSubmit={handleSubmit}>
                            {renderSelectField('Tipo de Documento', 'tipoDocumento', cliente.tipoDocumento, tiposDocumento, handleInputChange)}
                            {renderInputField('Número de Documento', 'numeroDocumento', cliente.numeroDocumento, handleInputChange)}
                            {renderInputField('Nombre Completo', 'nombreCompleto', cliente.nombreCompleto, handleInputChange)}
                            {renderInputField('Primer Apellido', 'primerApellido', cliente.primerApellido, handleInputChange)}
                            {renderInputField('Segundo Apellido', 'segundoApellido', cliente.segundoApellido, handleInputChange)}
                            {renderInputField('Email', 'email', cliente.email, handleInputChange, 'email')}
                            {renderInputField('Teléfono', 'telefono', cliente.telefono, handleInputChange)}
                            {renderInputField('Dirección', 'direccion', cliente.direccion, handleInputChange)}
                            {renderSelectField('País', 'pais', cliente.pais, paises, handleInputChange)}
                            {renderSelectField('Departamento', 'departamento', cliente.departamento, departamentos, handleInputChange)}
                            {renderSelectField('Ciudad', 'ciudad', cliente.ciudad, ciudades, handleInputChange)}
                            <div className='text-right'>
                                <button type='submit' className='btn btn-success' style={{ marginRight: '20px' }}>Guardar</button>
                                <button type='button' className='btn btn-danger' onClick={() => navigate('/clientes')}>
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

const renderInputField = (label, name, value, onChange, type = 'text') => (
    <div className='form-group mb-3'>
        <label htmlFor={name}>{label}</label>
        <input
            type={type}
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

const getMockDepartamentos = (pais) => {
    if (pais === 'Colombia') return ['Antioquia', 'Cundinamarca', 'Valle del Cauca', 'Cesar', 'Meta'];
    if (pais === 'México') return ['Jalisco', 'Nuevo León', 'Ciudad de México'];
    return [];
};

const getMockCiudades = (departamento) => {
    if (departamento === 'Antioquia') return ['Medellín', 'Bello', 'Itagüí'];
    if (departamento === 'Cundinamarca') return ['Bogotá', 'Soacha', 'Chía'];
    if (departamento === 'Cesar') return ['La Paz', 'San Diego', 'Valledupar'];
    return [];
};

export default FormClienteComponent;
