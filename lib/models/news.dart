class News {
  News({
    required this.newsId,
    this.newsContent = '',
    this.newsTitle = '',
    this.newsUrl = '',
    this.photoUrl = '',
    required this.createdAt,
    required this.updatedAt,
    required this.isDeleted,
  });

  int newsId;
  String newsContent;
  String newsTitle;
  String newsUrl;
  String photoUrl;
  int createdAt;
  int updatedAt;
  int isDeleted;

  factory News.fromJson(Map<String, dynamic> json) {
    return new News(
        newsId: int.parse(json['news_id']),
        newsContent: json['news_content'],
        newsTitle: json['news_title'],
        newsUrl: json['news_url'],
        photoUrl: json['photo_url'],
        updatedAt: int.parse(json['updated_at']),
        isDeleted: int.parse(json['is_deleted']),
        createdAt: int.parse(json['created_at']));
  }

  Map<String, dynamic> toMap() => {
        "news_id": newsId,
        "news_content": newsContent,
        "news_title": newsTitle,
        "news_url": newsUrl,
        "photo_url": photoUrl,
        "updated_at": updatedAt,
        "is_deleted": isDeleted,
        "created_at": createdAt,
      };
}
