package com.example.farmacia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoDAOImpl implements MedicamentoDAO{

    private static final String DBURL = "jdbc:mysql://localhost/medicamentodb";
    private static final String DBUSER = "root";
    private static final String DBPASS = "";

    public MedicamentoDAOImpl() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void adicionar(Medicamento m) {
        try {
            Connection con =  DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "insert into medicamento (id, nome, preco, vencimento) " +
                    "VALUES (?, ? ,?, ?)";

            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, m.getId());
            stmt.setString(2, m.getNome());
            stmt.setDouble(3, m.getPreco());
            stmt.setDate(4, java.sql.Date.valueOf(m.getVencimento()));
            stmt.executeUpdate();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Medicamento> pesquisarPorNome(String nome) {
        List<Medicamento> encontrados = new ArrayList<>();
        try {
            Connection con =  DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "SELECT * FROM medicamento WHERE nome like '%" + nome + "%'";
            System.out.println(sql);

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Medicamento m = new Medicamento();
                m.setId( rs.getLong("id"));
                m.setNome(rs.getString("nome"));
                m.setPreco(rs.getDouble("preco"));
                m.setVencimento(rs.getDate("vencimento").toLocalDate());

                encontrados.add(m);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontrados;
    }

    @Override
    public void atualizar(long id, Medicamento m) {

    }

    @Override
    public void remover(long id) {
        try {
            Connection con =  DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "DELETE FROM medicamento WHERE id = ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, id);
            stmt.executeUpdate();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
