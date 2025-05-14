package repository;

import model.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, UUID> {
    List<PessoaModel> findByNomeStartingWithAndIdadeGreaterThan(String nome, Integer idade);
    List<PessoaModel> findByNome(String nome);
}

