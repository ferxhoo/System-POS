import React from 'react'

export const NavBarComponents = () => {
  return (
    <div>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <a className="navbar-brand" href="/">Sistema POS</a>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNavDropdown">
                <ul className="navbar-nav">
                    <li className="nav-item active">
                        <a className="nav-link" href="/clientes">Clientes</a>
                    </li>
                    <li className="nav-item active">
                        <a className="nav-link" href="/proveedores">Proveedores</a>
                    </li>
                    <li className="nav-item active">
                        <a className="nav-link" href="/categorias">Categorias</a>
                    </li>
                    <li className="nav-item active">
                        <a className="nav-link" href="/unidades">Unidades</a>
                    </li>
                    <li className="nav-item active">
                        <a className="nav-link" href="/impuestos">Impuestos</a>
                    </li>
                    <li className="nav-item active">
                        <a className="nav-link" href="/insumos">Insumos</a>
                    </li>
                    <li className="nav-item active">
                        <a className="nav-link" href="/inventario">Inventario</a>
                    </li>
                    <li className="nav-item active">
                        <a className="nav-link" href="/productos">Productos</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
  )
}
