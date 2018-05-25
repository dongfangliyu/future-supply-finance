package cn.fintecher.authorization.agent.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;


@Data
public class UserInfo implements Serializable {

    private String firstName;
    private String lastName;
    private Set<String> roles;

    /**
     * Create a new UserInfo
     *
     * @param username The unique username for the user
     * @param name     The name of the user
     */
    public UserInfo(String username, String name) {
        this.firstName = username;
        this.lastName = name;
    }
}
