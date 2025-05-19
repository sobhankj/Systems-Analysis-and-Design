package org.example;
import java.util.ArrayList;
import java.util.List;

public class Actor {
    private final String name;
    private final List<Starring> roles;

    public Actor(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Actor name can't be null or empty");
        
        this.name = name;
        this.roles = new ArrayList<>();
    }

    public void addRole(Starring newRole) {
        if (roles.stream().anyMatch(role -> role.overlapsWith(newRole))) {
            throw new IllegalArgumentException("New role overlaps with an existing role");
        }
        roles.add(newRole);
    }

    public List<Starring> getRoles() {
        return new ArrayList<>(roles);
    }

    public int totalDaysInMovie(String movieName) {
        return roles.stream().filter(role -> role.getMovieName().equals(movieName)).mapToInt(Starring::durationInDays).sum();
    }

    public int totalDaysActing() {
        return roles.stream().mapToInt(Starring::durationInDays).sum();
    }

    @Override
    public String toString() {
        return "Actor: " + name + "\nRoles: " + roles;
    }

    public String getName() {
        return name;
    }
}
