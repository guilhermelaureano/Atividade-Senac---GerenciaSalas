/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Carteirinha;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Aluno
 */
public interface CarteirinhaDao extends BaseDao<Carteirinha, Long> {

    public List<Carteirinha> pesquisaPorNome(String nome, Session session) throws HibernateException;
    
    public List<Carteirinha> pesquisaPorNumero(String numero, Session session) throws HibernateException;
    
    public List<Carteirinha> pesquisaPorNomeNumero(String nome, String numero, Session session) throws HibernateException;

}
