package br.com.sistemagerenciamento.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserTeamId implements Serializable {
    private Long userId;
    private Long teamId;

    // Construtores, getters, setters, equals e hashCode
    public UserTeamId() {}

    public UserTeamId(Long userId, Long teamId) {
        this.userId = userId;
        this.teamId = teamId;
    }

    // Getters e Setters
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTeamId that = (UserTeamId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, teamId);
    }

}
