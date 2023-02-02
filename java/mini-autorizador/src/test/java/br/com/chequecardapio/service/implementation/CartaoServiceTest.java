package br.com.chequecardapio.service.implementation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.chequecardapio.chain.EncontrarChain;
import br.com.chequecardapio.chain.EncontrarPeloNumero;
import br.com.chequecardapio.chain.SemSaldoChain;
import br.com.chequecardapio.chain.SenhaInvalidaChain;
import br.com.chequecardapio.chain.TransacaoChain;
import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.exceptions.CartaoJaExisteException;
import br.com.chequecardapio.exceptions.CartaoNotFoundException;
import br.com.chequecardapio.exceptions.SaldoInsuficienteException;
import br.com.chequecardapio.exceptions.SenhaInvalidaexception;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.status.Status;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class CartaoServiceTest {

    private CartaoServiceImpl cartaoServ;

    @Mock
    private CartoesRepository repository;

    private final List<Cartao> banco_de_dados = new ArrayList<>();

    public CartaoServiceTest() {
    }

    @BeforeAll
    public void init() {
        this.cartaoServ = new CartaoServiceImpl(repository,
                new SenhaInvalidaChain(),
                new SemSaldoChain(),
                new EncontrarChain(repository),
                new TransacaoChain(repository),
                new EncontrarPeloNumero(repository));

        /*
         * Retorna a lista inteira
         */
        when(repository.findAll()).thenReturn(banco_de_dados);

        /*
         * Simula a inserção num banco de dados (no caso, inserre na lista)
         */
        when(repository.insert(any(Cartao.class))).then(new Answer<Cartao>() {
            @Override
            public Cartao answer(InvocationOnMock invocation) throws Throwable {
                Cartao cartao = invocation.getArgument(0);
                if (banco_de_dados.indexOf(cartao) > -1) {
                    throw new CartaoJaExisteException(cartao);
                }
                banco_de_dados.add(cartao);
                return cartao;
            }
        });

        /*
         * Simula a busca de um cartão na lista
         */
        when(repository.findById(any())).then(new Answer<Optional<Cartao>>() {

            @Override
            public Optional<Cartao> answer(InvocationOnMock invocation) throws Throwable {
                String id = invocation.getArgument(0);
                Cartao result = null;
                for (Cartao c : banco_de_dados) {
                    if (id.equals(c.getNumero())) {
                        result = c;
                        break;
                    }
                }
                return Optional.ofNullable(result);
            }

        });

        /*
         * Salva o cartão na lista que representa o banco de dados
         */
        when(repository.save(any(Cartao.class))).then(new Answer<Cartao>() {
            @Override
            public Cartao answer(InvocationOnMock invocation) throws Throwable {
                Cartao cartao = invocation.getArgument(0);
                Cartao result = null;
                for (Cartao c : banco_de_dados) {
                    if (cartao.equals(c)) {
                        result = c;
                        break;
                    }
                }
                if (result != null) {
                    result.setNome(cartao.getNome());
                    result.setNumero(cartao.getNumero());
                    result.setSenha(cartao.getSenha());
                    result.setValor(cartao.getValor());
                } else {
                    banco_de_dados.add(cartao);
                }

                return cartao;
            }
        });

    }

    /*
     * Aqui eu preparo o "banco de dados" sempre com os mesmos valores para cada teste
     */
    @BeforeEach
    public void prepararBanco(){
        banco_de_dados.clear();
        /*
         * Essa lista reporesenta o banco de dados
         */
        banco_de_dados.add(new Cartao("Teste1", "123", "senha1", BigDecimal.TEN));
        banco_de_dados.add(new Cartao("Teste2", "1234", "senha2", BigDecimal.ONE));
        banco_de_dados.add(new Cartao("Teste3", "12345", "senha3", BigDecimal.ZERO));

    }

    @Test
    public void testListAll() {
        List<Cartao> cartoes = this.cartaoServ.cartoes();
        assertNotEquals(cartoes.size(), 0, "não retornou cartões");
        assertEquals(cartoes.size(), 3, "não retornou cartões");
    }

    @Test
    public void testAddOne() throws CartaoJaExisteException {
        Cartao result = this.cartaoServ.add_cartao(new Cartao("Teste4", "123456", "senha4", new BigDecimal("10.50")));
        assertEquals(result.getNumero(), "123456", "não retornou cartão esperado");
        List<Cartao> cartoes = this.cartaoServ.cartoes();
        assertEquals(cartoes.size(), 4, "não houve inseção do cartão");
    }

    @Test
    public void testHasOne() throws CartaoJaExisteException {
        Cartao result = this.cartaoServ.add_cartao(new Cartao("Teste4", "123456", "senha4", new BigDecimal("10.50")));
        assertEquals(result.getNumero(), "123456", "não retornou cartão esperado");
        assertThrows(CartaoJaExisteException.class, () -> {
            this.cartaoServ.add_cartao(new Cartao("Teste4", "123456", "senha4", new BigDecimal("10.50")));
        });
    }

    @Test
    public void testSaldo() throws CartaoNotFoundException {
        assertThrows(CartaoNotFoundException.class, () -> {
            this.cartaoServ.saldo("9999999");
        });
        List<BigDecimal> result = new ArrayList<>();
        assertDoesNotThrow(() -> {
            result.add(this.cartaoServ.saldo("123"));
        });

        assertTrue(result.size() == 1, "não retornou saldo");
        assertTrue(result.get(0).equals(BigDecimal.TEN), "não retornou saldo");
    }

    @Test
    public void testTransacao() throws CartaoNotFoundException, SaldoInsuficienteException, SenhaInvalidaexception {
        Cartao cartao = new Cartao("Teste1", "123", "senha1", new BigDecimal("-11"));
        Status status = this.cartaoServ.transacao(cartao);
        assertTrue(status == Status.TRANSACAO_EFETUADA, "status deiferente de TRANSACAO_EFETUADA");
    }

    @Test
    public void testTransacaoSaldoInsuficiente() throws CartaoNotFoundException, SaldoInsuficienteException, SenhaInvalidaexception {
        Cartao cartao = new Cartao("Teste1", "123", "senha1", new BigDecimal("-11"));
        Status status = this.cartaoServ.transacao(cartao);
        assertTrue(status == Status.TRANSACAO_EFETUADA, "status deiferente de TRANSACAO_EFETUADA");

        assertThrows(SaldoInsuficienteException.class, () -> {
            Cartao cartaosemSaldo = new Cartao("Teste1", "123", "senha1", new BigDecimal("-1"));
            this.cartaoServ.transacao(cartaosemSaldo);
        });

        assertThrows(SenhaInvalidaexception.class, () -> {
            Cartao cartaosemSaldo = new Cartao("Teste1", "123", "99999", new BigDecimal("-1"));
            this.cartaoServ.transacao(cartaosemSaldo);
        });

    }

}