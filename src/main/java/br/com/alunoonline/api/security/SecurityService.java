package br.com.alunoonline.api.security;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import br.com.alunoonline.api.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    @Autowired
    private AlunoRepository alunoRepository;

    public boolean alunoPodeConsultarHistorico(Authentication authentication, Long alunoId) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        Aluno aluno = alunoRepository.findById(alunoId).orElse(null);
        if (aluno == null) {
            return false;
        }

        return usuarioLogado.getLogin().equals(aluno.getEmail());
    }
}
