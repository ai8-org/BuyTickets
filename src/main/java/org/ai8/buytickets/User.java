package org.ai8.buytickets;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User model.
 * @author lishiyu
 */
@Entity
public class User {
    @Id
    public String mail;

    public String nickname;
    public String password;
    public String tickets;
    public String role;
    public String session;
}
