import type { CityData } from '@type/city';
import type { TripItemData } from '@type/tripItem';

const TEST_URL = 'http://localhost:3000';

describe('여행 수정 페이지', () => {
  beforeEach(() => {
    cy.viewport(1280, 832);
    cy.visit(`${TEST_URL}/trip/1/edit`);
    cy.wait(3000);
  });

  it('여행 수정 페이지에 처음 방문 시 여행 데이터가 렌더링되기 전에 skeleton을 볼 수 있다.', () => {
    cy.clock();

    cy.visit(`${TEST_URL}/trip/1/edit`);

    cy.tick(3000);

    cy.get('.skeleton').should('be.visible');
  });

  it('여행 수정 페이지에서 여행에 대한 정보를 볼 수 있다.', () => {
    cy.fixture('trip.json').then((expectedData) => {
      cy.findByText(expectedData.title).should('be.visible');

      cy.findByText('2023.07.01 - 2023.07.03').should('be.visible');

      cy.findByText(expectedData.description).should('be.visible');

      expectedData.cities.forEach((city: CityData) => {
        cy.findByText(city.name);
      });
    });
  });

  it('여행 수정 페이지에서 "여행 정보 수정" 버튼과 "저장" 버튼을 볼 수 있다.', () => {
    cy.findByText('여행 정보 수정');

    cy.findByText('저장');
  });

  it('여행 수정 페이지에서 선택 된 날짜 길이 만큼 데이로그 탭이 만들어져 있다.', () => {
    cy.fixture('trip.json').then((expectedData) => {
      const dayLogLength = expectedData.dayLogs.length;

      cy.findByRole('tablist').children().should('have.length', dayLogLength);
    });
  });

  it('여행 수정 페이지에서 선택 된 탭의 데이로그 소제목과 여행 아이템들을 볼 수 있다.', () => {
    cy.get('li[role="tab"]').first().click();

    cy.fixture('trip.json').then((expectedData) => {
      const { title, items } = expectedData.dayLogs[0];

      cy.findByPlaceholderText('소제목').should('have.value', title);

      items.forEach((item: TripItemData) => {
        cy.get('h6').contains(item.title);
      });
    });
  });

  it('여행 수정 페이지에서 여행 아이템을 "전체", "장소"로 필터링할 수 있다.', () => {
    cy.get('li[aria-label="장소 필터"]').click();

    cy.fixture('trip.json').then((expectedData) => {
      const { items } = expectedData.dayLogs[0];

      items.forEach((item: TripItemData) => {
        if (!item.itemType) cy.get('h6').should('not.contain', item.title);
        else cy.get('h6').contains(item.title);
      });
    });
  });

  it('여행 수정 페이지에서 데이로그 탭을 변경해서 다른 날짜의 여행 아이템을 볼 수 있다.', () => {
    cy.get('li[role="tab"]').eq(1).click({ force: true });

    cy.fixture('trip.json').then((expectedData) => {
      const { items } = expectedData.dayLogs[1];

      items.forEach((item: TripItemData) => {
        cy.get('h6').contains(item.title);
      });
    });
  });
});

describe('여행 정보 수정', () => {
  beforeEach(() => {
    cy.viewport(1280, 832);
    cy.visit(`${TEST_URL}/trip/1/edit`);
    cy.wait(4000);
  });

  it('"여행 정보 수정" 버튼을 클릭하면 여행 정보 수정 모달을 볼 수 있다.', () => {
    cy.findByText('여행 정보 수정').click();

    cy.findByRole('dialog').should('be.visible');
  });

  it('여행 수정 페이지에서 데이로그 탭에 여행 아이템이 없는 경우 "일정 기록 추가하기" 버튼을 클릭해서 여행 아이템 추가 모달을 열 수 있다.', () => {
    cy.get('li[role="tab"]').eq(2).click({ force: true });

    cy.findByText('일정 기록 추가하기').click();

    cy.findByRole('dialog').should('be.visible');
  });

  it('여행 정보 수정 모달에서 방문 도시를 추가하거나 삭제할 수 있다.', () => {
    cy.findByText('여행 정보 수정').click();

    cy.get('input[aria-label="방문 도시"]').type('뉴욕', { force: true, delay: 3000 });
    cy.findByText('뉴욕, 미국').click();

    cy.findByText('뉴욕').should('be.visible');

    cy.get('svg[aria-label="삭제 아이콘"]').last().click();

    cy.get('span').should('not.contain.text', '뉴욕');
  });

  it('여행 정보 수정 모달에서 여행 제목을 변경할 수 있다', () => {
    cy.findByText('여행 정보 수정').click();

    cy.get('input#title').type('입니다.');

    cy.fixture('trip.json').then((expectedData) => {
      cy.get('input#title').should('have.value', `${expectedData.title}입니다.`);
    });
  });

  it('여행 정보 수정 모달에서 방문 기간을 변경할 수 있다.', () => {
    cy.findByText('여행 정보 수정').click();

    cy.get('#date').should('have.value', '2023.07.01 - 2023.07.03');

    cy.get('#date').click();

    cy.get('span[aria-label="2023년 06월 7일"]').click();
    cy.get('span[aria-label="2023년 06월 12일"]').click();

    cy.get('#date').click();

    cy.get('#date').should('have.value', '2023.06.07 - 2023.06.12');
  });

  it('여행 정보 수정 모달에서 여행 설명을 변경할 수 있다.', () => {
    cy.findByText('여행 정보 수정').click();

    cy.fixture('trip.json').then((expectedData) => {
      cy.get('textarea#description').should('have.value', expectedData.description);
      cy.get('textarea#description').type('입니다.');
      cy.get('textarea#description').should('have.value', `${expectedData.description}입니다.`);
    });
  });

  it('여행 정보 수정 모달에서 대표 이미지 삭제 후 새로운 이미지를 업로드할 수 있다', () => {
    cy.findByText('여행 정보 수정').click();

    cy.get('button[aria-label="이미지 삭제"]').first().click();

    cy.get('input[type=file]')
      .invoke('show')
      .selectFile('src/assets/png/trip_default-thumbnail.png', { force: true });

    cy.fixture('trip.json').then((expectedData) => {
      cy.findByRole('dialog').find('img').should('exist');
      cy.findByRole('dialog').find(`img[src="${expectedData.imageUrl}"]`).should('not.exist');
    });
  });

  // 로컬에서는 잘 돌아가는데, github action으로는 안 됨....
  it.skip('여행 정보 수정 모달에서 여행 정보를 수정하면 변경된 여행 정보를 여행 정보 수정 페이지에서 볼 수 있다.', () => {
    cy.findByText('여행 정보 수정').click();

    cy.get('#date').click();

    cy.get('span[aria-label="2023년 06월 7일"]').click();
    cy.get('span[aria-label="2023년 06월 12일"]').click();

    cy.get('#date').click();

    cy.get('input#title').type('입니다.');

    cy.get('textarea#description').type('입니다.');

    cy.findByText('여행 정보 수정하기').click();

    cy.fixture('trip.json').then((expectedData) => {
      cy.findByText(`${expectedData.title}입니다.`).should('be.visible');

      cy.findByText(`${expectedData.description}입니다.`).should('be.visible');
    });
  });
});

describe('여행 아이템 추가', () => {
  beforeEach(() => {
    cy.viewport(1280, 832);
    cy.visit(`${TEST_URL}/trip/1/edit`);
    cy.wait(4000);
  });

  it('페이지 하단 + 버튼을 클릭하면 여행 아이템 추가 모달을 볼 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.findByRole('dialog').should('be.visible');
  });

  it('여행 아이템 추가 모달에서 디폴트로 장소 카테고리가 선택되어있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.get('div[role="radio"][aria-checked="true"]').parent().should('have.text', '장소');
  });

  it('여행 아이템 추가 모달에서 장소를 입력하면 구글 place api 검색어 자동 완성 창을 볼 수 있고, 검색 결과에서 선택하면 인풋 창에 선택된 결과를 볼 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.get('#autocomplete').type('샹젤리제', { force: true, delay: 3000 });

    cy.get('.pac-container').first().should('be.visible');
    cy.get('.pac-item').first().click({ force: true });

    cy.get('#autocomplete').should('have.value', '프랑스 파리 샹젤리제 거리');
  });

  it('여행 아이템 추가 모달에서 별 아이콘 호버하면 호버한 지점까지 별점이 채워지고, 별 아이콘을 클릭해서 원하는 별점을 입력할 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.get('.star-box > div').eq(5).trigger('mouseover');

    cy.get('.star-box > div').eq(6).click();
  });

  it('여행 아이템 추가 모달에서 비용 입력 시, 비용 카테고리, 통화, 그리고 비용 금액을 입력할 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.get('select[aria-label="비용 카테고리"]').select('문화').should('have.value', '200');

    cy.get('select[aria-label="통화"')
      .select('€&nbsp;&nbsp;&nbsp;&nbsp;(EUR)')
      .should('have.value', 'EUR');

    cy.get('input[aria-label="비용"]').type('400').should('have.value', '400');
  });

  it('여행 아이템 추가 모달에서 메모를 입력할 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.findByPlaceholderText('메모를 입력해 주세요')
      .type('즐거운 여행')
      .should('have.value', '즐거운 여행');
  });

  it('여행 아이템 추가 모달에서 이미지 파일을 업로드 할 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.get('input[type=file]')
      .invoke('show')
      .selectFile('src/assets/png/trip_default-thumbnail.png', { force: true });

    cy.get('button[aria-label="이미지 삭제"]').first().should('be.visible');
  });

  it('여행 아이템 추가 모달에서 업로드한 이미지 파일을 아이템 추가 전에 삭제할 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.get('input[type=file]')
      .invoke('show')
      .selectFile('src/assets/png/trip_default-thumbnail.png', { force: true });

    cy.get('button[aria-label="이미지 삭제"]').first().click();
  });

  it('여행 아이템 추가 모달에서 기타 카테고리를 선택하면 제목을 입력할 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.findByRole('radio', { name: /기타/ }).click();

    cy.get('#title')
      .type('샹젤리제 거리 -> 에펠탑 지하철')
      .should('have.value', '샹젤리제 거리 -> 에펠탑 지하철');
  });

  it('여행 아이템 추가 모달에서 필수 정보인 카테고리와 제목만 입력하면 아이템을 추가할 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.findByRole('radio', { name: /기타/ }).click();
    cy.get('#title').type('샹젤리제 거리 -> 에펠탑 지하철');

    cy.findByText('일정 기록 추가하기').click();

    cy.findByRole('dialog').should('not.exist');

    cy.get('h6').contains('샹젤리제 거리 -> 에펠탑 지하철').should('exist');
  });

  it('여행 아이템 추가 모달에서 필수 정보 외에도 입력하고 아이템을 추가한 후에 여행 아이템 목록에서 볼 수 있다.', () => {
    cy.get('button[aria-label="여행 아이템 추가"]').click();

    cy.findByRole('radio', { name: /기타/ }).click();
    cy.get('#title').type('샹젤리제 거리 -> 에펠탑 지하철');
    cy.get('.star-box > div').eq(6).click();
    cy.get('select[aria-label="비용 카테고리"]').select('문화');
    cy.get('select[aria-label="통화"').select('€&nbsp;&nbsp;&nbsp;&nbsp;(EUR)');
    cy.get('input[aria-label="비용"]').type('400');
    cy.findByPlaceholderText('메모를 입력해 주세요').type('즐거운 여행');
    cy.get('input[type=file]')
      .invoke('show')
      .selectFile('src/assets/png/trip_default-thumbnail.png', { force: true });

    cy.findByText('일정 기록 추가하기').click();

    cy.findByRole('dialog').should('not.exist');

    cy.get('h6').contains('샹젤리제 거리 -> 에펠탑 지하철').should('exist');

    cy.findByText('샹젤리제 거리 -> 에펠탑 지하철').parent().find('svg').should('have.length', 6);
    cy.findByText('샹젤리제 거리 -> 에펠탑 지하철')
      .parent()
      .find('p')
      .contains('교통')
      .should('exist');
    cy.findByText('샹젤리제 거리 -> 에펠탑 지하철')
      .parent()
      .find('p')
      .contains('€400')
      .should('exist');
    cy.findByText('샹젤리제 거리 -> 에펠탑 지하철')
      .parent()
      .find('p')
      .contains('즐거운 여행')
      .should('exist');
  });
});

describe('여행 아이템 수정', () => {
  beforeEach(() => {
    cy.viewport(1280, 832);
    cy.visit(`${TEST_URL}/trip/1/edit`);
    cy.wait(4000);
  });

  it('여행 아이템의 더 보기 버튼을 클릭해서 여행 아이템을 수정할 수 있다.', () => {
    cy.get('button[aria-label="더 보기 메뉴"]').first().click({ force: true });
    cy.findByText('수정').click();

    cy.findByRole('dialog').should('be.visible');
  });

  it.skip('여행 아이템 수정 모달을 열면 여행 아이템 정보가 입력되어 있다.', () => {
    cy.get('button[aria-label="더 보기 메뉴"]').first().click({ force: true });
    cy.findByText('수정').click();

    cy.fixture('trip.json').then((expectedData) => {
      const { items } = expectedData.dayLogs[0];
      const firstItem: TripItemData = items[0];

      cy.get('div[role="radio"][aria-checked="true"]').parent().should('have.text', '장소');
      cy.get('#autocomplete').should('have.value', firstItem.title);

      cy.get('select[aria-label="통화"').should('have.value', firstItem.expense?.currency);
      cy.get('input[aria-label="비용"]').should('have.value', firstItem.expense?.amount);

      cy.findByPlaceholderText('메모를 입력해 주세요').should('have.value', firstItem.memo);
    });
  });

  it.skip('여행 아이템 수정 모달에서 여행 아이템 정보를 수정하면 여행 아이템 목록에서 변경된 정보를 볼 수 있다.', () => {
    cy.get('button[aria-label="더 보기 메뉴"]').first().click({ force: true });
    cy.findByText('수정').click();

    cy.findByRole('radio', { name: /기타/ }).click();
    cy.get('#title').type(' 택시');
    cy.get('.star-box > div').eq(6).click();
    cy.get('select[aria-label="비용 카테고리"]').select('문화');
    cy.get('input[aria-label="비용"]').type('0');
    cy.findByPlaceholderText('메모를 입력해 주세요').type(' 즐거움!');

    cy.findByText('일정 기록 수정하기').click();

    cy.findByRole('dialog').should('not.exist');

    cy.fixture('trip.json').then((expectedData) => {
      const { items } = expectedData.dayLogs[0];
      const firstItem: TripItemData = items[0];

      cy.get('h6').contains(`${firstItem.title} 택시`).should('exist');

      cy.findByText(`${firstItem.title} 택시`)
        .parent()
        .find('p')
        .contains('£2,000')
        .should('exist');

      cy.findByText(`${firstItem.title} 택시`)
        .parent()
        .find('p')
        .contains(`${firstItem.memo} 즐거움!`)
        .should('exist');
    });
  });
});
