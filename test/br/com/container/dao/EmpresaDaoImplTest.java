/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Empresa;
import br.com.container.modelo.Endereco;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aluno
 */
public class EmpresaDaoImplTest {

    private Empresa empresa;
    private Endereco endereco;
    private EmpresaDao empresaDao;
    private Session session;

    public EmpresaDaoImplTest() {
        empresaDao = new EmpresaDaoImpl();
    }

//    @Test
    public void testPesquisaEntidadeId() {
        System.out.println("pesquisaEntidadeId");

    }

    @Test
    public void testeSalvar() {
        session = HibernateUtil.abreSessao();
        System.out.println("Salvar");
        empresa = new Empresa("senac", "rober", "313132132");
        endereco = new Endereco("rua 1", "30", "bairr1", "cidade1", "complemento1", "observacao1");
        empresa.setEndereco(endereco);
        endereco.setEmpresa(empresa);
        empresaDao.salvarOuAlterar(empresa, session);
        session.close();
        assertNotNull(empresa.getId());
    }

//    @Test
    public void testListaTodos() {
        System.out.println("listaTodos");
        Session session = null;
        EmpresaDaoImpl instance = new EmpresaDaoImpl();
        List<Empresa> expResult = null;
        List<Empresa> result = instance.listaTodos(session);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

//    @Test
    public void testPesquisaPorNome() {
        System.out.println("pesquisaPorNome");
        String nome = "";
        Session session = null;
        EmpresaDaoImpl instance = new EmpresaDaoImpl();
        List<Empresa> expResult = null;
        List<Empresa> result = instance.pesquisaPorNome(nome, session);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
