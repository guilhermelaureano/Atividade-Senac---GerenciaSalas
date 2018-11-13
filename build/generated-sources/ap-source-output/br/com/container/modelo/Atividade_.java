package br.com.container.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Atividade.class)
public abstract class Atividade_ {

	public static volatile SingularAttribute<Atividade, Integer> progresso;
	public static volatile SingularAttribute<Atividade, String> observacao;
	public static volatile SingularAttribute<Atividade, Date> dt_prazoFinal;
	public static volatile SingularAttribute<Atividade, String> nome;
	public static volatile SingularAttribute<Atividade, Long> id;
	public static volatile SingularAttribute<Atividade, Date> dt_inicio;
	public static volatile SingularAttribute<Atividade, Date> dt_entrega;
	public static volatile SingularAttribute<Atividade, Planejamento> planejamento;
	public static volatile SingularAttribute<Atividade, String> descricao;

}

