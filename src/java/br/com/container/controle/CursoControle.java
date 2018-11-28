/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.CursoDao;
import br.com.container.dao.CursoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Curso;
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
@ManagedBean(name = "cursoC")
@ViewScoped
public class CursoControle implements Serializable {

    private boolean mostraToolbar = false;
    private String pesqCurso = "";

    private Session session;
    private CursoDao dao;

    private Curso curs;
    private List<Curso> cursos;
    private DataModel<Curso> modelCursos;

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void mudaToolbar() {
        curs = new Curso();
        cursos = new ArrayList();
        pesqCurso = "";
        mostraToolbar = !mostraToolbar;
    }

    public void pesquisar() {
        dao = new CursoDaoImpl();
        try {
            abreSessao();

            if (!pesqCurso.equals("")) {
                cursos = dao.pesquisaPorNome(pesqCurso, session);
            } else {
                cursos = dao.listaTodos(session);
            }

            modelCursos = new ListDataModel(cursos);
            pesqCurso = null;

        } catch (Exception e) {
            System.err.println("Erro pesquisa curso:\n" + e.getMessage());
        } finally {
            session.close();
        }

    }

    public void salvar() {
        dao = new CursoDaoImpl();
        abreSessao();
        try {
            dao.salvarOuAlterar(curs, session);
            Mensagem.salvar("Atividade " + curs.getNome());
            curs = null;
        } catch (HibernateException e) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro ao salvar curso:\n" + e.getMessage());
        } finally {
            curs = new Curso();
            session.close();
        }
    }

    public void alterarCurso() {
        mostraToolbar = !mostraToolbar;
        curs = modelCursos.getRowData();
    }

    public void excluir() {
        curs = modelCursos.getRowData();
        dao = new CursoDaoImpl();
        try {
            abreSessao();
            dao.remover(curs, session);
            Mensagem.excluir("Curso " + curs.getNome());
            curs = new Curso();
        } catch (Exception e) {
            System.err.println("Erro ao excluir curso" + e.getMessage());
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

    public String getPesqCurso() {
        return pesqCurso;
    }

    public void setPesqCurso(String pesqCurso) {
        this.pesqCurso = pesqCurso;
    }

    public Curso getCurs() {
        if (curs == null) {
            curs = new Curso();
        }
        return curs;
    }

    public void setCurs(Curso curs) {
        this.curs = curs;
    }

    public List<Curso> getCursos() {
        if (cursos == null) {
            cursos = new ArrayList();
        }
        return cursos;
    }

    public void setCursos(List<Curso> profs) {
        this.cursos = cursos;
    }

    public DataModel<Curso> getModelCursos() {
        return modelCursos;
    }

    public void setModelCursos(DataModel<Curso> modelCursos) {
        this.modelCursos = modelCursos;
    }

}
