package com.example.farmacia;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class MedicamentoControl {

    LongProperty id = new SimpleLongProperty(0);
    StringProperty nome = new SimpleStringProperty("");
    DoubleProperty preco = new SimpleDoubleProperty(0);
    ObjectProperty vencimento = new SimpleObjectProperty(LocalDate.now());

    private ObservableList<Medicamento> listaView = FXCollections.observableArrayList();
    private MedicamentoDAO medicamentoDAO = new MedicamentoDAOImpl();

    public Medicamento getEntity() {
        Medicamento m = new Medicamento();
        m.setId(id.get());
        m.setNome(nome.get());
        m.setPreco(preco.get());
        m.setVencimento((LocalDate) vencimento.get());
        return m;
    }

    public void setEntity(Medicamento m) {
        id.set(m.getId());
        nome.set(m.getNome());
        preco.set(m.getPreco());
        vencimento.set(m.getVencimento());
    }

    public void salvar() {
        Medicamento m = getEntity();
        if(m.getId() == 0) {
            medicamentoDAO.adicionar(m);
            setEntity(new Medicamento());
        } else {
            medicamentoDAO.atualizar(id.get(), m);
        }

        atualizarListaView();
    }

    public void pesquisar() {
        listaView.clear();
        List<Medicamento> encontrados = medicamentoDAO.pesquisarPorNome(nome.get());
        listaView.addAll(encontrados);
    }

    public void novoMedicamento() {
        Medicamento m = new Medicamento();
        m.setId(0);
        setEntity(m);
    }

    public void remover(long id) {
        medicamentoDAO.remover(id);
        atualizarListaView();
    }

    public void atualizarListaView() {
        listaView.clear();
        listaView.addAll(medicamentoDAO.pesquisarPorNome(""));
    }
    public ObservableList<Medicamento>  getListaView() {
        return listaView;
    }
}
