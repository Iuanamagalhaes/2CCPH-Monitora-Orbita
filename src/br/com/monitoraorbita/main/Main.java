package br.com.monitoraorbita.main;

import br.com.monitoraorbita.model.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        // Cria três sensores de demonstração para o relatório inicial
        SensorTemperatura st = new SensorTemperatura("ST-000", "Demo Temperatura", 80.0);
        SensorPressao     sp = new SensorPressao    ("SP-000", "Demo Pressão",     70.0, 150.0);
        SensorRadiacao    sr = new SensorRadiacao   ("SR-000", "Demo Radiação",    1.5);

        st.ligar();
        sp.ligar();
        sr.ligar();

        // Agrupa os sensores em uma lista
        List<Sensor> sensores = new ArrayList<>();
        sensores.add(st);
        sensores.add(sp);
        sensores.add(sr);

        // Exibe o relatório de demonstração
        System.out.println("+______________________________________________________+");
        System.out.println("|           Relatorio de Sensores - Demo               |");
        for (Sensor s : sensores) {
            s.lerValor();
            System.out.printf("| %-14s | Alerta: %s%n",
                    s.getTipo(), s.estaEmAlerta() ? "SIM" : "OK");
        }
        System.out.println("+______________________________________________________+\n");

        // Inicia o menu
        Menu menu = new Menu();
        menu.exibirMenu();
    }
}