package com.example.farmacia;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainBoundary extends Application implements CommandExecution {

    private MedicamentoBoundary medicamentoBoundary = new MedicamentoBoundary();
    private InformacoesBoundary informacoesBoundary = new InformacoesBoundary();

    private BorderPane paneMain = new BorderPane();

    public MainBoundary() {
        medicamentoBoundary.addExecution(this);
        informacoesBoundary.addExecution(this);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Scene scn = new Scene(paneMain, 1024, 768);

        MenuBar menuMain = new MenuBar();

        Menu menuArquivo = new Menu("Arquivos");
        Menu menuCadastro = new Menu("Cadastro");
        Menu menuAjuda = new Menu("Ajuda");

        MenuItem itemSair = new MenuItem("Sair");
        MenuItem itemMedicamento = new MenuItem("Medicamento");
        MenuItem itemInformacoes = new MenuItem("Informações");

        itemSair.setOnAction((e) -> {
            execute("SAIR");
        });

        itemMedicamento.setOnAction((e) -> {
            execute("BOUNDARY-MEDICAMENTO");
        });
        itemInformacoes.setOnAction((e) -> {
            execute("BOUNDARY-INFORMACOES");
        });
        menuArquivo.getItems().addAll(itemSair);
        menuCadastro.getItems().addAll(itemMedicamento);
        menuAjuda.getItems().addAll(itemInformacoes);

        menuMain.getMenus().addAll(menuArquivo, menuCadastro, menuAjuda);

        paneMain.setTop(menuMain);

        stage.setScene(scn);
        stage.setTitle("Gestão de Farmacia");
        stage.show();


    }

    @Override
    public void execute(String command) {
        if("BOUNDARY-MEDICAMENTO".equals(command)) {
            paneMain.setCenter(medicamentoBoundary.render());
        } else if("BOUNDARY-INFORMACOES".equals(command)) {
            paneMain.setCenter(informacoesBoundary.render());
        } else if("SAIR".equals(command)) {
            Platform.exit();
            System.exit(0);
        }

    }

    public static void main(String[] args) {
        Application.launch(MainBoundary.class, args);
    }
}
