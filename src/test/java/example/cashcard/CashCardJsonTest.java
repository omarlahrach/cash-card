package example.cashcard;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class CashCardJsonTest {
    @Autowired
    private JacksonTester<CashCard> json;
    @Autowired
    private JacksonTester<CashCard[]> jsonList;
    private CashCard cashCard;
    private CashCard[] cashCards;

    @BeforeEach
    void setUp() {
        cashCard = new CashCard(99L, 123.45);
        cashCards = Arrays.array(
                new CashCard(99L, 123.45),
                new CashCard(100L, 1.00),
                new CashCard(101L, 150.00)
        );
    }

    @Test
    void cashCardListDeserializationTest() throws IOException {
        // Given
        String cashCardListString = """
                [
                  {"id": 99, "amount": 123.45 },
                  {"id": 100, "amount": 1.00 },
                  {"id": 101, "amount": 150.00 }
                ]
                """;
        // Then
        assertThat(jsonList.parse(cashCardListString).getObject()).isEqualTo(cashCards);
    }

    @Test
    void cashCardListSerializationTest() throws IOException {
        // When
        String result = "list.json";
        // Then
        assertThat(jsonList.write(cashCards)).isStrictlyEqualToJson(result);
    }

    @Test
    void cashCardDeserializationTest() throws IOException {
        // Given
        String cashCardString = """
                {
                    "id": 99,
                    "amount": 123.45
                }
                """;
        // Then
        assertThat(json.parse(cashCardString).getObject()).isEqualTo(cashCard);
    }

    @Test
    void cashCardSerializationTest() throws IOException {
        // When
        String result = "single.json";
        // Then
        assertThat(json.write(cashCard)).isStrictlyEqualToJson(result);
    }
}
