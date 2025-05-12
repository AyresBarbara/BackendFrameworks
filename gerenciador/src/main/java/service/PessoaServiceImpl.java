package service;

import dto.PessoaDto;
import exception.PessoaNaoEncontradaException;
import model.PessoaModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PessoaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaServiceImpl implements PessoaService {

    // Criando o Logger para a classe
    private static final Logger logger = LoggerFactory.getLogger(PessoaServiceImpl.class);

    @Autowired
    private PessoaRepository pessoaRepository;

    @Override
    public PessoaModel createPessoa(PessoaDto pessoaDto) {
        // Log para informar que o processo de criação foi iniciado
        logger.info("Iniciando criação de pessoa com nome: {}", pessoaDto);
        
        PessoaModel pessoa = new PessoaModel();
        BeanUtils.copyProperties(pessoaDto, pessoa);
        PessoaModel savedPessoa = pessoaRepository.save(pessoa);
        
        // Log para confirmar que a criação foi bem-sucedida
        logger.info("Pessoa criada com sucesso: {}", savedPessoa);
        return savedPessoa;
    }

    @Override
    public List<PessoaModel> buscarPorNomeEIdade(String nome, Integer idade) {
        logger.info("Buscando pessoas com nome iniciando com '{}' e idade maior que {}", nome, idade);
        
        List<PessoaModel> pessoas = pessoaRepository.findByNomeStartingWithAndIdadeGreaterThan(nome, idade);

        if (pessoas.isEmpty()) {
            logger.warn("Nenhuma pessoa encontrada com nome '{}' e idade maior que {}", nome, idade);
            throw new PessoaNaoEncontradaException("Nenhuma pessoa encontrada com o nome " + nome + " e idade " + idade);
        }

        logger.info("Pessoas encontradas: {}", pessoas.size());
        return pessoas;
    }

    @Override
    public List<PessoaModel> getAllPessoas() {
        logger.info("Buscando todas as pessoas no sistema.");
        List<PessoaModel> pessoas = pessoaRepository.findAll();
        logger.info("Total de pessoas encontradas: {}", pessoas.size());
        return pessoas;
    }

    @Override
    public Optional<PessoaModel> getPessoaModelById(UUID id) {
        logger.info("Buscando pessoa com ID: {}", id);
        Optional<PessoaModel> pessoa = pessoaRepository.findById(id);
        
        if (pessoa.isPresent()) {
            logger.info("Pessoa encontrada: {}", pessoa.get());
        } else {
            logger.warn("Pessoa com ID {} não encontrada", id);
        }
        
        return pessoa;
    }

    @Override
    public void deletePessoaModelById(UUID id) {
        logger.info("Deletando pessoa com ID: {}", id);
        pessoaRepository.deleteById(id);
        logger.info("Pessoa com ID {} deletada com sucesso", id);
    }

    @Override
    public PessoaModel updatePessoaModelById(UUID id, PessoaDto pessoaDto) {
        logger.info("Atualizando pessoa com ID: {}", id);
        
        Optional<PessoaModel> foundPessoaModel = pessoaRepository.findById(id);
        if (foundPessoaModel.isPresent()) {
            PessoaModel pessoaModel = foundPessoaModel.get();
            BeanUtils.copyProperties(pessoaDto, pessoaModel);
            PessoaModel updatedPessoa = pessoaRepository.save(pessoaModel);
            
            logger.info("Pessoa com ID {} atualizada com sucesso: {}", id, updatedPessoa);
            return updatedPessoa;
        }
        
        logger.warn("Pessoa com ID {} não encontrada para atualização", id);
        throw new PessoaNaoEncontradaException("Pessoa não encontrada para atualização!");
    }

    @Override
    public List<PessoaModel> buscarRafaelsMaioresDeIdade() {
        logger.info("Buscando pessoas com nome iniciando com 'Rafael' e idade maior que 18");
        List<PessoaModel> pessoas = pessoaRepository.findByNomeStartingWithAndIdadeGreaterThan("Rafael", 18);
        logger.info("Total de Rafaels maiores de idade encontrados: {}", pessoas.size());
        return pessoas;
    }
}
