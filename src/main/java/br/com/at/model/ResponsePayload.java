package br.com.at.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponsePayload {
    private String mensagem;
    private LocalDateTime dataHora;
    public ResponsePayload(String mensagem) {
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
    }
}
