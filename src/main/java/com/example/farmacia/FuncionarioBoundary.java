package com.example.farmacia;

import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;

import java.util.Optional;

public class FuncionarioBoundary extends CommandProducer implements StrategyBoundary {

    private TextField txtId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtCargo = new TextField();
    private TextField txtSalario = new TextField();

    private Button btnNovoFuncionario = new Button("Novo Funcionario");
    private Button btnSalvar = new Button("Salvar");
    private Button btnPesquisar = new Button("Pesquisar");

    private FuncionarioControl control = new FuncionarioControl();

    private TableView<Funcionario> table = new TableView<>();

    private void criarTabela() {
        TableColumn<Funcionario, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory( new PropertyValueFactory<>("id"));

        TableColumn<Funcionario, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<>("nome"));

        TableColumn<Funcionario, String> col3 = new TableColumn<>("Cargo");
        col3.setCellValueFactory( new PropertyValueFactory<>("cargo"));

        TableColumn<Funcionario, Double> col4 = new TableColumn<>("Salario");
        col4.setCellValueFactory( new PropertyValueFactory<>("salario"));

        TableColumn<Funcionario, String> col5 = new TableColumn<>("Açoes");
        col5.setCellValueFactory( new PropertyValueFactory<>("DUMMY"));
        col5.setCellFactory( (tbCol) ->
                new TableCell<Funcionario, String>(){
                    final Button btn = new Button("Remover");

                    public void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction( (e) -> {
                                Funcionario f = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Você confirma a remoção do Funcionario Id " +
                                        f.getId(), ButtonType.OK, ButtonType.CANCEL);
                                Optional<ButtonType> clicado = alert.showAndWait();
                                if (clicado.isPresent() && clicado.get().equals(ButtonType.OK)) {
                                    control.remover(f.getId());
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                }
        );

        table.getColumns().addAll(col1, col2, col3, col4, col5);

        table.setItems(control.getListaView());

        table
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {
                            if (novo != null) {
                                control.setEntity(novo);
                            }
                        }
                );
    }

    @Override
    public Pane render() {
        BorderPane panelPrincipal = new BorderPane();
        GridPane panCampos = new GridPane();
        txtId.setEditable(false);
        txtId.setDisable(true);
        Bindings.bindBidirectional( txtId.textProperty(), control.id, new NumberStringConverter());
        Bindings.bindBidirectional( txtNome.textProperty(), control.nome);
        Bindings.bindBidirectional( txtCargo.textProperty(), control.cargo);
        Bindings.bindBidirectional( txtSalario.textProperty(), control.salario, new NumberStringConverter());

        panCampos.add(new Label("Id"), 0, 0);
        panCampos.add(txtId, 1, 0);

        panCampos.add(btnNovoFuncionario, 2, 0);

        panCampos.add(new Label("Nome"), 0, 1);
        panCampos.add(txtNome, 1, 1);

        panCampos.add(new Label("Cargo"), 0, 2);
        panCampos.add(txtCargo, 1, 2);

        panCampos.add(new Label("Salario"), 0, 3);
        panCampos.add(txtSalario, 1, 3);

        panCampos.add(btnSalvar, 0, 4);
        panCampos.add(btnPesquisar, 1, 4);

        Button btnInformacao = new Button("Informações");
        panCampos.add(btnInformacao, 2, 5);

        btnInformacao.setOnAction((e) -> {
            executeCommand("BOUNDARY-INFORMACOES");
        });

        btnSalvar.setOnAction( e -> {
            control.salvar();
        });

        btnPesquisar.setOnAction( e -> {
            control.pesquisar();
        });

        btnNovoFuncionario.setOnAction( e -> {
            control.novoFuncionario();
        });
        panelPrincipal.setTop(panCampos);
        panelPrincipal.setCenter(table);
        this.criarTabela();
        return (panelPrincipal);
    }
}
