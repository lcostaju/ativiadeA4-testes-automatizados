package com.iftm.client.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.iftm.client.entities.Client;

@DataJpaTest
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository repisitory;

    /**
     * Testa a busca de um cliente por nome existente no repositório.
     * 
     * <p>
     * Este teste verifica se o método {@code findByNameIgnoreCase} retorna corretamente
     * um {@code Optional<Client>} contendo o cliente cujo nome corresponde ao nome fornecido,
     * ignorando diferenças de caixa. O teste também valida se o nome e o CPF do cliente
     * retornado correspondem aos valores esperados.
     * </p>
     *
     * <ul>
     *   <li><b>Arrange:</b> Define o nome existente, nome esperado e CPF esperado.</li>
     *   <li><b>Act:</b> Executa a busca pelo nome no repositório.</li>
     *   <li><b>Assert:</b> Verifica se o cliente foi encontrado e se os dados estão corretos.</li>
     * </ul>
     */
    @Test
    void testeBuscaUmClientePorNomeExistente() {
         // assign
        String nomeExistente = "Clarice Lispector";
        String nomeEsperado = "Clarice Lispector";
        String cpfEsperado = "10919444522";
        // act
        Optional<Client> clienteObtido = repisitory.findByNameIgnoreCase(nomeExistente);
        // assert
        assertNotNull(clienteObtido);
        assertEquals(nomeEsperado, clienteObtido.get().getName());
        assertEquals(cpfEsperado, clienteObtido.get().getCpf());
    }
}
