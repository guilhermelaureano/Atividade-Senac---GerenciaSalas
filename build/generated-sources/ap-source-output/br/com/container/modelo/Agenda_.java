package br.com.container.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Agenda.class)
public abstract class Agenda_ {

	public static volatile SingularAttribute<Agenda, String> assunto;
	public static volatile SingularAttribute<Agenda, Date> email3;
	public static volatile SingularAttribute<Agenda, Date> email2;
	public static volatile SingularAttribute<Agenda, Date> dia_evento;
	public static volatile SingularAttribute<Agenda, Date> cadastroEvento;
	public static volatile SingularAttribute<Agenda, String> convidado;
	public static volatile SingularAttribute<Agenda, Usuario> usuario;
	public static volatile SingularAttribute<Agenda, Long> id;
	public static volatile SingularAttribute<Agenda, String> descricao;

}

