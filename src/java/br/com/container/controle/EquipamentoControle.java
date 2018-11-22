/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.EquipamentoDao;
import br.com.container.dao.EquipamentoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Equipamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
@ManagedBean(name = "equipamentoC")
@ViewScoped
public class EquipamentoControle implements Serializable {

    private boolean mostraToolbar = false;
    private boolean pesqEquipamento = false;
    private String pesqNome = "";
    private String pesqNumPatrimonio = "";
    private Session session;
    private EquipamentoDao dao;
    private Equipamento equip;
    private List<Equipamento> equips;
    private DataModel<Equipamento> modelEquips;

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    private void limpar() {
        equip = new Equipamento();
        equips = new ArrayList();
    }

    public void mudaToolbar() {
        limpar();
        pesqNome = "";
        pesqNumPatrimonio = "";
        mostraToolbar = !mostraToolbar;
    }

    public void pesquisar() {
        dao = new EquipamentoDaoImpl();
        try {
            abreSessao();
            if (!pesqNome.equals("") && !pesqNumPatrimonio.equals("")) {
                equips = dao.pesqEquipamento(pesqNome, pesqNumPatrimonio, session);
            } else if (!pesqNome.equals("")) {
                equips = dao.pesquisaPorNome(pesqNome, session);
            } else if (!pesqNumPatrimonio.equals("")) {
                equips = dao.pesquisaPorNum(pesqNome, session);
            } else {
                equips = dao.listaTodos(session);
            }
            modelEquips = new ListDataModel(equips);
            pesqNome = null;
            pesqNumPatrimonio = null;
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa equipamento:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        dao = new EquipamentoDaoImpl();
        try {
            abreSessao();
            Date dtCad = new Date();
            equip.setDataCad(dtCad);
            dao.salvarOuAlterar(equip, session);
            Mensagem.salvar("Equipamento " + equip.getNome());
        } catch (HibernateException ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro pesquisa equipamento:\n" + ex.getMessage());
        } finally {
            limpar();
            session.close();
        }
    }

    public void alterar() {
        mostraToolbar = !mostraToolbar;
        equip = modelEquips.getRowData();
    }

    public void excluir() {
        equip = modelEquips.getRowData();
        dao = new EquipamentoDaoImpl();
        try {
            abreSessao();
            dao.remover(equip, session);
            Mensagem.excluir("Equipamento " + equip.getNome());
            equip = new Equipamento();
        } catch (HibernateException ex) {
            System.err.println("Erro ao excluir equipamento:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    //Getter e setter
    public boolean isMostraToolbar() {
        return mostraToolbar;
    }

    public void setMostraToolbar(boolean mostraToolbar) {
        this.mostraToolbar = mostraToolbar;
    }

    public boolean isPesqEquipamento() {
        return pesqEquipamento;
    }

    public void setPesqEquipamento(boolean pesqEquipamento) {
        this.pesqEquipamento = pesqEquipamento;
    }

    public String getPesqNome() {
        return pesqNome;
    }

    public void setPesqNome(String pesqNome) {
        this.pesqNome = pesqNome;
    }

    public String getPesqNumPatrimonio() {
        return pesqNumPatrimonio;
    }

    public void setPesqNumPatrimonio(String pesqNumPatrimonio) {
        this.pesqNumPatrimonio = pesqNumPatrimonio;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public EquipamentoDao getDao() {
        return dao;
    }

    public void setDao(EquipamentoDao dao) {
        this.dao = dao;
    }

    public Equipamento getEquip() {
        return equip;
    }

    public void setEquip(Equipamento equip) {
        this.equip = equip;
    }

    public List<Equipamento> getEquips() {
        return equips;
    }

    public void setEquips(List<Equipamento> equips) {
        this.equips = equips;
    }

    public DataModel<Equipamento> getModelEquips() {
        return modelEquips;
    }

    public void setModelEquips(DataModel<Equipamento> modelEquips) {
        this.modelEquips = modelEquips;
    }

}
