package com.example.farmacia;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class FuncionarioControl {

    LongProperty id = new SimpleLongProperty(0);
    StringProperty nome = new SimpleStringProperty("");
    StringProperty cargo = new SimpleStringProperty("");
    DoubleProperty salario = new SimpleDoubleProperty(0);

    private ObservableList<Funcionario> listaView = FXCollections.observableArrayList();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAOImpl();

    public Funcionario getEntity() {
        Funcionario f = new Funcionario();
        f.setId(id.get());
        f.setNome(nome.get());
        f.setCargo(cargo.get());
        f.setSalario(salario.get());
        return f;
    }

    public void setEntity(Funcionario f) {
        id.set(f.getId());
        nome.set(f.getNome());
        cargo.set(f.getCargo());
        salario.set(f.getSalario());
    }

    public void salvar() {
        Funcionario f = getEntity();
        if(f.getId() == 0) {
            funcionarioDAO.adicionar(f);
            setEntity(new Funcionario());
        } else {
            funcionarioDAO.atualizar(id.get(), f);
        }
        atualizarListaView();
    }

    public void pesquisar() {
        listaView.clear();
        List<Funcionario> encontrados = funcionarioDAO.pesquisarPorNome(nome.get());
        listaView.addAll(encontrados);
    }

    public void novoFuncionario() {
        Funcionario f = new Funcionario();
        f.setId(0);
        setEntity(f);
    }

    public void remover(long id) {
        funcionarioDAO.remover(id);
        atualizarListaView();
    }

    public void atualizarListaView() {
        listaView.clear();
        listaView.addAll(funcionarioDAO.pesquisarPorNome(""));
    }
    public ObservableList<Funcionario>  getListaView() {
        return listaView;
    }
}
