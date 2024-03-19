package com.casetest.account.adapter.port.out.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.casetest.account.adapter.port.out.persistence.entity.ActivityEntity;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    @Query("""
        select a from ActivityEntity a 
        where a.ownerAccountId = :ownerAccountId 
        and a.whenHappened >= :since
        """)
	List<ActivityEntity> findByOwnerSince(
                @Param("ownerAccountId") long ownerAccountId,
                @Param("since") LocalDateTime since
        );

	@Query("""
        select sum(a.value) from ActivityEntity a
        where a.targetAccountId = :accountId
        and a.ownerAccountId = :accountId
        and a.whenHappened < :until
        """)
	Optional<Long> getDebitBalanceUntil(
                @Param("accountId") long accountId,
                @Param("until") LocalDateTime until
        );

	@Query("""
        select sum(a.value) from ActivityEntity a
        where a.sourceAccountId = :accountId
        and a.ownerAccountId = :accountId
        and a.whenHappened < :until
        """)
	Optional<Long> getCreditalBalanceUntil(
                @Param("accountId") long accountId,
                @Param("until") LocalDateTime until
        );

}
