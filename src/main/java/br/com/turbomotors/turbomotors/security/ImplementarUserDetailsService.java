package br.com.turbomotors.turbomotors.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.turbomotors.turbomotors.Repositorio.ClienteRepositorio;
import br.com.turbomotors.turbomotors.Tabelas.Cliente;


@Service
public class ImplementarUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepositorio acao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente usuario = acao.findByEmail(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não foi encontrado.");
        }
        return usuario;
    }

}
