package com.sebarys.gazeWebsite.repo;

import com.sebarys.gazeWebsite.model.dbo.Stimul;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StimulRepo extends PagingAndSortingRepository<Stimul, Long> {
	Page<Stimul> findByNameLike(String name, Pageable pageable);
}
