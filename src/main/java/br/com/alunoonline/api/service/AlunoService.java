package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository alunoRepository;

    public void criarAluno(Aluno aluno) {
        alunoRepository.save(aluno);
    }

    public List<Aluno> listarTodosAlunos(){
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id){
        return alunoRepository.findById(id);
    }

    public void deleteAlunoById(Long id){
        alunoRepository.deleteById(id);
    }

    public void atualizarAlunoPorId(Long id, Aluno aluno) {
        //PRIMEIRO PASSO: VER SER O ALUNO EXISTE NO BD
        Optional<Aluno> alunoDoBancoDeDados = buscarAlunoPorId(id);

        //E SE NAO EXISTIR ESSE ALUNO?
        if (alunoDoBancoDeDados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Aluno não encontrado no Banco de Dados");
        }

        //SE CHEGAR AQUI, SIGNIFICA QUE EXISTE ALUNO COM ESSE ID
        //VOU ARMAZENA-LO EM UMA VARIAVEL PARA DEPOIS EDITA-LO
        Aluno alunoParaEditar = alunoDoBancoDeDados.get();

        //COM ESSE ALUNO PARA SER EDITADO ACIMA, FAÇO
        // OS SETS NECESSARIOS PARA ATUALIZAR OS ATRIBITOS DELE
        alunoParaEditar.setNome(aluno.getNome());
        alunoParaEditar.setCpf(aluno.getCpf());
        alunoParaEditar.setEmail(aluno.getEmail());

        //COM O ALUNO TOTALMENTE EDITADO ACIMA
        // EU DEVOLVO ELE ATUALIZADO PARA O BD
        alunoRepository.save(alunoParaEditar);
    }
}

