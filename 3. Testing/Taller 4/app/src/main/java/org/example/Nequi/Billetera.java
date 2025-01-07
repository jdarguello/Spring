package org.example.Nequi;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Billetera {
    private Long billeteraId;
    private Cliente cliente;
    private Long numeroCelular;
    private List<Bolsillo> bolsillos;

    public Billetera (Cliente cliente, Long numeroCelular) throws Exception {
        this.bolsillos = new ArrayList<>();
        this.bolsillos.add(new Bolsillo());

        //Evaluar que la cantidad de digitos sean 10
        if (numeroCelular > 999999999L && numeroCelular <= 9999999999L ) {
            this.numeroCelular = numeroCelular;
        } else {
            throw new Exception("500: el número de celular tiene que tener 10 digitos");
        }
        
    }

    public void transaccion (Double monto, Long bolsilloId) throws Exception {
        //1. Encontrar el bolsillo a consignar o retirar
        Bolsillo bolsillo = getBolsillo(bolsilloId);

        //2. Enviar o retirar monto al bolsillo
        if (monto > 0) {
            //Consignación
            bolsillo.consignacion(monto);
        } else {
            //Retiro

            bolsillo.retirar(monto);
        }
    }

    public void crearBolsillo(Bolsillo bolsillo) {
        //Lógica para crear un bolsillo. Añada el bolsillo a la lista
    }

    public Double getSaldoTotal() {
        Double total = 0.0;
        for (Bolsillo bolsillo : bolsillos) {
            total += bolsillo.getSaldo();
        }
        return total;
    }

    private Bolsillo getBolsillo (Long bolsilloId) throws Exception {
        for (Bolsillo bolsillo : bolsillos) {
            if (bolsillo.getBolsilloId() == bolsilloId) {
                return bolsillo;
            }
        }
        throw new Exception("500: no se reconoce el bolsillo con bolsilloId = " + bolsilloId);
    }
}
