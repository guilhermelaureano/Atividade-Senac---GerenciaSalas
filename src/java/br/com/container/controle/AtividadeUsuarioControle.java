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
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author silvio
 */
@ManagedBean(name = "atividadeUsuC")
@ViewScoped
public class AtividadeUsuarioControle implements Serializable {

    private Atividade atividade;
    private Planejamento planejamento;
    private AtividadeDao atividadeDao;
    private PlanejamentoDao planejamentoDao;
    private Session sessao;
    private DataModel<Atividade> modelAtividade;
    private List<Atividade> atividades;
    private List<Planejamento> planejamentos;
    private boolean mostra_toolbar;

    private void abreSessao() {
        if (sessao == null) {
            sessao = HibernateUtil.abreSessao();
        } else if (!sessao.isOpen()) {
            sessao = HibernateUtil.abreSessao();
        }
    }

    public void novo() {
        mostra_toolbar = !mostra_toolbar;

    }

    public void novaPesquisa() {
        mostra_toolbar = !mostra_toolbar;
    }

    public void preparaAlterar() {
        mostra_toolbar = !mostra_toolbar;
    }

    public void pesquisar() {
        
    }
    
    public List<Planejamento> pesquisar(String query) {
        planejamentoDao = new PlanejamentoDaoImpl();
        try {
            abreSessao();
            planejamentos = planejamentoDao.pesquisaPorNome(query, sessao);
//            modelAtividade = new ListDataModel(atividades);
//            planejamento.setNome(null);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar planejamento por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
        return planejamentos;
    }

    public void limpar() {
        atividade = new Atividade();
    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        atividade = modelAtividade.getRowData();
        planejamento = atividade.getPlanejamento();
    }

    public void excluir() {
        atividade = modelAtividade.getRowData();
        atividadeDao = new AtividadeDaoImpl();
        abreSessao();
        try {
            atividadeDao.remover(atividade, sessao);
            atividades.remove(atividade);
            modelAtividade = new ListDataModel(atividades);
            Mensagem.excluir("Atividade");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir Atividade: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        atividadeDao = new AtividadeDaoImpl();
        abreSessao();
        try {
            atividadeDao.salvarOuAlterar(atividade, sessao);
            Mensagem.salvar("Atividade " + atividade.getNome());
            atividade = null;
        } catch (HibernateException e) {
            System.out.println("Erro ao Atividade Sala " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no Atividade Sala Controle " + e.getMessage());
        } finally {
            sessao.close();
        }
    }


    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Atividade getAtividade() {
        if (atividade == null) {
            atividade = new Atividade();
        }
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }
    
    public DataModel<Atividade> getModelAtividade() {
        return modelAtividade;
    }

    public void setModelAtividade(DataModel<Atividade> modelAtividade) {
        this.modelAtividade = modelAtividade;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public Planejamento getPlanejamento() {
        if(planejamento == null){
            planejamento = new Planejamento();
        }
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
    }

    public List<Planejamento> getPlanejamentos() {
        return planejamentos;
    }

}
