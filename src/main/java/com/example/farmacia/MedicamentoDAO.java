package com.example.farmacia;

import java.util.List;

public interface MedicamentoDAO {
    void adicionar(Medicamento m);
    List<Medicamento> pesquisarPorNome(String nome);
    void atualizar(long id, Medicamento m);
    void remover(long id);
}
