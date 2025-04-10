package com.sylviavitoria.crud_pessoas.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sylviavitoria.crud_pessoas.dto.PessoaDTO;
import com.sylviavitoria.crud_pessoas.model.Endereco;
import com.sylviavitoria.crud_pessoas.model.Pessoa;
import com.sylviavitoria.crud_pessoas.repository.PessoaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    public PessoaDTO salvar(PessoaDTO pessoaDTO) {
        if (pessoaDTO.getId() != null && pessoaRepository.existsByCpf(pessoaDTO.getCpf())) {
            Pessoa existingPessoa = pessoaRepository.findById(pessoaDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada"));
            if (!existingPessoa.getCpf().equals(pessoaDTO.getCpf())) {
                throw new IllegalArgumentException("CPF já cadastrado para outra pessoa");
            }
        } else if (pessoaDTO.getId() == null && pessoaRepository.existsByCpf(pessoaDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        
        Pessoa pessoa = convertToEntity(pessoaDTO);
        pessoa = pessoaRepository.save(pessoa);
        return new PessoaDTO(pessoa);
    }

    public List<PessoaDTO> listarTodas() {
        return pessoaRepository.findAll().stream()
                .map(pessoa -> new PessoaDTO(pessoa))
                .collect(Collectors.toList());
    }

    public PessoaDTO buscarPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com o ID: " + id));
        return new PessoaDTO(pessoa);
    }
    
    
    private Pessoa convertToEntity(PessoaDTO dto) {
        Pessoa pessoa;
        
        if (dto.getId() != null) {
            pessoa = pessoaRepository.findById(dto.getId())
                .orElse(new Pessoa());
        } else {
            pessoa = new Pessoa();
        }
        
        pessoa.setId(dto.getId());
        pessoa.setNome(dto.getNome());
        pessoa.setCpf(dto.getCpf());
        
        if (dto.getDataNascimento() != null) {
            pessoa.setDataNascimento(dto.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        
        pessoa.getEnderecos().clear();
        
        if (dto.getEnderecos() != null) {
            dto.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = enderecoDTO.toEntity();
                pessoa.addEndereco(endereco);
            });
        }
        
        return pessoa;
    }
}