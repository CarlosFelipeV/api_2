package br.com.alunoonline.api.controller;

import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @Operation(
            summary = "Criar um novo professor",
            description = "Cria um novo professor no sistema com os dados fornecidos no corpo da requisição"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarProfessor(@RequestBody Professor professor) {
        professorService.criarProfessor(professor);
    }

    @Operation(
            summary = "Listar todos os professores",
            description = "Retorna uma lista com todos os professores cadastrados no sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de professores retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> listarTodosProfessores(){
        return professorService.listarTodosProfessores();
    }

    @Operation(
            summary = "Buscar professor por ID",
            description = "Retorna os dados de um professor com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Professor> buscarProfessorPorId(@PathVariable Long id){
        return professorService.buscarProfessorPorId(id);
    }

    @Operation(
            summary = "Deletar professor por ID",
            description = "Remove um professor do sistema com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfessorById(@PathVariable Long id){
        professorService.deleteProfessorById(id);
    }

    @Operation(
            summary = "Atualizar professor por ID",
            description = "Atualiza os dados de um professor existente com base no ID fornecido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Professor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarProfessorPorId(@PathVariable Long id, @RequestBody Professor professor){
        professorService.atualizarProfessorPorId(id, professor);
    }
}

