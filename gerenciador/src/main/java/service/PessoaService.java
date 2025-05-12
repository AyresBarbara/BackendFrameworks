package service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dto.PessoaDto;
import model.PessoaModel;

public interface PessoaService {

    PessoaModel createPessoa(PessoaDto pessoaDto);

    List<PessoaModel> getAllPessoas();

    Optional<PessoaModel> getPessoaModelById(UUID id);

    void deletePessoaModelById(UUID id);
    
    PessoaModel updatePessoaModelById(UUID id, PessoaDto pessoaDto);

    List<PessoaModel> buscarRafaelsMaioresDeIdade();

    List<PessoaModel> buscarPorNomeEIdade(String nome, Integer idade);

}
