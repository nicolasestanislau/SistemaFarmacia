package com.example.farmacia;

import java.util.List;

public interface FuncionarioDAO {
    void adicionar(Funcionario f);
    List<Funcionario> pesquisarPorNome(String nome);
    void atualizar(long id, Funcionario f);
    void remover(long id);
}
