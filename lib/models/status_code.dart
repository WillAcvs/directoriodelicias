class StatusCode {
  String statusCode;
  String statusText;

  StatusCode({required this.statusCode, required this.statusText});

  factory StatusCode.fromJson(Map<String, dynamic> json) {
    return new StatusCode(
      statusCode: json['status_code'],
      statusText: json['status_text'],
    );
  }
}
