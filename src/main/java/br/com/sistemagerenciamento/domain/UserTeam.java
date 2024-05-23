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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("teamId") // Mapeia para o campo teamId da chave composta
    @JoinColumn(name = "team_id")
    private Team team;
}

