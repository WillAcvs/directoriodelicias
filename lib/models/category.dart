class Category {
  Category(
      {required this.categoryId,
      this.category = '',
      this.categoryIcon = '',
      this.mapIcon = '',
      required this.createdAt,
      required this.updatedAt,
      required this.isDeleted,
      required this.pid,
      required this.hasSub});

  int categoryId;
  String category;
  String categoryIcon;
  String mapIcon;
  int createdAt;
  int updatedAt;
  int isDeleted;
  int pid;
  int hasSub;

  factory Category.fromJson(Map<String, dynamic> json) {
    return new Category(
        categoryId: int.parse(json['category_id']),
        category: json['category'],
        mapIcon: json['map_icon'],
        categoryIcon: json['category_icon'],
        updatedAt: int.parse(json['updated_at']),
        isDeleted: int.parse(json['is_deleted']),
        pid: int.parse(json['pid']),
        hasSub: int.parse(json['has_sub']),
        createdAt: int.parse(json['created_at']));
  }

  factory Category.fromJsonRaw(Map<String, dynamic> json) {
    return new Category(
        categoryId: json['category_id'],
        category: json['category'],
        mapIcon: json['map_icon'],
        categoryIcon: json['category_icon'],
        updatedAt: json['updated_at'],
        isDeleted: json['is_deleted'],
        pid: json['pid'],
        hasSub: json['has_sub'],
        createdAt: json['created_at']);
  }

  Map<String, dynamic> toMap() => {
        "category_id": categoryId,
        "category": category,
        "map_icon": mapIcon,
        "category_icon": categoryIcon,
        "updated_at": updatedAt,
        "is_deleted": isDeleted,
        "pid": pid,
        "has_sub": hasSub,
        "created_at": createdAt,
      };
}
