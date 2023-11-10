package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static christmas.constant.ErrorMessage.DAY_INVALID;
import static christmas.constant.ErrorMessage.DAY_NOT_NUMERIC;
import static org.assertj.core.api.Assertions.assertThatCode;

public class VisitingDayTest {
    @ParameterizedTest
    @DisplayName("생성자를 통한 생성")
    @ValueSource(ints = {1, 28})
    void newInstance(int day) {
        assertThatCode(() -> new VisitingDay(day)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("from 함수를 통한 생성")
    @ValueSource(strings = {"1", "28"})
    void fromInstance(String day) {
        assertThatCode(() -> VisitingDay.from(day)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("입력이 숫자형 문자열이 아닐 경우 예외 발생")
    @ValueSource(strings = {"십이"})
    void validateNumeric(String day) {
        assertThatCode(() -> VisitingDay.from(day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(DAY_NOT_NUMERIC.toString());
    }

    @ParameterizedTest
    @DisplayName("날짜가 이벤트 기간 중아 아닐 경우 예외 발생")
    @ValueSource(ints = {-1, 0, 32})
    void validateCanPeriodDay(int day) {
        assertThatCode(() -> new VisitingDay(day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(DAY_INVALID.toString());
    }
}
