import { containerStyling } from '@components/ToastContainer/ToastContainer.style';

/** Toast 컴포넌트들이 쌓이는 컨테이너  */
const ToastContainer = () => {
  return <div css={containerStyling} id="toast-container" />;
};

export default ToastContainer;
