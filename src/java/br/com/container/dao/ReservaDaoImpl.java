/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.DiaDaSemana;
import br.com.container.modelo.Reserva;
import br.com.container.modelo.Sala;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class ReservaDaoImpl extends BaseDaoImpl<Reserva, Long> implements ReservaDao {

    @Override
    public Reserva pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        Reserva reserva = (Reserva) session.get(Reserva.class, id);
        reserva.setDiasDaSemana(pesqDiasDaReserva(reserva, session));
        return reserva;
    }

    @Override
    public List<Reserva> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Reserva order by r.sala.nome").list();
    }

    @Override
    public List<Reserva> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Reserva s where s.nome like :informacao");
        consulta.setParameter("nome", "%" + nome + "%");
        List<Reserva> reservas = consulta.list();
        for (Reserva reserva : reservas) {
            reserva.setDiasDaSemana(pesqDiasDaReserva(reserva, session));
        }
        return reservas;
    }

    @Override
    public List<Reserva> pesquisarReservaPorSala(String sala, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Reserva r where r.sala.nome Like :sala order by r.inicio, r.sala.nome");
        consulta.setParameter("sala", "%" + sala + "%");
        List<Reserva> reservas = consulta.list();
        for (Reserva reserva : reservas) {
            reserva.setDiasDaSemana(pesqDiasDaReserva(reserva, session));
        }
        return reservas;
    }

    @Override
    public List<Reserva> pesquisarReservaPorSala(Sala sala, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Reserva r where r.sala.id = :idSala group by r.id order by r.inicio");
        consulta.setParameter("idSala", sala.getId());
        List<Reserva> reservas = consulta.list();
        for (Reserva reserva : reservas) {
            reserva.setDiasDaSemana(pesqDiasDaReserva(reserva, session));
        }
        return reservas;
    }

    @Override
    public List<Reserva> pesquisarReservaPorSala(Sala sala, Date inicio, Date fim, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Reserva r where r.sala.id = :idSala  group by r.id");
        consulta.setParameter("idSala", sala.getId());
        List<Reserva> reservas = consulta.list();
        for (Reserva reserva : reservas) {
            reserva.setDiasDaSemana(pesqDiasDaReserva(reserva, session));
        }
        return reservas;
    }

    @Override
    public List<Reserva> pesquisarReservaPorSala(List<Sala> salas, Session session) throws HibernateException {
        List<Long> ids = new ArrayList<>();
        for (Sala sala : salas) {
            ids.add(sala.getId());
        }
        Query consulta = session.createQuery("select r from Reserva r join r.diasDaSemana where r.sala.id in (:idSala) group by r.id");
        consulta.setParameterList("idSala", ids);
        List<Reserva> reservas = consulta.list();
        for (Reserva reserva : reservas) {
            reserva.setDiasDaSemana(pesqDiasDaReserva(reserva, session));
        }
        return reservas;
    }

    @Override
    public List<Reserva> pesquisarReservaPorSala(List<Sala> sala, Date inicio, Date fim, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private List<DiaDaSemana> pesqDiasDaReserva(Reserva reserva, Session session){
        List<Long> ids = new ArrayList<>();
        for (DiaDaSemana diasDaSemana : reserva.getDiasDaSemana()) {
            ids.add(diasDaSemana.getId());
        }
        Query consulta = session.createQuery("from DiaDaSemana d where d.id in (:idDias)");
        consulta.setParameterList("idDias", ids);
        return consulta.list();
    }
    
    public static void main(String[] args) {
        Session session = HibernateUtil.abreSessao();
        ReservaDaoImpl daoImpl = new ReservaDaoImpl();
        SalaDao dao = new SalaDaoImpl();
        List<Sala> salas = dao.listaTodos(session);
        List<Reserva> todas = daoImpl.pesquisarReservaPorSala(salas, session);
        System.out.println(todas);
        session.close();
    }

}
