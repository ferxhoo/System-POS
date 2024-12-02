import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import proveedorServiceInstance from '../services/ProveedorService';
import axios from 'axios';

const FormProveedorComponent = () => {
    const [proveedor, setProveedor] = useState({
        tipoProveedor: '',
        tipoDocumento: '',
        numeroDocumento: '',
        nombreProveedor: '',
        direccion: '',
        pais: '',
        departamento: '',
        ciudad: '',
        telefono: '',
        email: '',
    });

    const [paises, setPaises] = useState([]);
    const [departamentos, setDepartamentos] = useState([]);
    const [ciudades, setCiudades] = useState([]);
    const [tiposDocumento, setTiposDocumento] = useState([]);
    const [tiposProveedor] = useState(['Natural', 'Jurídico']); // Tipos de proveedor.

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
            proveedorServiceInstance.getProveedorById(id)
                .then(response => {
                    const proveedorData = response.data;
                    setProveedor(proveedorData);

                    if (proveedorData.pais) {
                        const departamentos = getMockDepartamentos(proveedorData.pais);
                        setDepartamentos(departamentos);

                        if (proveedorData.departamento) {
                            const ciudades = getMockCiudades(proveedorData.departamento);
                            setCiudades(ciudades);
                        }
                    }

                    // Ajusta los tipos de documento según el tipo de proveedor cargado.
                    updateTiposDocumento(proveedorData.tipoProveedor);
                })
                .catch(error => console.error('Error al cargar proveedor:', error));
        }
    }, [id]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setProveedor({ ...proveedor, [name]: value });

        if (name === 'pais') {
            const departamentos = getMockDepartamentos(value);
            setDepartamentos(departamentos);
            setCiudades([]);
        } else if (name === 'departamento') {
            const ciudades = getMockCiudades(value);
            setCiudades(ciudades);
        } else if (name === 'tipoProveedor') {
            updateTiposDocumento(value); // Cambiar tipos de documento según tipo de proveedor.
            setProveedor({ ...proveedor, tipoProveedor: value, tipoDocumento: '' }); // Reiniciar tipo de documento.
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (id) {
            proveedorServiceInstance.editProveedor(id, proveedor)
                .then(() => navigate('/proveedores'))
                .catch(error => console.error('Error al editar proveedor:', error));
        } else {
            proveedorServiceInstance.saveProveedor(proveedor)
                .then(() => navigate('/proveedores'))
                .catch(error => console.error('Error al registrar proveedor:', error));
        }
    };

    const updateTiposDocumento = (tipoProveedor) => {
        if (tipoProveedor === 'Natural') {
            setTiposDocumento(['Cédula', 'DNI', 'Pasaporte', 'Cédula Extranjera']);
        } else if (tipoProveedor === 'Jurídico') {
            setTiposDocumento(['NIT', 'RUT', 'Otro']);
        } else {
            setTiposDocumento([]);
        }
    };

    const title = id ? 'Editar Proveedor' : 'Registrar Proveedor';

    return (
        <div className='mt-5 mb-4'>
            <div className='container'>
                <div className='row'>
                    <div className='card col-md-6 offset-md-3'>
                        <br />
                        <h2 className='text-center'>{title}</h2>
                        <div className='card-body'>
                            <form onSubmit={handleSubmit}>
                                {renderSelectField('Tipo de Proveedor', 'tipoProveedor', proveedor.tipoProveedor, tiposProveedor, handleInputChange)}
                                {renderSelectField('Tipo de Documento', 'tipoDocumento', proveedor.tipoDocumento, tiposDocumento, handleInputChange)}
                                {renderInputField('Número de Documento', 'numeroDocumento', proveedor.numeroDocumento, handleInputChange)}
                                {renderInputField('Nombre del Proveedor', 'nombreProveedor', proveedor.nombreProveedor, handleInputChange)}
                                {renderInputField('Dirección', 'direccion', proveedor.direccion, handleInputChange)}
                                {renderSelectField('País', 'pais', proveedor.pais, paises, handleInputChange)}
                                {renderSelectField('Departamento', 'departamento', proveedor.departamento, departamentos, handleInputChange)}
                                {renderSelectField('Ciudad', 'ciudad', proveedor.ciudad, ciudades, handleInputChange)}
                                {renderInputField('Teléfono', 'telefono', proveedor.telefono, handleInputChange)}
                                {renderInputField('Email', 'email', proveedor.email, handleInputChange, 'email')}
                                <div className='text-right'>
                                    <button type='submit' className='btn btn-success' style={{ marginRight: '20px' }}>Guardar</button>
                                    <button type='button' className='btn btn-danger' onClick={() => navigate('/proveedores')}>
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

export default FormProveedorComponent;
