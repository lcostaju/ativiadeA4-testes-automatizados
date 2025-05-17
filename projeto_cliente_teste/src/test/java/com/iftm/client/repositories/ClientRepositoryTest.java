package com.iftm.client.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.iftm.client.entities.Client;
import com.iftm.client.services.exceptions.ResourceNotFoundException;

@DataJpaTest
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository repository;

    /**
     * Testa a busca de um cliente por nome existente no repositório.
     * 
     * <p>
     * Este teste verifica se o método {@code findByNameIgnoreCase} retorna
     * corretamente
     * um {@code Optional<Client>} contendo o cliente cujo nome corresponde ao nome
     * fornecido,
     * ignorando diferenças de caixa. O teste também valida se o nome e o CPF do
     * cliente
     * retornado correspondem aos valores esperados.
     * </p>
     *
     * <ul>
     * <li><b>Arrange:</b> Define o nome existente, nome esperado e CPF
     * esperado.</li>
     * <li><b>Act:</b> Executa a busca pelo nome no repositório.</li>
     * <li><b>Assert:</b> Verifica se o cliente foi encontrado e se os dados estão
     * corretos.</li>
     * </ul>
     */
    @Test
    void testeBuscaUmClientePorNomeExistente() {
        // assign
        String nomeExistente = "Clarice Lispector";
        String nomeEsperado = "Clarice Lispector";
        String cpfEsperado = "10919444522";
        // act
        Optional<Client> clienteObtido = repository.findByNameIgnoreCase(nomeExistente);
        // assert
        assertNotNull(clienteObtido);
        assertEquals(nomeEsperado, clienteObtido.get().getName());
        assertEquals(cpfEsperado, clienteObtido.get().getCpf());
    }

    /**
     * Testa o método de busca de cliente por nome no repositório.
     * 
     * Este teste verifica se a busca por um nome de cliente inexistente retorna um Optional vazio,
     * garantindo que o método {@code findByNameIgnoreCase} não encontra clientes com nomes que não existem no banco de dados.
     */
    @Test
    void testeBuscaUmCLientePorNomeRetornoVazio() {
        // assign
        String nomeNaoExistente = "ASDASDFAFADFADFADSFDFFASFASDFSDF";
        // act
        Optional<Client> clienteObtido = repository.findByNameIgnoreCase(nomeNaoExistente);
        // assert
        assertTrue(clienteObtido.isEmpty());
    }
}
