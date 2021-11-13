package com.example.farmacia;

import java.time.LocalDate;

public class Medicamento {

    private long id = 0;
    private String nome ="";
    private double preco = 0;
    private LocalDate vencimento = LocalDate.now();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
