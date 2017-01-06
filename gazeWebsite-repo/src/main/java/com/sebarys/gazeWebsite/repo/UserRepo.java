package com.sebarys.gazeWebsite.repo;

import com.sebarys.gazeWebsite.model.dbo.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
    public User findByusername(String name);
}
