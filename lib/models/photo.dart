
class Photo {
  Photo({
    this.photoId,
    this.photoUrl = '',
    this.thumbUrl = '',
    this.storeId,
    this.createdAt,
    this.updatedAt,
    this.isDeleted,
  });

  int photoId;
  String photoUrl;
  String thumbUrl;
  int storeId;
  int createdAt;
  int updatedAt;
  int isDeleted;

  factory Photo.fromJson(Map<String, dynamic> json) {
    return new Photo(
      photoId: int.parse(json['photo_id']),
      photoUrl: json['photo_url'],
      thumbUrl: json['thumb_url'],
      storeId: int.parse(json['store_id']),
      updatedAt: int.parse(json['updated_at']),
      isDeleted: int.parse(json['is_deleted']),
      createdAt: int.parse(json['created_at'])
    );
  }

  factory Photo.fromJsonRaw(Map<String, dynamic> json) {
    return new Photo(
      photoId: json['photo_id'],
      photoUrl: json['photo_url'],
      thumbUrl: json['thumb_url'],
      storeId: json['store_id'],
      updatedAt: json['updated_at'],
      isDeleted: json['is_deleted'],
      createdAt: json['created_at']
    );
  }

  Map<String, dynamic> toMap() => {
    "photo_id": photoId,
    "photo_url": photoUrl,
    "thumb_url": thumbUrl,
    "store_id": storeId,
    "updated_at": updatedAt,
    "is_deleted": isDeleted,
    "created_at": createdAt,
  };
}