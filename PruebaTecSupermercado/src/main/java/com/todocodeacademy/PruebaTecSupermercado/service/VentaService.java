package com.todocodeacademy.PruebaTecSupermercado.service;

import com.todocodeacademy.PruebaTecSupermercado.dto.DetalleVentaDTO;
import com.todocodeacademy.PruebaTecSupermercado.dto.VentaDTO;
import com.todocodeacademy.PruebaTecSupermercado.exception.NotFoundException;
import com.todocodeacademy.PruebaTecSupermercado.mapper.Mapper;
import com.todocodeacademy.PruebaTecSupermercado.model.DetalleVenta;
import com.todocodeacademy.PruebaTecSupermercado.model.Producto;
import com.todocodeacademy.PruebaTecSupermercado.model.Sucursal;
import com.todocodeacademy.PruebaTecSupermercado.model.Venta;
import com.todocodeacademy.PruebaTecSupermercado.repository.ProductoRepository;
import com.todocodeacademy.PruebaTecSupermercado.repository.SucursalRepository;
import com.todocodeacademy.PruebaTecSupermercado.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService implements IVentaService {

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private SucursalRepository sucursalRepo;

    @Override
    public List<VentaDTO> traerVentas() {
        return ventaRepo.findAll()
                .stream()
                .map(Mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VentaDTO crearVenta(VentaDTO ventaDTO) {
        if (ventaDTO == null) throw new RuntimeException("VentaDTO es null");
        if (ventaDTO.getIdSucursal() == null) throw new RuntimeException("Debe indicar la sucursal");
        if (ventaDTO.getDetalle() == null || ventaDTO.getDetalle().isEmpty())
            throw new RuntimeException("Debe incluir al menos un producto");

        Sucursal suc = sucursalRepo.findById(ventaDTO.getIdSucursal()).orElse(null);
        if (suc == null) {
            throw new NotFoundException("Sucursal no encontrada.");
        }

        Venta vent = new Venta();
        vent.setFecha(ventaDTO.getFecha());
        vent.setEstado(ventaDTO.getEstado());
        vent.setSucursal(suc);
        vent.setTotal(ventaDTO.getTotal());

        List<DetalleVenta> detalles = new ArrayList<>();
        Double totalCalculado = 0.0;

        for (DetalleVentaDTO detDTO : ventaDTO.getDetalle()) {
            Producto p = productoRepo.findByNombre(detDTO.getNombreProd()).orElse(null);

            if (p == null)
                throw new RuntimeException("Producto no encontrado: " + detDTO.getNombreProd());

            DetalleVenta detalleVenta = new DetalleVenta();
            detalleVenta.setProducto(p);
            detalleVenta.setPrecio(detDTO.getPrecioUnitario());
            detalleVenta.setCantProd(detDTO.getCantProd());
            detalleVenta.setVenta(vent);

            detalles.add(detalleVenta);

            totalCalculado = totalCalculado + (detDTO.getPrecioUnitario() * detDTO.getCantProd());
        }

        vent.setDetalle(detalles);
        return Mapper.toDTO(ventaRepo.save(vent));
    }

    @Override
    public VentaDTO actualizarVenta(Long id, VentaDTO ventaDTO) {
        Venta v = ventaRepo.findById(id).orElse(null);
        if (v == null) throw new NotFoundException("Venta no encontrada");

        if (ventaDTO.getFecha() != null) v.setFecha(ventaDTO.getFecha());
        if (ventaDTO.getEstado() != null) v.setEstado(ventaDTO.getEstado());
        if (ventaDTO.getTotal() != null) v.setTotal(ventaDTO.getTotal());
        if (ventaDTO.getIdSucursal() != null) {
            Sucursal suc = sucursalRepo.findById(ventaDTO.getIdSucursal()).orElse(null);
            if (suc == null) throw new NotFoundException("Sucursal no encontrada.");
            v.setSucursal(suc);
        }

        ventaRepo.save(v);
        return Mapper.toDTO(ventaRepo.save(v));
    }

    @Override
    public void eliminarVenta(Long id) {
        Venta v = ventaRepo.findById(id).orElse(null);
        if (v == null) throw new NotFoundException("Venta no encontrada");
        ventaRepo.deleteById(id);
    }
}
