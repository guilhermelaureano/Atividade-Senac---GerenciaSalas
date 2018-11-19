/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Carteirinha;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public class CarteirinhaDaoImpl extends BaseDaoImpl<Carteirinha, Long> implements CarteirinhaDao {

    @Override
    public Carteirinha pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Carteirinha) session.get(Carteirinha.class, id);
    }

    @Override
    public List<Carteirinha> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Carteirinha").list();
    }

    @Override
    public List<Carteirinha> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Carteirinha c where c.nome like :nome");
        consulta.setParameter("nome", "%"+ nome + "%");
        return consulta.list();
    }
    
    public List<Carteirinha> pesquisaPorNumero(String numero, Session session) throws HibernateException{
        Query consulta = session.createQuery("from Carteirinha c where c.numero like :numero");
        consulta.setParameter("numero", "%"+numero+"%");
        return consulta.list();
    }
    
}
