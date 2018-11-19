/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.CarteirinhaDao;
import br.com.container.dao.CarteirinhaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Carteirinha;
import br.com.container.modelo.Endereco;
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
 * @author Aluno
 */

@ManagedBean(name = "carteirinhaC")
@ViewScoped
public class CarteirinhaControle {
    private boolean mostraToolbar;
    private Session session;
    private CarteirinhaDao dao;
    private Carteirinha carteirinha;
    private List<Carteirinha> carteirinhas;
    private DataModel<Carteirinha> modelCarteirinhas;
    private Endereco endereco;

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void mudaToolbar() {
        carteirinha = new Carteirinha();
        carteirinhas = new ArrayList();
        carteirinha.setNum(null);
        endereco = new Endereco();
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
            dao.salvarOuAlterar(carteirinha, session);
            Mensagem.salvar("Aluno " + carteirinha.getNum());
        } catch (HibernateException ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro ao pesquisar aluno:\n" + ex.getMessage());
        } finally {
            carteirinha = new Carteirinha();
            session.close();
        }
    }

    public void alterarCarteirinha() {
        mostraToolbar = !mostraToolbar;
        carteirinha = modelCarteirinhas.getRowData();
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
    
    
    

}
