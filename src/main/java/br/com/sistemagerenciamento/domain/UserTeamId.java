package br.com.sistemagerenciamento.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class UserTeamId implements Serializable {
    private Long userId;
    private Long teamId;
}
