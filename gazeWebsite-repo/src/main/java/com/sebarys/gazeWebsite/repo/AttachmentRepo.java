package com.sebarys.gazeWebsite.repo;

import com.sebarys.gazeWebsite.model.dbo.Attachment;
import com.sebarys.gazeWebsite.model.dbo.Stimul;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepo extends CrudRepository<Attachment, Long> {
    Iterable<Attachment> findByStimul(Stimul stimul);
}
