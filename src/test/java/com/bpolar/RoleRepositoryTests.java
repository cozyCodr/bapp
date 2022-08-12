package com.bpolar;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.bpolar.user.Role;
import com.bpolar.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase( replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
    RoleRepository repo;

	@Test
	public void testCreateRoles() {
//		Role user = new Role("User");
//		Role admin = new Role("Admin");
//
//		repo.save(user);
//		repo.save(admin);
//
//		List<Role> listRoles = repo.findAll();
//		assertThat(listRoles.size()).isEqualTo(2);
	}
}
