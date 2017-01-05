package com.sebarys.gazeWebsite.repo;

import com.sebarys.gazeWebsite.model.dbo.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Sebastian on 2016-11-06.
 */
public interface UserRepo extends CrudRepository<User, Long> {
    public User findByusername(String name);
}
