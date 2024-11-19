package app.Exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException {
    private final String fieldName;
    private final String message;
}
