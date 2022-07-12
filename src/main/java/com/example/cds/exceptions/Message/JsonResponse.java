package com.example.cds.exceptions.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект для возврата ошибок в json
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JsonResponse {
    private String message;
}