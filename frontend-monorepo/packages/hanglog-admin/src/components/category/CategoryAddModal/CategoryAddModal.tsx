import { Modal, Flex, Button, Theme } from 'hang-log-design-system';

import { UseAddCategoryForm } from '@/hooks/category/useAddCategoryForm';
import { CategoryData } from '@/types/category';

import CloseIcon from '@assets/svg/close-icon.svg?react';

import {
  wrapperStyling,
  formStyling,
  buttonStyling,
  closeButtonStyling,
  closeIconStyling,
} from './CategoryAddModal.style';
import IdInput from './IdInput/IdInput';
import EngNameInput from './EngNameInput/EngNameInput';
import KorNameInput from './KorNameInput/KorNameInput';

interface CategoryAddModalProps {
  originalCategoryId?: number;
  initialData?: CategoryData;
  isOpen?: boolean;
  onClose: () => void;
}

const CategoryAddModal = ({
  originalCategoryId,
  initialData,
  isOpen = true,
  onClose,
}: CategoryAddModalProps) => {
  const {
    categoryInformation,
    isIdError,
    isEngNameError,
    isKorNameError,
    disableIdError,
    disableEngNameError,
    disableKorNameError,
    updateInputValue,
    handleSubmit,
  } = UseAddCategoryForm({
    originalCategoryId,
    initialData,
    onSuccess: onClose,
  });

  return (
    <>
      <Modal
        css={wrapperStyling}
        isOpen={isOpen}
        closeModal={onClose}
        isBackdropClosable={false}
        hasCloseButton={false}
      >
        <button
          type="button"
          aria-label="모달 닫기 버튼"
          onClick={onClose}
          css={closeButtonStyling}
        >
          <CloseIcon css={closeIconStyling} />
        </button>
        <form css={formStyling} onSubmit={handleSubmit} noValidate>
          <Flex styles={{ direction: 'column', gap: Theme.spacer.spacing3, align: 'stretch' }}>
            <IdInput
              value={categoryInformation.id}
              isError={isIdError}
              updateInputValue={updateInputValue}
              disableError={disableIdError}
            />
            <EngNameInput
              value={categoryInformation.engName}
              isError={isEngNameError}
              updateInputValue={updateInputValue}
              disableError={disableEngNameError}
            />
            <KorNameInput
              value={categoryInformation.korName}
              isError={isKorNameError}
              updateInputValue={updateInputValue}
              disableError={disableKorNameError}
            />
          </Flex>
          <Button css={buttonStyling} variant="primary">
            도시 {originalCategoryId ? '수정하기' : '추가하기'}
          </Button>
        </form>
      </Modal>
    </>
  );
};

export default CategoryAddModal;
