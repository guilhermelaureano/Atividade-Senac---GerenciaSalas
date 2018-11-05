/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.EmpresaDao;
import br.com.container.dao.EmpresaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Empresa;
import br.com.container.modelo.Endereco;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author cel05
 */
@ManagedBean(name = "empresaC")
@ViewScoped
public class EmpresaControle implements Serializable {

    private boolean mostraToolbar = false;
    private boolean pesquisaPorDisciplina = false;
    private String pesqNome = "";
    private Session session;
    private EmpresaDao dao;
    private Empresa empresa;
    private List<Empresa> empresas;
    private DataModel<Empresa> modelEmpresas;
    private Endereco endereco;

    public Endereco getEndereco() {
        if(endereco == null){
            endereco = new Endereco();
        }
        
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void mudaToolbar() {
        empresa = new Empresa();
        empresas = new ArrayList();
        pesqNome = "";
        mostraToolbar = !mostraToolbar;
    }

    public void pesquisar() {
        dao = new EmpresaDaoImpl();
        try {
            abreSessao();

            if (!pesqNome.equals("")) {
            } else if (!pesqNome.equals("")) {
                empresas = dao.pesquisaPorNome(pesqNome, session);
            } else {
                empresas = dao.listaTodos(session);
            }

            modelEmpresas = new ListDataModel(empresas);
            pesqNome = null;
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa professor:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        dao = new EmpresaDaoImpl();
        try {
            abreSessao();
            empresa.setEndereco(endereco);
            endereco.setEmpresa(empresa);
            dao.salvarOuAlterar(empresa, session);
            Mensagem.salvar("Empresa " + empresa.getNome());
        } catch (Exception ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro pesquisa empresa:\n" + ex.getMessage());
        } finally {
            empresa = new Empresa();
            session.close();
        }
    }

    public void alterarEmpresa() {
        mostraToolbar = !mostraToolbar;
        empresa = modelEmpresas.getRowData();
    }



    public void excluir() {
        empresa = modelEmpresas.getRowData();
        dao = new EmpresaDaoImpl();
        try {
            abreSessao();
            dao.remover(empresa, session);
            Mensagem.excluir("Empresa " + empresa.getNome());
            empresa = new Empresa();
        } catch (Exception ex) {
            System.err.println("Erro ao excluir Empresa:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    //Getters e Setters
    public boolean isMostraToolbar() {
        return mostraToolbar;
    }

    public void setMostraToolbar(boolean mostraToolbar) {
        this.mostraToolbar = mostraToolbar;
    }


    public String getPesqNome() {
        return pesqNome;
    }

    public void setPesqNome(String pesqNome) {
        this.pesqNome = pesqNome;
    }


    public Empresa getEmpresa() {
        if (empresa == null) {
            empresa = new Empresa();
        }
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Empresa> getEmpresas() {
        if (empresas == null) {
            empresas = new ArrayList();
        }
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public DataModel<Empresa> getModelEmpresas() {
        return modelEmpresas;
    }

    public void setModelEmpresas(DataModel<Empresa> modelEmpresas) {
        this.modelEmpresas = modelEmpresas;
    }

}
