package com.authkit.servlet;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultTokenExtractorTest {

    @Mock
    private HttpServletRequest reqMock;

    @ParameterizedTest
    @MethodSource("provideForApply")
    public void apply(String input, String result) {

        DefaultTokenExtractor unit = new DefaultTokenExtractor();

        when(reqMock.getHeader("Authorization")).thenReturn(input);

        String got = unit.apply(reqMock);

        assertThat(got).isEqualTo(result);

        verifyNoMoreInteractions(reqMock);
    }

    private static Stream<Arguments> provideForApply() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", null),
                Arguments.of("bearer", null),
                Arguments.of("Bearer", null),
                Arguments.of("bearer ", null),
                Arguments.of("Bearer ", ""),
                Arguments.of("Bearer a", "a"),
                Arguments.of("Bearer abc", "abc"),
                Arguments.of("other abc", null),
                Arguments.of("xxxxxx abc", null)
        );
    }
}