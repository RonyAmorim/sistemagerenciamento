package br.com.sistemagerenciamento.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "UserTeam")
@Data
public class UserTeam {
    @EmbeddedId
    private UserTeamId id;

    @ManyToOne
    @MapsId("userId") // Mapeia para o campo userId da chave composta
    private User user;

    @ManyToOne
    @MapsId("teamId") // Mapeia para o campo teamId da chave composta
    private Team team;
}

// Classe para representar a chave primária composta
@Embeddable
@Data
class UserTeamId implements Serializable {
    private Long userId;
    private Long teamId;
}
