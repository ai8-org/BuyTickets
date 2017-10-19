package org.ai8.buytickets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    public List<User> findBySession(String session);
    public List<User> getPaidticketsByMail(String mail);
}
