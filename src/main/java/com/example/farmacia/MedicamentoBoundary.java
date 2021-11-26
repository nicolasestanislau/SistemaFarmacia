package com.example.farmacia;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MedicamentoBoundary extends CommandProducer implements  StrategyBoundary {

    private TextField txtId = new TextField();
    private TextField txtNome = new TextField();
    private TextField txtPreco = new TextField();
    private DatePicker dtVencimento = new DatePicker();

    private Button btnNovoMedicamento = new Button("Novo Medicamento");
    private Button btnSalvar = new Button("Salvar");
    private Button btnPesquisar = new Button("Pesquisar");

    private MedicamentoControl control = new MedicamentoControl(); //Composição

    private TableView<Medicamento> table = new TableView<>();

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private void criarTabela() {
        TableColumn<Medicamento, Long> col1 = new TableColumn<>("Id");
        col1.setCellValueFactory( new PropertyValueFactory<>("id"));

        TableColumn<Medicamento, String> col2 = new TableColumn<>("Nome");
        col2.setCellValueFactory( new PropertyValueFactory<>("nome"));

        TableColumn<Medicamento, Double> col3 = new TableColumn<>("Preço");
        col3.setCellValueFactory( new PropertyValueFactory<>("preco"));

        TableColumn<Medicamento, String> col4 = new TableColumn<>("Vencimento");
        col4.setCellValueFactory( (medicamentoProp) -> {
            LocalDate n = medicamentoProp.getValue().getVencimento();
            String strData = n.format(this.dtf);
            return new ReadOnlyStringWrapper(strData);
        } );

        TableColumn<Medicamento, String> col5 = new TableColumn<>("Açoes");
        col5.setCellFactory( (tbCol) ->
                new TableCell<Medicamento, String>(){
                    final Button btn = new Button("Remover");

                    public void updateItem(String item, boolean empty) {
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction( (e) -> {
                                Medicamento m = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Você confirma a remoção do Medicamento Id " +
                                        m.getId(), ButtonType.OK, ButtonType.CANCEL);
                                Optional<ButtonType> clicado = alert.showAndWait();
                                if (clicado.isPresent() && clicado.get().equals(ButtonType.OK)) {
                                    control.remover(m.getId());
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                }
        );
        table.getColumns().clear();

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
        Bindings.bindBidirectional( txtPreco.textProperty(), control.preco, new NumberStringConverter());
        Bindings.bindBidirectional( dtVencimento.valueProperty(), control.vencimento);

        panCampos.add(new Label("Id"), 0, 0);
        panCampos.add(txtId, 1, 0);

        panCampos.add(btnNovoMedicamento, 2, 0);

        panCampos.add(new Label("Nome"), 0, 1);
        panCampos.add(txtNome, 1, 1);

        panCampos.add(new Label("Preço"), 0, 2);
        panCampos.add(txtPreco, 1, 2);

        panCampos.add(new Label("Vencimento"), 0, 3);
        panCampos.add(dtVencimento, 1, 3);

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

        btnNovoMedicamento.setOnAction( e -> {
            control.novoMedicamento();
        });
        panelPrincipal.setTop(panCampos);
        panelPrincipal.setCenter(table);
        this.criarTabela();
        return (panelPrincipal);
    }
}
