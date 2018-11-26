/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.AlunoDao;
import br.com.container.dao.AlunoDaoImpl;
import br.com.container.dao.CarteirinhaDao;
import br.com.container.dao.CarteirinhaDaoImpl;
import br.com.container.dao.CursoDao;
import br.com.container.dao.CursoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Aluno;
import br.com.container.modelo.Carteirinha;
import br.com.container.modelo.Curso;
import br.com.container.modelo.Endereco;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
@ManagedBean(name = "carteirinhaC")
@ViewScoped
public class CarteirinhaControle implements Serializable {

    private boolean mostraToolbar;
    private Session session;
    private CarteirinhaDao dao;
    private Carteirinha carteirinha;
    private List<Carteirinha> carteirinhas;
    private DataModel<Carteirinha> modelCarteirinhas;
    private Endereco endereco;
    private Aluno aluno;
    private Curso curso;

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void mudaToolbar() {
        mostraToolbar = !mostraToolbar;
    }

    public void pesquisar() {
        dao = new CarteirinhaDaoImpl();
        try {
            abreSessao();
            if (!carteirinha.getNum().equals("")) {
                carteirinhas = dao.pesquisaPorNumero(carteirinha.getNum(), session);
            } else {
                carteirinhas = dao.listaTodos(session);
            }
            modelCarteirinhas = new ListDataModel(carteirinhas);
            carteirinha.setNum(null);
        } catch (HibernateException ex) {
            System.err.println("Erro ao pesquisar carteirinha:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        dao = new CarteirinhaDaoImpl();
        try {
            abreSessao();
            carteirinha.setValidade(new Date());
            carteirinha.setAluno(aluno);
            carteirinha.setCurso(curso);
            carregaAluno();
            carteirinha.setNum(aluno.getCPF());
            dao.salvarOuAlterar(carteirinha, session);
            Mensagem.salvar("Carteirinha " + carteirinha.getNum());
            carteirinha = new Carteirinha();
        } catch (HibernateException ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro ao pesquisar aluno:\n" + ex.getMessage());
        } finally {

            session.close();
        }
    }

    private void carregaAluno() {
        AlunoDao alunoDao = new AlunoDaoImpl();
        try {
            aluno = alunoDao.pesquisaEntidadeId(aluno.getId(), session);
        } catch (Exception e) {
            System.out.println("Erro ao carregar aluno " + e.getMessage());
        }
    }

    public void alterarCarteirinha() {
        mostraToolbar = !mostraToolbar;
        carteirinha = modelCarteirinhas.getRowData();
        aluno = carteirinha.getAluno();
        curso = carteirinha.getCurso();
    }

    public void excluir() {
        carteirinha = modelCarteirinhas.getRowData();
        dao = new CarteirinhaDaoImpl();
        try {
            abreSessao();
            dao.remover(carteirinha, session);
            Mensagem.excluir("Carteirinha " + carteirinha.getNum());
            carteirinha = new Carteirinha();
        } catch (HibernateException ex) {
            System.err.println("Erro ao excluir carteirinha:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public List<Aluno> pesquisarAluno(String query) {
        List<Aluno> alunos = null;
        abreSessao();
        AlunoDao alunoDao = new AlunoDaoImpl();
        try {
            alunos = alunoDao.pesquisaPorNome(query, session);
        } catch (HibernateException he) {
            System.out.println("Erro no pesquisaAluno Carteirinha Controle " + he.getMessage());
        } finally {
            session.close();
        }
        return alunos;
    }

    public List<Curso> pesquisarCurso(String query) {
        List<Curso> cursos = null;
        abreSessao();
        CursoDao cursoDao = new CursoDaoImpl();
        try {
            cursos = cursoDao.pesquisaPorNome(query, session);
        } catch (HibernateException he) {
            System.out.println("Erro no pesquisaCurso Carteirinha Controle " + he.getMessage());
        } finally {
            session.close();
        }
        return cursos;
    }

    //Getters e Setters
    public boolean isMostraToolbar() {
        return mostraToolbar;
    }

    public void setMostraToolbar(boolean mostraToolbar) {
        this.mostraToolbar = mostraToolbar;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public CarteirinhaDao getDao() {
        return dao;
    }

    public void setDao(CarteirinhaDao dao) {
        this.dao = dao;
    }

    public Carteirinha getCarteirinha() {
        if (carteirinha == null) {
            carteirinha = new Carteirinha();
        }
        return carteirinha;
    }

    public void setCarteirinha(Carteirinha carteirinha) {
        this.carteirinha = carteirinha;
    }

    public List<Carteirinha> getCarteirinhas() {
        return carteirinhas;
    }

    public void setCarteirinhas(List<Carteirinha> carteirinhas) {
        this.carteirinhas = carteirinhas;
    }

    public DataModel<Carteirinha> getModelCarteirinhas() {
        return modelCarteirinhas;
    }

    public void setModelCarteirinhas(DataModel<Carteirinha> modelCarteirinhas) {
        this.modelCarteirinhas = modelCarteirinhas;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
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

    public Curso getCurso() {
        if (curso == null) {
            curso = new Curso();
        }
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

}
