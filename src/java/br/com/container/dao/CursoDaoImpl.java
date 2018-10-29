/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Curso;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Dhyego Pedroso
 */
public class CursoDaoImpl extends BaseDaoImpl<Curso, Long> implements CursoDao {

    @Override
    public Curso pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Curso) session.get(Curso.class, id);
    }

    @Override
    public List<Curso> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Curso").list();
    }

    @Override
    public List<Curso> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Curso c where c.curso like :curso");
        consulta.setParameter("curso", "%" + nome + "%");
        return consulta.list();
    }

}
