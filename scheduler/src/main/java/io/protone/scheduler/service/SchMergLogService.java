package io.protone.scheduler.service;

import io.protone.scheduler.domain.SchSchedule;
import org.springframework.stereotype.Service;

import javax.inject.Inject;


@Service
public class SchMergLogService {

    private static final String LOG_COMMERCIAL_LIBRARY = "loc";

    private static final String LOG_MUSIC_LIBRARY = "lom";

    private static final String LOG_OTHER_LIBRARY = "loo";
    @Inject
    private SchGridService schGridService;


    public SchSchedule mergeLogs() {
        return null;
    }
}
