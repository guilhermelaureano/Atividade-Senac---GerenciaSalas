/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Planejamento;
import br.com.container.modelo.Usuario;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public interface PlanejamentoDao extends BaseDao<Planejamento, Long>{
    
    Planejamento pesquisarPorUsuarioLogado(Usuario usuario, Session session) throws HibernateException;
}
