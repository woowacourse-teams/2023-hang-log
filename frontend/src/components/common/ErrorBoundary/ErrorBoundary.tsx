import type { ComponentType, PropsWithChildren } from 'react';
import { Component } from 'react';

import { HTTPError } from '@api/HTTPError';

import type { ErrorProps } from '@components/common/Error/Error';

interface ErrorBoundaryProps {
  Fallback: ComponentType<ErrorProps>;
  onReset?: (error: Error | HTTPError) => void;
}

interface State {
  hasError: boolean;
  error: Error | null;
}

const initialState: State = {
  hasError: false,
  error: null,
};

class ErrorBoundary extends Component<PropsWithChildren<ErrorBoundaryProps>, State> {
  state: State = initialState;

  static getDerivedStateFromError(error: Error): State {
    return { hasError: true, error };
  }

  resetErrorBoundary = () => {
    const { onReset } = this.props;
    const { error } = this.state;

    onReset?.(error!);
    this.setState(initialState);
  };

  render() {
    const { Fallback, children } = this.props;
    const { error } = this.state;

    if (error) {
      return (
        <Fallback
          statusCode={error instanceof HTTPError ? error.statusCode : undefined}
          resetError={this.resetErrorBoundary}
        />
      );
    }

    return children;
  }
}

export default ErrorBoundary;
