package com.sylviavitoria.crud_pessoas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sylviavitoria.crud_pessoas.dto.EnderecoDTO;
import com.sylviavitoria.crud_pessoas.dto.PessoaDTO;
import com.sylviavitoria.crud_pessoas.model.Endereco;
import com.sylviavitoria.crud_pessoas.model.Pessoa;
import com.sylviavitoria.crud_pessoas.repository.PessoaRepository;
import com.sylviavitoria.crud_pessoas.service.PessoaService;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    private Pessoa pessoa;
    private PessoaDTO pessoaDTO;
    private List<Pessoa> listaPessoas;
    private List<PessoaDTO> listaPessoasDTO;
    private Endereco endereco;
    private EnderecoDTO enderecoDTO;

    @BeforeEach
    public void configurar() {
    
        pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("João Silva");
        pessoa.setCpf("12345678900");
        pessoa.setDataNascimento("01/01/1990");
        
        endereco = new Endereco();
        endereco.setId(1L);
        endereco.setRua("Rua Teste");
        endereco.setNumero(123);
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");
        endereco.setEstado("SP");
        endereco.setCep("01234-567");
        pessoa.addEndereco(endereco);

        enderecoDTO = new EnderecoDTO(endereco);

        pessoaDTO = new PessoaDTO();
        pessoaDTO.setId(1L);
        pessoaDTO.setNome("João Silva");
        pessoaDTO.setCpf("12345678900");
        pessoaDTO.setDataNascimento(LocalDate.of(1990, 1, 1));
        pessoaDTO.setIdade(33); 
        pessoaDTO.setEnderecos(Arrays.asList(enderecoDTO));

        listaPessoas = Arrays.asList(pessoa);
    }

    @Test
    public void testarListarTodas() {
        when(pessoaRepository.findAll()).thenReturn(listaPessoas);

        List<PessoaDTO> resultado = pessoaService.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNome());
        assertEquals("12345678900", resultado.get(0).getCpf());

        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    public void testarBuscarPorId_IdExistente() {
        when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoa));

        PessoaDTO resultado = pessoaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("12345678900", resultado.getCpf());

        verify(pessoaRepository, times(1)).findById(1L);
    }

    @Test
    public void testarBuscarPorId_IdInexistente() {
        when(pessoaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.buscarPorId(99L);
        });

        verify(pessoaRepository, times(1)).findById(99L);
    }

    @Test
    public void testarSalvar_NovaPessoa() {
        PessoaDTO novaPessoaDTO = new PessoaDTO();
        novaPessoaDTO.setNome("Maria Oliveira");
        novaPessoaDTO.setCpf("98765432100");
        novaPessoaDTO.setDataNascimento(LocalDate.of(1995, 5, 15));
        
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setId(2L);
        novaPessoa.setNome("Maria Oliveira");
        novaPessoa.setCpf("98765432100");
        novaPessoa.setDataNascimento("15/05/1995");
        
        when(pessoaRepository.existsByCpf("98765432100")).thenReturn(false);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(novaPessoa);

        PessoaDTO resultado = pessoaService.salvar(novaPessoaDTO);

        assertNotNull(resultado);
        assertEquals("Maria Oliveira", resultado.getNome());
        assertEquals("98765432100", resultado.getCpf());
        
        verify(pessoaRepository, times(1)).existsByCpf("98765432100");
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    public void testarSalvar_CPFExistente() {
        PessoaDTO novaPessoaDTO = new PessoaDTO();
        novaPessoaDTO.setNome("Duplicado");
        novaPessoaDTO.setCpf("12345678900");
        
        when(pessoaRepository.existsByCpf("12345678900")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.salvar(novaPessoaDTO);
        });
        
        verify(pessoaRepository, times(1)).existsByCpf("12345678900");
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    public void testarAtualizar_IdExistente() {
        Long id = 1L;
        PessoaDTO atualizacaoDTO = new PessoaDTO();
        atualizacaoDTO.setNome("João Silva Atualizado");
        atualizacaoDTO.setCpf("12345678900"); 
        atualizacaoDTO.setDataNascimento(LocalDate.of(1990, 1, 1));
    
        when(pessoaRepository.existsById(id)).thenReturn(true);
        when(pessoaRepository.existsByCpf("12345678900")).thenReturn(true);
        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(any(Pessoa.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PessoaDTO resultado = pessoaService.atualizar(id, atualizacaoDTO);

        assertNotNull(resultado);
        assertEquals("João Silva Atualizado", resultado.getNome());
        assertEquals("12345678900", resultado.getCpf());
    
        verify(pessoaRepository, times(1)).existsById(id);
        verify(pessoaRepository, times(1)).existsByCpf("12345678900");
        verify(pessoaRepository, times(2)).findById(id); 
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }

    @Test
    public void testarAtualizar_IdInexistente() {
        Long id = 99L;
        PessoaDTO atualizacaoDTO = new PessoaDTO();
        atualizacaoDTO.setNome("Pessoa Inexistente");
        atualizacaoDTO.setCpf("99999999999");
        
        when(pessoaRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.atualizar(id, atualizacaoDTO);
        });
        
        verify(pessoaRepository, times(1)).existsById(id);
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    public void testarAtualizar_CPFDuplicado() {
        Long id = 1L;
        PessoaDTO atualizacaoDTO = new PessoaDTO();
        atualizacaoDTO.setNome("João Silva");
        atualizacaoDTO.setCpf("98765432100"); 
        
        Pessoa outraPessoa = new Pessoa();
        outraPessoa.setId(2L);
        outraPessoa.setCpf("98765432100");
        
        when(pessoaRepository.existsById(id)).thenReturn(true);
        when(pessoaRepository.existsByCpf("98765432100")).thenReturn(true);
        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));
        
        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.atualizar(id, atualizacaoDTO);
        });

        verify(pessoaRepository, times(1)).existsById(id);
        verify(pessoaRepository, times(1)).existsByCpf("98765432100");
        verify(pessoaRepository, times(1)).findById(id);
        verify(pessoaRepository, never()).save(any(Pessoa.class));
    }

    @Test
    public void testarExcluir_IdExistente() {
        Long id = 1L;
        when(pessoaRepository.existsById(id)).thenReturn(true);
        doNothing().when(pessoaRepository).deleteById(id);

        pessoaService.excluir(id);

        verify(pessoaRepository, times(1)).existsById(id);
        verify(pessoaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testarExcluir_IdInexistente() {
        Long id = 99L;
        when(pessoaRepository.existsById(id)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            pessoaService.excluir(id);
        });
        
        verify(pessoaRepository, times(1)).existsById(id);
        verify(pessoaRepository, never()).deleteById(anyLong());
    }

    @Test
    public void testarSalvarComEnderecos() {
        PessoaDTO novaPessoaDTO = new PessoaDTO();
        novaPessoaDTO.setNome("Carlos Souza");
        novaPessoaDTO.setCpf("11122233344");
        novaPessoaDTO.setDataNascimento(LocalDate.of(1985, 3, 10));
        
        EnderecoDTO novoEnderecoDTO = new EnderecoDTO();
        novoEnderecoDTO.setRua("Av Principal");
        novoEnderecoDTO.setNumero(456);
        novoEnderecoDTO.setBairro("Jardim");
        novoEnderecoDTO.setCidade("Rio de Janeiro");
        novoEnderecoDTO.setEstado("RJ");
        novoEnderecoDTO.setCep("22222-333");
        
        novaPessoaDTO.setEnderecos(Arrays.asList(novoEnderecoDTO));
        
        Pessoa pessoaSalva = new Pessoa();
        pessoaSalva.setId(3L);
        pessoaSalva.setNome("Carlos Souza");
        pessoaSalva.setCpf("11122233344");
        pessoaSalva.setDataNascimento("10/03/1985");
        
        Endereco enderecoSalvo = new Endereco();
        enderecoSalvo.setId(2L);
        enderecoSalvo.setRua("Av Principal");
        enderecoSalvo.setNumero(456);
        enderecoSalvo.setBairro("Jardim");
        enderecoSalvo.setCidade("Rio de Janeiro");
        enderecoSalvo.setEstado("RJ");
        enderecoSalvo.setCep("22222-333");
        pessoaSalva.addEndereco(enderecoSalvo);
        
        when(pessoaRepository.existsByCpf("11122233344")).thenReturn(false);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaSalva);

        PessoaDTO resultado = pessoaService.salvar(novaPessoaDTO);

        assertNotNull(resultado);
        assertEquals("Carlos Souza", resultado.getNome());
        assertEquals("11122233344", resultado.getCpf());
        assertNotNull(resultado.getEnderecos());
        assertEquals(1, resultado.getEnderecos().size());
        assertEquals("Av Principal", resultado.getEnderecos().get(0).getRua());
        assertEquals("Rio de Janeiro", resultado.getEnderecos().get(0).getCidade());
        
        verify(pessoaRepository, times(1)).existsByCpf("11122233344");
        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
    }
}