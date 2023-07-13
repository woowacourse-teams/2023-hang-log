package hanglog.trip.service;


import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.presentation.dto.response.DayLogGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DayLogService {

    private final DayLogRepository dayLogRepository;

    public DayLogGetResponse getById(final Long id) {
        final DayLog dayLog = dayLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."));
        return DayLogGetResponse.of(dayLog);
    }
}
