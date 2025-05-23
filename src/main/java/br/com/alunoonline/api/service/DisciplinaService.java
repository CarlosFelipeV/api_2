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
        //PRIMEIRO PASSO: VER SER A DISCIPLINA EXISTE NO BD
        Optional<Disciplina> disiplinaDoBancoDeDados = buscarDisciplinaPorId(id);

        //E SE NAO EXISTIR ESSA DISCIPLINA?
        if (disiplinaDoBancoDeDados.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Disciplina não encontrada no Banco de Dados");
        }

        //SE CHEGAR AQUI, SIGNIFICA QUE EXISTE DISCIPLINA COM ESSE ID
        //VOU ARMAZENA-LO EM UMA VARIAVEL PARA DEPOIS EDITA-LO
        Disciplina disciplinaParaEditar = disiplinaDoBancoDeDados.get();

        //COM ESSA DISCIPLINA PARA SER EDITADA ACIMA, FAÇO
        // OS SETS NECESSARIOS PARA ATUALIZAR OS ATRIBITOS DELA
        disciplinaParaEditar.setNome(disciplina.getNome());
        disciplinaParaEditar.setCargaHoraria(disciplina.getCargaHoraria());
        disciplinaParaEditar.setProfessor(disciplina.getProfessor());

        //COM A DISCIPLINA TOTALMENTE EDITADA ACIMA
        // EU DEVOLVO ELA ATUALIZADA PARA O BD
        disciplinaRepository.save(disciplinaParaEditar);
    }

    public List<Disciplina> listarDiscipinasDoProf(Long professorId) {
        return disciplinaRepository.findByProfessorId(professorId);
    }

}
