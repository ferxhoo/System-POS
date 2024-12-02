
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { NavBarComponents } from './components/NavBarComponents';

import ListClientesComponents from './components/ListClientesComponents';
import FormClienteComponent from './components/formClienteComponent';

import ListProveedoresComponents from './components/ListProveedoresComponents';
import FormProveedorComponent from './components/formProveedorComponent';

import ListInsumosComponents from './components/ListInsumosComponents';
import FormInsumoComponent from './components/formInsumoComponent';

import ListCategoriasComponents from './components/ListCategoriasComponents';
import FormCategoriaComponent from './components/formCategoriaComponent';

import ListUnidadesComponents from './components/ListUnidadesComponents';
import FormUnidadComponent from './components/formUnidadComponent';

import ListImpuestosComponents from './components/ListImpuestosComponents';
import FormImpuestoComponent from './components/formImpuestoComponent';

import ListInventarioComponent from './components/ListInventarioComponent'; 

import ListProductosComponents from './components/ListProductosComponents';
import FormProductoComponent from './components/formProductoComponent';

function App() {
  return (
    <div>
      <BrowserRouter>
        <div className='mb-7'>
          <NavBarComponents />
        </div>
        <div className='mt-10'>
          <Routes>
            <Route exact path='/' element={<ListClientesComponents />} />
            <Route exact path='/clientes' element={<ListClientesComponents />} />
            <Route exact path='/cliente/registro' element={<FormClienteComponent />} />
            <Route exact path='/cliente/editar/:id' element={<FormClienteComponent />} />

            <Route exact path='/proveedores' element={<ListProveedoresComponents />} />
            <Route exact path='/proveedor/registro' element={<FormProveedorComponent />} />
            <Route exact path='/proveedor/editar/:id' element={<FormProveedorComponent />} />

            <Route exact path='/insumos' element={<ListInsumosComponents />} />
            <Route exact path='/insumo/registro' element={<FormInsumoComponent />} />
            <Route exact path='/insumo/editar/:id' element={<FormInsumoComponent />} />

            <Route exact path='/impuestos' element={<ListImpuestosComponents />} />
            <Route exact path='/impuesto/registro' element={<FormImpuestoComponent />} />
            <Route exact path='/impuesto/editar/:id' element={<FormImpuestoComponent />} />

            <Route exact path='/categorias' element={<ListCategoriasComponents />} />
            <Route exact path='/categoria/registro' element={<FormCategoriaComponent />} />
            <Route exact path='/categoria/editar/:id' element={<FormCategoriaComponent />} />

            <Route exact path='/unidades' element={<ListUnidadesComponents />} />
            <Route exact path='/unidad/registro' element={<FormUnidadComponent />} />
            <Route exact path='/unidad/editar/:id' element={<FormUnidadComponent />} />

            <Route exact path='/inventario' element={<ListInventarioComponent />} />

            <Route exact path='/productos' element={<ListProductosComponents />} />
            <Route exact path='/producto/registro' element={<FormProductoComponent />} />
            <Route exact path='/producto/editar/:id' element={<FormProductoComponent />} />

          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
