package hanglog.trip.service;


import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.presentation.dto.request.DayLogUpdateTitleRequest;
import hanglog.trip.presentation.dto.response.DayLogGetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DayLogService {

    private final DayLogRepository dayLogRepository;

    @Transactional(readOnly = true)
    public DayLogGetResponse getById(final Long id) {
        final DayLog dayLog = dayLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."));
        validateAlreadyDeleted(dayLog);

        return DayLogGetResponse.of(dayLog);
    }

    public void updateTitle(final Long id, final DayLogUpdateTitleRequest request) {
        final DayLog dayLog = dayLogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("요청한 ID에 해당하는 데이로그가 존재하지 않습니다."));
        validateAlreadyDeleted(dayLog);

        final DayLog updatedDayLog = new DayLog(
                dayLog.getId(),
                request.getTitle(),
                dayLog.getOrdinal(),
                dayLog.getTrip(),
                dayLog.getItems()
        );
        dayLogRepository.save(updatedDayLog);
    }


    private void validateAlreadyDeleted(final DayLog dayLog) {
        if (dayLog.isDeleted()) {
            throw new IllegalStateException("이미 삭제된 날짜입니다.");
        }
    }
}
