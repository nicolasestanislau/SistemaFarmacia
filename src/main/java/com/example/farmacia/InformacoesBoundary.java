package com.example.farmacia;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class InformacoesBoundary extends CommandProducer implements StrategyBoundary {
    @Override
    public Pane render() {

        VBox pane = new VBox();
        Button btnMedicamentos = new Button("Cadastro Medicamentos");
        Button btnFuncionarios = new Button("Cadastro Funcionarios");
        pane.getChildren().addAll(
                new Label("Nicolas Estanislau"),
                new Label("Sistema de Farmacia"),
                new Label("Professor Antonio Rodrigues Carvalho Neto"),
                btnMedicamentos,
                btnFuncionarios
        );
        btnMedicamentos.setOnAction((e) -> {
            executeCommand("BOUNDARY-MEDICAMENTO");
        });

        btnFuncionarios.setOnAction((e) -> {
            executeCommand("BOUNDARY-FUNCIONARIO");
        });

        return pane;
    }
}
