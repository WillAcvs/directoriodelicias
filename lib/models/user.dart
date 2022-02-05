class User {
  
  int userId;
  String username;
  String facebookId;
  String twitterId;
  String loginHash;
  String fullName;
  String thumbUrl;
  String photoUrl;
  String email;
  String appleId;

  User({
    this.userId,
    this.username,
    this.facebookId,
    this.twitterId,
    this.loginHash,
    this.fullName,
    this.thumbUrl,
    this.photoUrl,
    this.appleId,
    this.email
  });

  factory User.fromJsonRaw(Map<String, dynamic> json) {
    return new User(
      userId: json['user_id'],
      username: json['username'],
      facebookId: json['facebook_id'],
      twitterId: json['twitter_id'],
      loginHash: json['login_hash'],
      fullName: json['full_name'],
      thumbUrl: json['thumb_url'],
      photoUrl: json['photo_url'],
      appleId: json['apple_id'],
      email: json['email']
    );
  }

  User.fromJson(Map<String, dynamic> json)
      : this.userId = int.parse(json['user_id']),
        this.username = json['username'],
        this.facebookId = json['facebook_id'],
        this.twitterId = json['twitter_id'],
        this.loginHash = json['login_hash'],
        this.fullName = json['full_name'],
        this.thumbUrl = json['thumb_url'],
        this.photoUrl = json['photo_url'],
        this.appleId = json['apple_id'],
        this.email = json['email'];

  Map<String, dynamic> toMap() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['user_id'] = this.userId;
    data['username'] = this.username;
    data['facebook_id'] = this.facebookId;
    data['twitter_id'] = this.twitterId;
    data['login_hash'] = this.loginHash;
    data['full_name'] = this.fullName;
    data['thumb_url'] = this.thumbUrl;
    data['photo_url'] = this.photoUrl;
    data['apple_id'] = this.appleId;
    data['email'] = this.email;
    return data;
  }

}