package br.com.container.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reserva.class)
public abstract class Reserva_ {

	public static volatile SingularAttribute<Reserva, String> informacao;
	public static volatile SingularAttribute<Reserva, String> periodo;
	public static volatile SingularAttribute<Reserva, Sala> sala;
	public static volatile SingularAttribute<Reserva, Date> inicio;
	public static volatile ListAttribute<Reserva, DiaDaSemana> diasDaSemana;
	public static volatile SingularAttribute<Reserva, Usuario> usuario;
	public static volatile SingularAttribute<Reserva, Long> id;
	public static volatile SingularAttribute<Reserva, Date> fim;

}

