package com.example.farmacia;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoControl {

    LongProperty id = new SimpleLongProperty(0);
    StringProperty nome = new SimpleStringProperty("");
    DoubleProperty preco = new SimpleDoubleProperty(0);
    ObjectProperty vencimento = new SimpleObjectProperty(LocalDate.now());

    private static long counter = 0;

    private List<Medicamento> lista = new ArrayList<>();
    private ObservableList<Medicamento> listaView = FXCollections.observableArrayList();

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
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            Medicamento medicamento = lista.get(i);
            if(m.getId() == medicamento.getId()) {
                lista.set(i, m);
                encontrado = true;
                break;
            }
        }
        if(!encontrado) {
            lista.add(m);
        }

        atualizarListaView();
    }

    public void pesquisar() {
        listaView.clear();
        for (Medicamento m : lista) {
            if (m.getNome().contains(nome.get())) {
                listaView.add(m);
            }
        }
    }

    public void novoPet() {
        Medicamento m = new Medicamento();
        m.setId(++counter);
        setEntity(m);
    }

    public void remover(long id) {
        for ( Medicamento m : lista) {
            if(m.getId() == id) {
                lista.remove(m);
                break;
            }
        }
        atualizarListaView();
    }

    public void atualizarListaView() {
        listaView.clear();
        listaView.addAll(lista);
    }
    public ObservableList<Medicamento>  getListaView() {
        return listaView;
    }
}
