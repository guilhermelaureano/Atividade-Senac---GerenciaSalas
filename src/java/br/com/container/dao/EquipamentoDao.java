/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Equipamento;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author guila
 */
public interface EquipamentoDao extends BaseDao<Equipamento, Long> {

    List<Equipamento> pesqEquipamento(String nome, String numPatrimonio, Session session) throws HibernateException;
    
    List<Equipamento> pesquisaPorNum(String numPatrimonio, Session session) throws HibernateException;

}
