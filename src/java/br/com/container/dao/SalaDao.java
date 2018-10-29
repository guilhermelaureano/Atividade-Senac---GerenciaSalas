/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.DiaDaSemana;
import br.com.container.modelo.Reserva;
import br.com.container.modelo.Sala;
import br.com.container.modelo.SalaLimpeza;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public interface SalaDao extends BaseDao<Sala, Long> {

    List<Sala> pesquisaSalaComReserva(Reserva reserva, Session session) throws HibernateException;

    List<Sala> pesquisaSalaComReserva(Date inicio, Date fim, List<DiaDaSemana> dias, String periodo, Session session) throws HibernateException;
    
    List<Sala> pesquisaSalaComReserva(Long id, Date inicio, Date fim, List<DiaDaSemana> dias, String periodo, Session session) throws HibernateException;

    List<Sala> pesquisaSalaSemReserva(Reserva reserva, Session session) throws HibernateException;

    List<Sala> pesquisaSalaSemReserva(Long id, Date inicio, Date fim, List<DiaDaSemana> dias, String periodo, Session session) throws HibernateException;

    List<Sala> pesquisaSalaSemReserva(List<Reserva> reservas, Session session) throws HibernateException;
    
    List<SalaLimpeza> todasSalaLimpezaDoDia(Session session) throws HibernateException;
    
    Long totalSala(Session session) throws HibernateException;
    
    List<SalaLimpeza> todasSalaLimpezaDiaAnterior(Session session) throws HibernateException;
    
    List<SalaLimpeza> todasSalaLimpezaProximoDia(Session session) throws HibernateException;
}
