package com.bulitski.app.repository;


import com.bulitski.app.model.Status;
import com.bulitski.app.model.Role;
import com.bulitski.app.model.UserAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    Optional<UserAccount> findByUserName(String name);

    UserAccount save(UserAccount userAccount);

    List<UserAccount> findAll(Pageable pageable);

    @Modifying
    @Query("update UserAccount u set u.status=:status where u.id=:id")
    void updateStatus(Status status, Long id);

    @Query("select u from UserAccount u where upper(u.userName) like %:userName%")
    List<UserAccount> findAllMatch(@Param("userName") String userName, Pageable pageable);

    @Query("select u from UserAccount u where upper(u.userName) like %:userName% and u.role=:role")
    List<UserAccount> findAllMatchByRole(@Param("userName") String userName, @Param("role") Role role, Pageable pageable);

    List<UserAccount> findByRole(Role role, Pageable pageable);

    @Modifying
    @Query("update UserAccount u set u.role=:role where u.id=:id")
    void updateRole(Role role,Long id);
}
