/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.DisciplinaDao;
import br.com.container.dao.DisciplinaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Disciplina;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;

/**
 *
 * @author felipedania
 */
@ManagedBean(name = "DisciplinaC")
@ViewScoped
public class DisciplinaControle implements Serializable{
    
    Disciplina disciplina;
    DisciplinaDao disciplinaDao;
    Session sessao;
    
    
    private void abreSessao() {
        if (sessao == null) {
            sessao = HibernateUtil.abreSessao();
        } else if (!sessao.isOpen()) {
            sessao = HibernateUtil.abreSessao();
        }
    }
    
    

}
