package controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import dto.PessoaDto;
import model.PessoaModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.PessoaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @PostMapping("/pessoas")
    public ResponseEntity<PessoaModel> createPessoa(@RequestBody PessoaDto pessoaDto){
        PessoaModel pessoa = new PessoaModel();
        BeanUtils.copyProperties(pessoaDto, pessoa);

        PessoaModel savedPessoa = pessoaRepository.save(pessoa);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedPessoa);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaModel>> getAllPessoas(){
        List<PessoaModel> allPessoa = pessoaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allPessoa);
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Object> getPessoaModelById(@PathVariable UUID id){
        Optional<PessoaModel> foundPessoaModel = pessoaRepository.findById(id);

        if(foundPessoaModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada!!!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundPessoaModel.get());
    }
    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<String> deletePessoaModelById(@PathVariable UUID id){
        Optional<PessoaModel> foundPessoaModel = pessoaRepository.findById(id);
        if(foundPessoaModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada!!!");
        }
        pessoaRepository.delete(foundPessoaModel.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso!!!");
    }

    @PutMapping("/pessoas/{id}")
    public ResponseEntity<Object> updatePessoaModelById(@RequestBody PessoaDto pessoaDto, @PathVariable UUID id){
        Optional<PessoaModel> foundPessoaModel = pessoaRepository.findById(id);

        if(foundPessoaModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada!!!");
        }
        PessoaModel pessoaModel = foundPessoaModel.get();
        BeanUtils.copyProperties(pessoaDto, pessoaModel);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaRepository.save(pessoaModel));
    }

}
