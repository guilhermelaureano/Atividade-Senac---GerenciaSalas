package br.com.container.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Planejamento.class)
public abstract class Planejamento_ {

	public static volatile ListAttribute<Planejamento, Atividade> atividades;
	public static volatile SingularAttribute<Planejamento, Integer> progresso;
	public static volatile SingularAttribute<Planejamento, Boolean> aberto;
	public static volatile SingularAttribute<Planejamento, String> nome;
	public static volatile SingularAttribute<Planejamento, Usuario> usuario;
	public static volatile SingularAttribute<Planejamento, Long> id;
	public static volatile SingularAttribute<Planejamento, String> semestre;

}

