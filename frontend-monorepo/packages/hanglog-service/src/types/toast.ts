export interface ToastType {
  id: number;
  variant?: 'default' | 'success' | 'error';
  message: string;
  hasCloseButton?: boolean;
  showDuration?: number;
}
