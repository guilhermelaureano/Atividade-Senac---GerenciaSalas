/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.HibernateUtil;
import br.com.container.dao.PlanejamentoDao;
import br.com.container.dao.PlanejamentoDaoImpl;
import br.com.container.dao.UsuarioDao;
import br.com.container.dao.UsuarioDaoImpl;
import br.com.container.modelo.Planejamento;
import br.com.container.modelo.Usuario;
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
@ManagedBean(name = "planejaC")
@ViewScoped
public class PlanejamentoControle implements Serializable {

    private Planejamento planejamento;
    private PlanejamentoDao planejamentoDao;
    private Session sessao;
    private Usuario usuario;
    private DataModel<Planejamento> modelPlanejamentos;
    private List<Planejamento> planejamentos;
    private List<Usuario> usuarios;
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
        planejamentoDao = new PlanejamentoDaoImpl();
        try {
            abreSessao();
            planejamentos = planejamentoDao.pesquisaPorNome(planejamento.getNome(), sessao);
            modelPlanejamentos = new ListDataModel(planejamentos);
            planejamento.setNome(null);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar Planejamento por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        planejamento = new Planejamento();
    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        planejamento = modelPlanejamentos.getRowData();
        usuario = planejamento.getUsuario();
    }

    public void excluir() {
        planejamento = modelPlanejamentos.getRowData();
        planejamentoDao = new PlanejamentoDaoImpl();
        abreSessao();
        try {
            planejamentoDao.remover(planejamento, sessao);
            planejamentos.remove(planejamento);
            modelPlanejamentos = new ListDataModel(planejamentos);
            Mensagem.excluir("Planejamento");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir Planejamento: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        planejamentoDao = new PlanejamentoDaoImpl();
        abreSessao();
        try {
            planejamento.setUsuario(usuario);
            planejamentoDao.salvarOuAlterar(planejamento, sessao);
            Mensagem.salvar("Planejamento " + planejamento.getNome());
            planejamento = null;
        } catch (HibernateException e) {
            System.out.println("Erro ao Planejamento Sala " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar Planejamento Controle " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public List<Usuario> pesquisaConvidado(String query) {
        abreSessao();
        UsuarioDao usuarioDao = new UsuarioDaoImpl();
        try {
            usuarios = usuarioDao.pesquisaPorNome(query, sessao);
        } catch (HibernateException he) {
            System.out.println("Erro no pesquisaConvidado Planejamento Controle " + he.getMessage());
        } finally {
            sessao.close();
        }
        return usuarios;
    }

    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Planejamento getPlanejamento() {
        if (planejamento == null) {
            planejamento = new Planejamento();
        }
        return planejamento;
    }

    public void setPlanejamento(Planejamento planejamento) {
        this.planejamento = planejamento;
    }

    public DataModel<Planejamento> getModelPlanejamentos() {
        return modelPlanejamentos;
    }

    public void setModelPlanejamentos(DataModel<Planejamento> modelPlanejamentos) {
        this.modelPlanejamentos = modelPlanejamentos;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Planejamento> getPlanejamentos() {
        return planejamentos;
    }

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

}
