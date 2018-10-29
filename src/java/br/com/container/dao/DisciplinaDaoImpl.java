/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Disciplina;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author felipedania
 */
public class DisciplinaDaoImpl extends BaseDaoImpl<Disciplina, Long> implements DisciplinaDao, Serializable {

    @Override
    public Disciplina pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Disciplina) session.get(Disciplina.class, id);
    }

    @Override
    public List<Disciplina> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Disciplina").list();
    }

    @Override
    public List<Disciplina> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Disciplina d WHERE d.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

}
