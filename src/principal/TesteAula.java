/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import modelo.Funcionario;

/**
 *
 * @author geraldo
 */
public class TesteAula {
    public static void main(String[] args) {
        Funcionario func = new Funcionario();
        func.setNome("João");
        System.out.println("O nome do funcionario "
                + "é "+func.getNome());
    }
}
