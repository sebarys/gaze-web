package com.sebarys.gazeWebsite.repo;

import com.sebarys.gazeWebsite.model.dbo.Result;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Sebastian on 2016-12-18.
 */
public interface ResultRepo extends PagingAndSortingRepository<Result, Long> {
}
