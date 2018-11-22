/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Equipamento;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author guila
 */
public class EquipamentoDaoImpl extends BaseDaoImpl<Equipamento, Long> implements EquipamentoDao{

    @Override
    public Equipamento pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Equipamento) session.get(Equipamento.class, id);
    }

    @Override
    public List<Equipamento> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Equipamento").list();
    }

    @Override
    public List<Equipamento> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Equipamento e where e.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
    @Override
    public List<Equipamento> pesquisaPorNum(String numPatrimonio, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Equipamento e where e.numPatrimonio like :numPatrimonio");
        consulta.setParameter("numPatrimonio", "%" + numPatrimonio + "%");
        return consulta.list();
    }

    @Override
    public List<Equipamento> pesqEquipamento(String nome, String numPatrimonio, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Equipamento e where e.nome like :nome and e.numPatrimonio like :numPatrimonio");
        consulta.setParameter("nome", "%" + nome + "%");
        consulta.setParameter("numPatrimonio", ";%" + numPatrimonio + "%;");
        return consulta.list();
    }
    
    
    
}
