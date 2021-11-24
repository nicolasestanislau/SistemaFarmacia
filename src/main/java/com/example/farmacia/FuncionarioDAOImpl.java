package com.example.farmacia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    private static final String DBURL = "jdbc:mysql://localhost/funcionariodb";
    private static final String DBUSER = "root";
    private static final String DBPASS = "";

    public FuncionarioDAOImpl() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void adicionar(Funcionario f) {
        try {
            Connection con =  DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "insert into funcionario (id, nome, cargo, salario) " +
                    "VALUES (?, ? ,?, ?)";

            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, f.getId());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getCargo());
            stmt.setDouble(4, f.getSalario());
            stmt.executeUpdate();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Funcionario> pesquisarPorNome(String nome) {
        List<Funcionario> encontrados = new ArrayList<>();
        try {
            Connection con =  DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "SELECT * FROM funcionario WHERE nome like '%" + nome + "%'";
            System.out.println(sql);

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Funcionario f = new Funcionario();
                f.setId( rs.getLong("id"));
                f.setNome(rs.getString("nome"));
                f.setCargo(rs.getString("cargo"));
                f.setSalario(rs.getDouble("salario"));

                encontrados.add(f);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontrados;
    }

    @Override
    public void atualizar(long id, Funcionario f) {
        try {
            Connection con =  DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "update funcionario  set id = ?, nome = ?, cargo = ?, salario = ? where id = ?";
            System.out.println(sql);
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setLong(1, f.getId());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getCargo());
            stmt.setDouble(4, f.getSalario());
            stmt.setLong(5, f.getId());
            stmt.executeUpdate();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void remover(long id) {
        try {
            Connection con =  DriverManager.getConnection(DBURL, DBUSER, DBPASS);

            String sql = "DELETE FROM funcionario WHERE id = ?";
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
