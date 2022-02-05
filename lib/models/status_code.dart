class StatusCode {
  
  String statusCode;
  String statusText;
  
  StatusCode({
    this.statusCode, 
    this.statusText
   });

  factory StatusCode.fromJson(Map<String, dynamic> json) {
      return new StatusCode(
        statusCode: json['status_code'],
        statusText: json['status_text'],
    );
  }
}