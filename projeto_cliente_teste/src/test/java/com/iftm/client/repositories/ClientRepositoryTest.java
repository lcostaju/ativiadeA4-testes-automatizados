package com.iftm.client.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.iftm.client.entities.Client;

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
     * Este teste verifica se a busca por um nome de cliente inexistente retorna um
     * Optional vazio,
     * garantindo que o método {@code findByNameIgnoreCase} não encontra clientes
     * com nomes que não existem no banco de dados.
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

    /**
     * Testa o método de busca de clientes pelo nome, ignorando maiúsculas e
     * minúsculas.
     * 
     * <p>
     * Este teste verifica se o repositório retorna uma lista não nula e não vazia
     * de clientes
     * cujo nome contém o termo de busca especificado, independentemente do caso das
     * letras.
     * Também garante que pelo menos um dos clientes retornados possui o termo de
     * busca em seu nome.
     * </p>
     */
    @Test
    void testeBuscarClientesComPeloNomeExistentes() {
        // assing
        String termoBusca = "li";
        // act
        List<Client> clientes = repository.findByNameContainingIgnoreCase(termoBusca);
        // assert
        assertNotNull(clientes);
        assertTrue(clientes.size() > 0);
        assertTrue(clientes.stream().anyMatch(c -> c.getName().toLowerCase().contains(termoBusca)));
    }

    /**
     * Testa a busca de clientes por um termo inexistente no nome.
     * 
     * Este teste verifica se a busca por um termo que não está presente em nenhum
     * nome de cliente
     * retorna uma lista vazia, garantindo que o método
     * {@code findByNameContainingIgnoreCase}
     * não encontra clientes quando o termo não existe no banco de dados.
     */
    @Test
    void testeBuscarClientesPorTermoInexistente() {
        // assign
        String termoInexistente = "XYZTERMOQUENAOEXISTE";
        // act
        List<Client> clientes = repository.findByNameContainingIgnoreCase(termoInexistente);
        // assert
        assertNotNull(clientes);
        assertTrue(clientes.isEmpty());
    }

    /**
     * Testa o método de busca de clientes cujo nome contém uma substring, ignorando
     * maiúsculas e minúsculas.
     * 
     * Este teste verifica se a lista de clientes retornada contém ao menos um
     * cliente com o nome contendo a substring fornecida.
     */
    @Test
    void testeBuscaClientesPorNomeContendoSubstring() {
        // assign
        String substring = "clarice"; // deve encontrar "Clarice Lispector"

        // act
        List<Client> resultado = repository.findByNameContainingIgnoreCase(substring);

        // assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertTrue(resultado.stream().anyMatch(c -> c.getName().toLowerCase().contains(substring)));
    }

    /**
     * Testa a busca por substring inexistente no nome dos clientes.
     * 
     * Espera-se que a lista retornada esteja vazia.
     */
    @Test
    void testeBuscaClientesPorNomeContendoSubstringInexistente() {
        // assign
        String substringInexistente = "XyzAbc123";

        // act
        List<Client> resultado = repository.findByNameContainingIgnoreCase(substringInexistente);

        // assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    /**
     * Testa o método que retorna clientes com salário superior a um determinado
     * valor.
     * 
     * Verifica se todos os clientes retornados possuem salário maior que o valor
     * informado.
     */
    @Test
    void testeBuscaClientesComSalarioMaiorQueValor() {
        List<Client> clientes = repository.findByIncomeGreaterThan(5000.0);
        assertNotNull(clientes);
        assertTrue(clientes.stream().allMatch(c -> c.getIncome() > 5000.0));
    }

    /**
     * Testa o método de busca por clientes com salário muito alto, esperando lista
     * vazia.
     */
    @Test
    void testeBuscaClientesComSalarioMaiorQueValorNenhumEncontrado() {
        List<Client> clientes = repository.findByIncomeGreaterThan(999999.0);
        assertTrue(clientes.isEmpty());
    }

    /**
     * Testa o método que retorna clientes com número de filhos inferior ao valor
     * informado.
     * 
     * Verifica se todos os clientes retornados possuem menos filhos que o limite
     * definido.
     */
    @Test
    void testeBuscaClientesComMenosFilhosQueValor() {
        List<Client> clientes = repository.findByChildrenLessThan(3);
        assertNotNull(clientes);
        assertTrue(clientes.stream().allMatch(c -> c.getChildren() < 3));
    }

    /**
     * Testa o método de busca por clientes com menos filhos, com valor mínimo zero.
     * 
     * Espera-se uma lista vazia, assumindo que não há cliente com filhos negativos.
     */
    @Test
    void testeBuscaClientesComMenosFilhosQueValorNenhum() {
        List<Client> clientes = repository.findByChildrenLessThan(0);
        assertTrue(clientes.isEmpty());
    }

    /**
     * Testa o método de busca por clientes cuja data de nascimento está entre duas
     * datas específicas.
     * 
     * Verifica se todos os clientes retornados nasceram dentro do intervalo
     * definido.
     */
    @Test
    void testeBuscaClientesPorDataNascimentoEntre() {
        Instant inicio = Instant.parse("1980-01-01T00:00:00Z");
        Instant fim = Instant.parse("2000-12-31T23:59:59Z");

        List<Client> clientes = repository.findByBirthDateBetween(inicio, fim);
        assertNotNull(clientes);
        assertTrue(clientes.stream()
                .allMatch(c -> !c.getBirthDate().isBefore(inicio) && !c.getBirthDate().isAfter(fim)));
    }

    /**
     * Testa o método de busca por clientes com data de nascimento em intervalo sem
     * resultados.
     * 
     * O intervalo definido não deve conter nenhum cliente.
     */
    @Test
    void testeBuscaClientesPorDataNascimentoEntreSemResultados() {
        Instant inicio = Instant.parse("1800-01-01T00:00:00Z");
        Instant fim = Instant.parse("1800-12-31T23:59:59Z");

        List<Client> clientes = repository.findByBirthDateBetween(inicio, fim);
        assertTrue(clientes.isEmpty());
    }

    /**
     * Testa o método que busca cliente por nome (ignorando maiúsculas/minúsculas) e
     * CPF exatos.
     * 
     * Verifica se o cliente retornado corresponde exatamente ao nome e CPF
     * fornecidos.
     */
    @Test
    void testeBuscaClientePorNomeECpfExistente() {
        String nome = "Clarice Lispector";
        String cpf = "10919444522";

        Optional<Client> cliente = repository.findByNameIgnoreCaseAndCpf(nome, cpf);
        assertTrue(cliente.isPresent());
        assertEquals(nome, cliente.get().getName());
        assertEquals(cpf, cliente.get().getCpf());
    }

    /**
     * Testa o método de busca por nome e CPF com CPF incorreto.
     * 
     * Espera-se que nenhum cliente seja encontrado.
     */
    @Test
    void testeBuscaClientePorNomeECpfIncorreto() {
        String nome = "Clarice Lispector";
        String cpfErrado = "00000000000";

        Optional<Client> cliente = repository.findByNameIgnoreCaseAndCpf(nome, cpfErrado);
        assertTrue(cliente.isEmpty());
    }
}
