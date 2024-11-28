
import './App.css';
import ListClientesComponents from './components/ListClientesComponents';
import { NavBarComponents } from './components/NavBarComponents';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import FormClienteComponent from './components/formClienteComponent';

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
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
