
class Review {
  Review({
    this.reviewId,
    this.review,
    this.storeId,
    this.userId,
    this.fullName,
    this.thumbUrl,
    this.createdAt,
  });

  int reviewId;
  String review;
  String fullName;
  String thumbUrl;
  int storeId;
  int userId;
  int createdAt;


  factory Review.fromJson(Map<String, dynamic> json) {
    return new Review(
      reviewId: int.parse(json['review_id']),
      review: json['review'],
      fullName: json['full_name'],
      thumbUrl: json['thumb_url'],
      storeId: int.parse(json['store_id']),
      userId: int.parse(json['user_id']),
      createdAt: int.parse(json['created_at'])
    );
  }
}