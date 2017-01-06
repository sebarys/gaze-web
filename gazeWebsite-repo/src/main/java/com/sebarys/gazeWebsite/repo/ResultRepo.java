package com.sebarys.gazeWebsite.repo;

import java.util.Set;

import com.sebarys.gazeWebsite.model.dbo.Result;
import com.sebarys.gazeWebsite.model.dbo.Stimul;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ResultRepo extends PagingAndSortingRepository<Result, Long> {

	Set<Result> findByStimul(Stimul stimul);

	Page<Result> findByStimul(Stimul stimul, Pageable pageable);

	@Query(value = "SELECT * FROM db.result res JOIN db.result_profile p " +
			"WHERE res.stimul_id = ?1 AND res.id = p.result AND "
			+ "p.profile_key = ?2 AND p.profile = ?3 ORDER BY ?#{#pageable}",
			countQuery = "SELECT count(*) FROM db.result res JOIN db.result_profile p " +
					"WHERE res.stimul_id = ?1 AND res.id = p.result AND "
					+ "p.profile_key = ?2 AND p.profile = ?3",
			nativeQuery = true)
	Page<Result> findByProfileKey(final Long stimulId, final String key, final String value, Pageable pageable);
}
