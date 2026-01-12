package com.todocodeacademy.PruebaTecSupermercado.service;

import com.todocodeacademy.PruebaTecSupermercado.dto.SucursalDTO;

import java.util.List;

public interface ISucursalService {
    List<SucursalDTO> traerSucursales();

    SucursalDTO crearSucursal(SucursalDTO sucursalDTO);

    SucursalDTO actualizarSucursal(Long id, SucursalDTO sucursalDto);

    void eliminarSucursal(Long id);
}
