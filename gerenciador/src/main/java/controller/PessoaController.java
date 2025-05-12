package controller;

import dto.PessoaDto;
import exception.PessoaNaoEncontradaException;
import model.PessoaModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.PessoaService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping("/pessoas")
    public ResponseEntity<PessoaModel> createPessoa(@RequestBody PessoaDto pessoaDto){
        PessoaModel savedPessoa = pessoaService.createPessoa(pessoaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPessoa);
    }

    @GetMapping("/pessoas/rafael")
    public ResponseEntity<List<PessoaModel>> getRafaelsMaiorDeIdade() {
        List<PessoaModel> pessoas = pessoaService.buscarRafaelsMaioresDeIdade();
        return ResponseEntity.ok(pessoas);
    }
    
    @GetMapping("/pessoas/buscar")
    public ResponseEntity<List<PessoaModel>> buscarPorNomeEIdade(
            @RequestParam String nome,
            @RequestParam Integer idade
    ) {
        List<PessoaModel> pessoas = pessoaService.buscarPorNomeEIdade(nome, idade);
        return ResponseEntity.ok(pessoas);
    }

    @ExceptionHandler(PessoaNaoEncontradaException.class)
    public ResponseEntity<String> handlePessoaNaoEncontradaException(PessoaNaoEncontradaException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<PessoaModel>> getAllPessoas(){
        List<PessoaModel> allPessoa = pessoaService.getAllPessoas();
        return ResponseEntity.status(HttpStatus.OK).body(allPessoa);
    }

    @GetMapping("/pessoas/{id}")
    public ResponseEntity<Object> getPessoaModelById(@PathVariable UUID id){
        Optional<PessoaModel> foundPessoaModel = pessoaService.getPessoaModelById(id);

        if(foundPessoaModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada!!!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(foundPessoaModel.get());
    }

    @DeleteMapping("/pessoas/{id}")
    public ResponseEntity<String> deletePessoaModelById(@PathVariable UUID id){
        Optional<PessoaModel> foundPessoaModel = pessoaService.getPessoaModelById(id);
        if(foundPessoaModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada!!!");
        }
        pessoaService.deletePessoaModelById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada com sucesso!!!");
    }

    @PutMapping("/pessoas/{id}")
    public ResponseEntity<Object> updatePessoaModelById(@RequestBody PessoaDto pessoaDto, @PathVariable UUID id){
        try {
            PessoaModel updatedPessoa = pessoaService.updatePessoaModelById(id, pessoaDto);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPessoa);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
