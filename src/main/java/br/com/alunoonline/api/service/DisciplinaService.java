package br.com.alunoonline.api.service;

import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    public void criarDisciplina(Disciplina disciplina){
        disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> listarTodasDisciplinas(){
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> buscarDisciplinaPorId(Long id){
        return disciplinaRepository.findById(id);
    }

    public void deleteDisciplinaById(Long id){
        disciplinaRepository.deleteById(id);
    }

    public void atualizarDisciplinaPorId(Long id, Disciplina disciplina) {
        //PRIMEIRO PASSO: VER SER O PROFESSOR EXISTE NO BD
        Optional<Disciplina> disiplinaDoBancoDeDados = buscarDisciplinaPorId(id);

        //E SE NAO EXISTIR ESSE PROFESSOR?
        if (disiplinaDoBancoDeDados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Disciplina não encontrada no Banco de Dados");
        }

        //SE CHEGAR AQUI, SIGNIFICA QUE EXISTE Professor COM ESSE ID
        //VOU ARMAZENA-LO EM UMA VARIAVEL PARA DEPOIS EDITA-LO
        Disciplina disciplinaParaEditar = disiplinaDoBancoDeDados.get();

        //COM ESSE PROFESSOR PARA SER EDITADO ACIMA, FAÇO
        // OS SETS NECESSARIOS PARA ATUALIZAR OS ATRIBITOS DELE
        disciplinaParaEditar.setNome(disciplina.getNome());
        disciplinaParaEditar.setCargaHoraria(disciplina.getCargaHoraria());
        disciplinaParaEditar.setProfessor(disciplina.getProfessor());

        //COM O PROFESSOR TOTALMENTE EDITADO ACIMA
        // EU DEVOLVO ELE ATUALIZADO PARA O BD
        disciplinaRepository.save(disciplinaParaEditar);
    }

}
