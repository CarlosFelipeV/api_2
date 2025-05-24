package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Disciplina;
import br.com.alunoonline.api.service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    DisciplinaService disciplinaService;

    @Operation(
            summary = "Criar uma nova disciplina",
            description = "Cria uma nova disciplina no sistema com os dados fornecidos no corpo da requisição"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarDisciplina(@RequestBody Disciplina disciplina) {
        disciplinaService.criarDisciplina(disciplina);
    }

    @Operation(
            summary = "Listar todas as disciplinas",
            description = "Retorna uma lista com todas as disciplinas cadastradas no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de disciplinas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Disciplina> listarTodasDisciplinas(){
        return disciplinaService.listarTodasDisciplinas();
    }

    @Operation(
            summary = "Buscar disciplina por ID",
            description = "Retorna os dados de uma disciplina com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disciplina encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Disciplina> buscarDisciplinaPorId(@PathVariable Long id){
        return disciplinaService.buscarDisciplinaPorId(id);
    }

    @Operation(
            summary = "Deletar disciplina por ID",
            description = "Remove uma disciplina do sistema com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disciplina excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDisciplinaById(@PathVariable Long id){
        disciplinaService.deleteDisciplinaById(id);
    }

    @Operation(
            summary = "Atualizar disciplina por ID",
            description = "Atualiza os dados de uma disciplina existente com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disciplina atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Disciplina não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarDisciplinaPorId(@PathVariable Long id, @RequestBody Disciplina disciplina){
        disciplinaService.atualizarDisciplinaPorId(id, disciplina);
    }

    @Operation(
            summary = "Listar disciplinas de um professor",
            description = "Retorna uma lista de disciplinas associadas ao professor com o ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de disciplinas retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/professor/{professorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Disciplina> listarDisciplinasDoProf(@PathVariable Long professorId) {
        return disciplinaService.listarDiscipinasDoProf(professorId);
    }

}

