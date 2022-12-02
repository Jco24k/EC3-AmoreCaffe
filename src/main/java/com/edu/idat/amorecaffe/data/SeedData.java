package com.edu.idat.amorecaffe.data;

import java.util.Arrays;
import java.util.List;

import com.edu.idat.amorecaffe.entity.CabeceraPedidoEntity;
import com.edu.idat.amorecaffe.entity.CargoEntity;
import com.edu.idat.amorecaffe.entity.CategoriaEntity;
import com.edu.idat.amorecaffe.entity.ClienteEntity;
import com.edu.idat.amorecaffe.entity.ComprobanteEntity;
import com.edu.idat.amorecaffe.entity.DetallePedidoEntity;
import com.edu.idat.amorecaffe.entity.DistritoEntity;
import com.edu.idat.amorecaffe.entity.EmpleadoEntity;
import com.edu.idat.amorecaffe.entity.ProductoEntity;

/**
 *
 * @author 51934
 */
public interface SeedData {

        public static List<CargoEntity> cargo = Arrays.asList(
                        new CargoEntity("admin"),
                        new CargoEntity("user"),
                        new CargoEntity("super-user"));

        public static List<CategoriaEntity> categoria = Arrays.asList(
                        new CategoriaEntity("postres"),
                        new CategoriaEntity("comidas"),
                        new CategoriaEntity("bebidas"));

        public static List<ClienteEntity> cliente = Arrays.asList(
                        new ClienteEntity("juan", "perez", "12345678", "987654321", "mz m1 ##"),
                        new ClienteEntity("antonella", "da silva", "98765432", "987654132", "mz m2 ##"),
                        new ClienteEntity("jazmin", "calderon", "96385274", "951623847", "mz m3 ##"));

        public static List<ComprobanteEntity> comprobante = Arrays.asList(
                        new ComprobanteEntity("BOLETA", "78945612322"),
                        new ComprobanteEntity("FACTURA", "96385274133"),
                        new ComprobanteEntity("BOLETA", "12345696344"));

        public static List<DetallePedidoEntity> detallePedido = Arrays.asList(
                        new DetallePedidoEntity(5),
                        new DetallePedidoEntity(3),
                        new DetallePedidoEntity(10));

        public static List<DistritoEntity> distrito = Arrays.asList(
                        new DistritoEntity("bellavista"),
                        new DistritoEntity("puente piedra"),
                        new DistritoEntity("san miguel"));

        public static List<ProductoEntity> producto = Arrays.asList(
                        new ProductoEntity("coca cola", 2.50, 20),
                        new ProductoEntity("inca kola", 2.50, 20),
                        new ProductoEntity("agua cielo", 1.00, 30));

        public static List<EmpleadoEntity> empleado = Arrays.asList(
                        new EmpleadoEntity("victor","sandoval", "96396323", "987365211", "mz m5 ##"),
                        new EmpleadoEntity("jorge","luna", "99988550", "906000111", "mz m6 ##"),
                        new EmpleadoEntity("ricardo","mendoza", "90022260", "900333222", "mz m7 ##"));

}
