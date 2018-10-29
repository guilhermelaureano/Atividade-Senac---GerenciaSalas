/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Planejamento;
import br.com.container.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class PlanejamentoDaoImpl extends BaseDaoImpl<Planejamento, Long> implements PlanejamentoDao, Serializable{

    @Override
    public Planejamento pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Planejamento) session.get(Planejamento.class, id);
    }

    @Override
    public List<Planejamento> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Planejamento").list();
    }

    @Override
    public List<Planejamento> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Planejamento p left join fetch p.atividades where p.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public Planejamento pesquisarPorUsuarioLogado(Usuario usuario, Session session) throws HibernateException {
         Query consulta = session.createQuery("from Planejamento p left join fetch p.atividades where"
                 + " p.usuario.id = :idUsuario and p.aberto = true");
        consulta.setParameter("idUsuario", usuario.getId());
        return (Planejamento) consulta.uniqueResult();
    }
    
//    public static void main(String[] args) {
//        PlanejamentoDao pd = new PlanejamentoDaoImpl();
//        Usuario usuario = new Usuario();
//        usuario.setId(5L);
//        Session session = HibernateUtil.abreSessao();
//        Planejamento pla =  pd.pesquisarPorUsuarioLogado(usuario, session);
//        System.out.println("pla " + pla.getAtividades());
//        session.close();
//    }
}
