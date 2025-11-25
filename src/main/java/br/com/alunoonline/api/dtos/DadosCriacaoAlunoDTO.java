package br.com.alunoonline.api.dtos;

public record DadosCriacaoAlunoDTO(
        String nome,
        String cpf,
        String email,
        String senha
) {
}