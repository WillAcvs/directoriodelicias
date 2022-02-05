
class Fave {
  
  int faveId;
  int storeId;
  
  Fave({
    this.faveId, 
    this.storeId
   });

  factory Fave.fromJson(Map<String, dynamic> json) {
    return new Fave(
      faveId: json['fave_id'],
      storeId: int.parse(json['store_id'].toString())
    );
  }

  Map<String, dynamic> toMap() => {
    "store_id": storeId
  };
}