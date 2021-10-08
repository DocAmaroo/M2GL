class HTTPException implements Exception {
  final int code;
  final String message;

  HTTPException(this.code, this.message);

  @override
  String toString() {
    return 'HTTPException{code: $code, message: $message}';
  }
}
