package hanglog.trip.service;


import hanglog.trip.presentation.dto.response.DayLogGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DayLogService {

    public DayLogGetResponse getDayLogById(final Long id) {
        return new DayLogGetResponse(null, null, null, null);
    }
}
