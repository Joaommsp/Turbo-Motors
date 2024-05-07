package br.com.turbomotors.turbomotors.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.turbomotors.turbomotors.Repositorio.FuncionarioRepositorio;
import br.com.turbomotors.turbomotors.Tabelas.Funcionarios;

@Service

public class ImplementarFuncDetailsService implements UserDetailsService {

    @Autowired
    private FuncionarioRepositorio funcionarioAcao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("Entrou no userDetails Implementa. ");
        Funcionarios admin = funcionarioAcao.obterUsuario(username);
        System.out.println(admin.getUser() + admin.getPassword());
        if(admin == null){
            throw new UsernameNotFoundException("Usuário não foi encontrado.");
        }
        return admin;
    }

}
