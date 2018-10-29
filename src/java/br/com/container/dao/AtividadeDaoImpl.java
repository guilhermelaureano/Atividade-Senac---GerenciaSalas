/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Atividade;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class AtividadeDaoImpl extends BaseDaoImpl<Atividade, Long> implements AtividadeDao, Serializable{

    @Override
    public Atividade pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Atividade) session.get(Atividade.class, id);
    }

    @Override
    public List<Atividade> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Atividade").list();
    }

    @Override
    public List<Atividade> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Atividade where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public void finalizar(Long id, Session session) throws HibernateException {
        Query consulta = session.createQuery("UPDATE Atividade a SET a.dt_entrega = now() where a.id = :id");
        consulta.setParameter("id", id);
        consulta.executeUpdate();
    }
    
    
}
