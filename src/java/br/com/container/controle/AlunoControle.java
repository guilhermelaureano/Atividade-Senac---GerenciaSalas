/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.AlunoDao;
import br.com.container.dao.AlunoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Aluno;
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
 * @author guila
 */
@ManagedBean(name = "alunoC")
@ViewScoped
public class AlunoControle implements Serializable {

    private boolean mostraToolbar;
    private Session session;
    private AlunoDao dao;
    private Aluno aluno;
    private List<Aluno> alunos;
    private DataModel<Aluno> modelAlunos;
    private Endereco endereco;

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    private void limparTela() {
        aluno = new Aluno();
        alunos = new ArrayList();
        aluno.setWhatsapp(true);
        aluno.setNome(null);
        endereco = new Endereco();
    }

    public void mudaToolbar() {
        limparTela();
        mostraToolbar = !mostraToolbar;
    }

    public void pesquisar() {
        dao = new AlunoDaoImpl();
        try {
            abreSessao();
            alunos = dao.pesquisaPorNome(aluno.getNome(), session);
            modelAlunos = new ListDataModel(alunos);
            aluno.setNome(null);
        } catch (HibernateException e) {
            System.err.println("Erro ao pesquisar aluno:\n" + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        dao = new AlunoDaoImpl();
        try {
            abreSessao();
            aluno.setEndereco(endereco);
            endereco.setPessoa(aluno);
            dao.salvarOuAlterar(aluno, session);
            Mensagem.salvar("Aluno " + aluno.getNome());
        } catch (HibernateException ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro ao salvar aluno:\n" + ex.getMessage());
        } finally {
            limparTela();
            session.close();
        }
    }

    public void alterarAluno() {
        mostraToolbar = !mostraToolbar;
        aluno = modelAlunos.getRowData();
        endereco = aluno.getEndereco();
    }

    public void excluir() {
        aluno = modelAlunos.getRowData();
        dao = new AlunoDaoImpl();
        try {
            abreSessao();
            dao.remover(aluno, session);
            modelAlunos = null;
            Mensagem.excluir("Aluno " + aluno.getNome());
            aluno = new Aluno();
        } catch (HibernateException ex) {
            System.err.println("Erro ao excluir aluno:\n" + ex.getMessage());
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

    public Aluno getAluno() {
        if (aluno == null) {
            aluno = new Aluno();
        }
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Aluno> getAlunos() {
        if (alunos == null) {
            alunos = new ArrayList();
        }
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public DataModel<Aluno> getModelAlunos() {
        return modelAlunos;
    }

    public void setModelAlunos(DataModel<Aluno> modelAlunos) {
        this.modelAlunos = modelAlunos;
    }

    public Endereco getEndereco() {
        if (endereco == null) {
            endereco = new Endereco();
        }
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
