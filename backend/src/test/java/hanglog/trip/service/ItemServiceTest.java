package hanglog.trip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import hanglog.category.domain.Category;
import hanglog.category.domain.repository.CategoryRepository;
import hanglog.category.fixture.CategoryFixture;
import hanglog.global.exception.BadRequestException;
import hanglog.image.domain.repository.ImageRepository;
import hanglog.trip.domain.DayLog;
import hanglog.trip.domain.Item;
import hanglog.trip.domain.repository.DayLogRepository;
import hanglog.trip.domain.repository.ItemRepository;
import hanglog.trip.domain.type.ItemType;
import hanglog.trip.dto.request.ExpenseRequest;
import hanglog.trip.dto.request.ItemRequest;
import hanglog.trip.dto.request.ItemUpdateRequest;
import hanglog.trip.dto.request.PlaceRequest;
import hanglog.trip.dto.response.ItemResponse;
import hanglog.trip.fixture.ExpenseFixture;
import hanglog.trip.fixture.ItemFixture;
import hanglog.trip.fixture.TripFixture;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private DayLogRepository dayLogRepository;

    @Mock
    private ImageRepository imageRepository;

    @DisplayName("새롭게 생성한 여행 아이템의 id를 반환한다.")
    @Test
    void save() {
        // given
        final PlaceRequest placeRequest = new PlaceRequest(
                "에펠탑",
                new BigDecimal("38.123456"),
                new BigDecimal("39.123456"),
                List.of("culture")
        );
        final ExpenseRequest expenseRequest = new ExpenseRequest("EUR", 10000.0, 1L);
        final ItemRequest itemRequest = new ItemRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                List.of("https://hanglog.com/img/imageName.png"),
                placeRequest,
                expenseRequest
        );

        given(itemRepository.save(any()))
                .willReturn(ItemFixture.LONDON_EYE_ITEM);
        given(dayLogRepository.findById(any()))
                .willReturn(Optional.of(new DayLog("첫날", 1, TripFixture.LONDON_TRIP)));
        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(new Category(1L, "문화", "culture")));

        // when
        final Long actualId = itemService.save(1L, itemRequest);

        // then
        assertThat(actualId).isEqualTo(1L);
    }


    @DisplayName("URL이 Base Url을 포함하고 있지 않으면 예외가 발생한다.")
    @Test
    void save_NotContainBaseUrl() {
        // given
        final PlaceRequest placeRequest = new PlaceRequest(
                "에펠탑",
                new BigDecimal("38.123456"),
                new BigDecimal("39.123456"),
                List.of("culture")
        );
        final ExpenseRequest expenseRequest = new ExpenseRequest("EUR", 10000.0, 1L);
        final ItemRequest itemRequest = new ItemRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                List.of("https://invalid-url/img/imageName.png"),
                placeRequest,
                expenseRequest
        );

        given(dayLogRepository.findById(any()))
                .willReturn(Optional.of(new DayLog("첫날", 1, TripFixture.LONDON_TRIP)));

        // when & then
        assertThatThrownBy(() -> itemService.save(1L, itemRequest)).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("URL의 형식이 잘못되면 예외가 발생한다.")
    @Test
    void save_InvalidParsedUrl() {
        // given
        final PlaceRequest placeRequest = new PlaceRequest(
                "에펠탑",
                new BigDecimal("38.123456"),
                new BigDecimal("39.123456"),
                List.of("culture")
        );
        final ExpenseRequest expenseRequest = new ExpenseRequest("EUR", 10000.0, 1L);
        final ItemRequest itemRequest = new ItemRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                List.of("https://hanglog.com/img/https://hanglog.com/img/imageName.png"),
                placeRequest,
                expenseRequest
        );

        given(dayLogRepository.findById(any()))
                .willReturn(Optional.of(new DayLog("첫날", 1, TripFixture.LONDON_TRIP)));

        // when & then
        assertThatThrownBy(() -> itemService.save(1L, itemRequest)).isInstanceOf(BadRequestException.class);
    }

    @DisplayName("여행 아이템의 정보를 수정한다. - 장소가 바뀌지 않은 경우")
    @Test
    void update_PlaceNotChange() {
        // given
        final ExpenseRequest expenseRequest = new ExpenseRequest("EUR", 10000.0, 1L);
        final ItemUpdateRequest itemUpdateRequest = new ItemUpdateRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                List.of("https://hanglog.com/img/imageName.png"),
                false,
                null,
                expenseRequest
        );

        given(itemRepository.save(any()))
                .willReturn(ItemFixture.LONDON_EYE_ITEM);
        given(itemRepository.findById(any()))
                .willReturn(Optional.of(ItemFixture.LONDON_EYE_ITEM));
        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(CategoryFixture.EXPENSE_CATEGORIES.get(1)));
        given(dayLogRepository.findById(any()))
                .willReturn(Optional.of(new DayLog("첫날", 1, TripFixture.LONDON_TRIP)));

        // when
        itemService.update(1L, 1L, itemUpdateRequest);

        // then
        verify(itemRepository).save(any());
    }

    @DisplayName("여행 아이템의 정보를 수정한다. - 장소가 바뀐 경우")
    @Test
    void update_PlaceChange() {
        // given
        final PlaceRequest placeRequest = new PlaceRequest(
                "에펠탑",
                new BigDecimal("38.123456"),
                new BigDecimal("39.123456"),
                List.of("culture")
        );
        final ExpenseRequest expenseRequest = new ExpenseRequest("EUR", 10000.0, 200L);
        final ItemUpdateRequest itemUpdateRequest = new ItemUpdateRequest(
                true,
                "에펠탑",
                4.5,
                "에펠탑을 방문",
                1L,
                List.of("https://hanglog.com/img/imageName.png"),
                false,
                placeRequest,
                expenseRequest
        );

        given(itemRepository.save(any()))
                .willReturn(ItemFixture.LONDON_EYE_ITEM);
        given(itemRepository.findById(any()))
                .willReturn(Optional.of(ItemFixture.LONDON_EYE_ITEM));
        given(categoryRepository.findById(any()))
                .willReturn(Optional.of(CategoryFixture.EXPENSE_CATEGORIES.get(1)));
        given(dayLogRepository.findById(any()))
                .willReturn(Optional.of(new DayLog("첫날", 1, TripFixture.LONDON_TRIP)));

        // when
        itemService.update(1L, 1L, itemUpdateRequest);

        // then
        verify(itemRepository).save(any());
    }

    @DisplayName("여행 아이템의 StatusType을 DELETED로 변경한다.")
    @Test
    void delete() {
        // given
        final DayLog dayLog = new DayLog(
                "첫날",
                1,
                TripFixture.LONDON_TRIP
        );
        final Item itemForDelete = new Item(
                1L,
                ItemType.NON_SPOT,
                "버스",
                1,
                3.0,
                "",
                dayLog,
                ExpenseFixture.EURO_10000
        );
        given(itemRepository.findById(any()))
                .willReturn(Optional.of(itemForDelete));

        // when
        itemService.delete(itemForDelete.getId());

        // then
        verify(itemRepository).delete(any());
    }

    @DisplayName("모든 여행 아이템의 Response를 반환한다.")
    @Test
    void getItems() {
        // given
        given(itemRepository.findAll())
                .willReturn(List.of(ItemFixture.LONDON_EYE_ITEM, ItemFixture.TAXI_ITEM));

        // when
        final List<ItemResponse> items = itemService.getItems();

        // then
        assertSoftly(softly -> {
            softly.assertThat(items.get(0)).usingRecursiveComparison()
                    .isEqualTo(ItemResponse.of(ItemFixture.LONDON_EYE_ITEM));
            softly.assertThat(items.get(1)).usingRecursiveComparison()
                    .isEqualTo(ItemResponse.of(ItemFixture.TAXI_ITEM));
        });
    }
}
