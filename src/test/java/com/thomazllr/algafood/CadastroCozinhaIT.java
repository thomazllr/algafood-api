package com.thomazllr.algafood;

import com.thomazllr.algafood.domain.entity.Cozinha;
import com.thomazllr.algafood.domain.repository.CozinhaRepository;
import com.thomazllr.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static com.thomazllr.algafood.util.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CadastroCozinhaIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner cleaner;

    @Autowired
    private CozinhaRepository repository;

    private Cozinha cozinhaAmerica;

    @BeforeEach
    void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        cleaner.clearTables();

        prepararBancoDeDados();

    }

    @Test
    @DisplayName("Deve retornar 200 quando consultar cozinhas")
    void deveRetornar200_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Deve conter 2 cozinhas quando consultar cozinhas")
    void deveConter4Cozinhas_QuandoConsultarCozinhas() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(2));
    }


    @Test
    @DisplayName("Deve retornar 201 quando cadastrar cozinha")
    void deveRetornar201_QuandoCadastrarCozinha() {
        String jsonCorretoCozinhaChinesa = getContentFromResource("/json/cadastro-cozinha-unica.json");
        given()
                .body(jsonCorretoCozinhaChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("nome", equalTo("Chinesa"));
    }

    @Test
    @DisplayName("Deve retornar resposta e dados corretos quando consultar cozinha existente")
    void deveRetornarRespostaEDadosCorretos_QuandoConsultarCozinhaExistente() {
        given()
                .pathParam("id", 2)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("nome", equalTo(cozinhaAmerica.getNome()));
    }

    private void prepararBancoDeDados() {

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Brasileira");
        repository.save(cozinha1);

        cozinhaAmerica = new Cozinha();

        cozinhaAmerica.setNome("Americana");
        repository.save(cozinhaAmerica);


    }
}
