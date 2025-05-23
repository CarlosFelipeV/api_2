package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    public void criarProfessor(Professor professor){
        professorRepository.save(professor);
    }

    public List<Professor> listarTodosProfessores(){
        return professorRepository.findAll();
    }

    public Optional<Professor> buscarProfessorPorId(Long id){
        return professorRepository.findById(id);
    }

    public void deleteProfessorById(Long id){
        professorRepository.deleteById(id);
    }

    public void atualizarProfessorPorId(Long id, Professor professor) {
        //PRIMEIRO PASSO: VER SER O PROFESSOR EXISTE NO BD
        Optional<Professor> professorDoBancoDeDados = buscarProfessorPorId(id);

        //E SE NAO EXISTIR ESSE PROFESSOR?
        if (professorDoBancoDeDados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Professor não encontrado no Banco de Dados");
        }

        //SE CHEGAR AQUI, SIGNIFICA QUE EXISTE Professor COM ESSE ID
        //VOU ARMAZENA-LO EM UMA VARIAVEL PARA DEPOIS EDITA-LO
        Professor professorParaEditar = professorDoBancoDeDados.get();

        //COM ESSE PROFESSOR PARA SER EDITADO ACIMA, FAÇO
        // OS SETS NECESSARIOS PARA ATUALIZAR OS ATRIBITOS DELE
        professorParaEditar.setNome(professor.getNome());
        professorParaEditar.setCpf(professor.getCpf());
        professorParaEditar.setEmail(professor.getEmail());

        //COM O PROFESSOR TOTALMENTE EDITADO ACIMA
        // EU DEVOLVO ELE ATUALIZADO PARA O BD
        professorRepository.save(professorParaEditar);
    }
}


