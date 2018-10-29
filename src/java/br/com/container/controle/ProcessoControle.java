/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.AtividadeDao;
import br.com.container.dao.AtividadeDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.dao.PlanejamentoDao;
import br.com.container.dao.PlanejamentoDaoImpl;
import br.com.container.modelo.Atividade;
import br.com.container.modelo.Planejamento;
import br.com.container.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
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
@ManagedBean(name = "processoC")
@ViewScoped
public class ProcessoControle implements Serializable {

    private boolean mostra_toolbar = false;

    private Session session;
    private PlanejamentoDao planejamentoDao;
    private AtividadeDao atividadeDao;
    private Planejamento planejamento;
    private Usuario usuario;
    private List<Atividade> atividades;
    private Atividade atividade;
    private int porcentagem;

    private DataModel<Atividade> modelAtividades;

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void novo() {
        mostra_toolbar = !mostra_toolbar;
    }

    public void novaPesquisa() {
        mostra_toolbar = !mostra_toolbar;
        inicializar();
    }

    public void preparaAlterar() {
        mostra_toolbar = !mostra_toolbar;
    }

    @PostConstruct
    public void inicializar() {
        //Carrega obj's iniciais do banco de dados
        UsuarioLogado usuarioLogado = new UsuarioLogado();
        abreSessao();
        try {
            listarAtividadeUsuarioLogado(usuarioLogado);
        } catch (HibernateException he) {
            System.out.println("Erro ao carregar atividades " + he.getMessage());
        }

        calcularPorcentagemAtividade();
        session.close();
    }

    private void calcularPorcentagemAtividade() {
        if (!atividades.isEmpty()) {
            int contador = 0;
            for (Atividade atividade : atividades) {
                if (atividade.getDt_entrega() != null) {
                    contador++;
                }
            }
            porcentagem = contador * 100 / atividades.size();
        }
    }

    private void listarAtividadeUsuarioLogado(UsuarioLogado usuarioLogado) throws HibernateException {
        usuario = usuarioLogado.usuarioLogadoSpring(session);
        planejamentoDao = new PlanejamentoDaoImpl();
        planejamento = planejamentoDao.pesquisarPorUsuarioLogado(usuario, session);
        if (planejamento != null) {
            atividades = planejamento.getAtividades();
            modelAtividades = new ListDataModel<>(atividades);
        }else{
            planejamento = new Planejamento();
            atividades = new ArrayList<>();
            planejamento.setAtividades(atividades);
        }
    }

    public void pesquisar() {
        try {
            abreSessao();
//            modelAtividades = new ListDataModel(profs);
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa atividades:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        try {
            abreSessao();
            if (planejamento.getId() == null) {
                Mensagem.mensagemError("Não existe ainda um planejamento aberto para essa atividade");
            } else {
                atividadeDao = new AtividadeDaoImpl();
                atividade.setPlanejamento(planejamento);
                atividadeDao.salvarOuAlterar(atividade, session);
                Mensagem.salvar("Atividade ");
            }
        } catch (Exception ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro ao salvar atividade: " + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void alterarProcesso() {
        mostra_toolbar = !mostra_toolbar;
        atividade = modelAtividades.getRowData();
    }

    public void finalizar() {
        atividade = modelAtividades.getRowData();
        atividade.setDt_entrega(new Date());
        abreSessao();
        try {
            atividadeDao = new AtividadeDaoImpl();
            atividadeDao.finalizar(atividade.getId(), session);
        } catch (HibernateException ex) {
            System.err.println("Erro finalizar atividade: " + ex.getMessage());
        }finally{
            session.close();
        }
    }

    public void excluir() {
//        prof = modelAtividades.getRowData();
        try {
            abreSessao();
//            dao.remover(prof, session);
//            Mensagem.excluir("Professor " + prof.getNome());
//            prof = new Professor();
        } catch (Exception ex) {
            System.err.println("Erro ao excluir professor:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    //Getters e Setters
    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public DataModel<Atividade> getModelAtividades() {
        return modelAtividades;
    }

    public Planejamento getPlanejamento() {
        if (planejamento == null) {
            planejamento = new Planejamento();
        }
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
    }

    public int getPorcentagem() {
        return porcentagem;
    }

    public Atividade getAtividade() {
        if (atividade == null) {
            atividade = new Atividade();
        }
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

}
