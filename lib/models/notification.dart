class Notification {
  Notification(
      {this.notificationId,
      this.pushTitle = '',
      this.pushMsg = '',
      this.receiveAt = '',
      this.createdAt = ''});

  int? notificationId;
  String pushTitle;
  String pushMsg;
  String receiveAt;
  String createdAt;

  factory Notification.fromJson(Map<String, dynamic> json) {
    return new Notification(
      notificationId: int.parse(json['notification_id']),
      pushTitle: json['push_title'],
      pushMsg: json['push_msg'],
      createdAt: json['created_at_notif'],
    );
  }

  factory Notification.fromJsonRaw(Map<String, dynamic> json) {
    return new Notification(
      pushTitle: json['push_title'],
      pushMsg: json['push_msg'],
      createdAt: json['created_at_notif'],
    );
  }

  Map<String, dynamic> toMap() => {
        "push_title": pushTitle,
        "push_msg": pushMsg,
        "created_at_notif": createdAt,
      };
}
