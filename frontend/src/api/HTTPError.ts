export class HTTPError extends Error {
  statusCode: number;

  code?: number;

  constructor(statusCode: number, message?: string, code?: number) {
    super(message);

    this.name = 'HTTPError';
    this.statusCode = statusCode;
    this.code = code;

    Object.setPrototypeOf(this, HTTPError.prototype);
  }
}
